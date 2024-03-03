package com.lodestar.lodestar_server.board.service;

import com.lodestar.lodestar_server.board.dto.response.GetBoardResponseDto;
import com.lodestar.lodestar_server.board.entity.Board;
import com.lodestar.lodestar_server.board.repository.BoardRepository;
import com.lodestar.lodestar_server.career.entity.Career;
import com.lodestar.lodestar_server.career.repository.CareerRepository;
import com.lodestar.lodestar_server.hashtag.entity.BoardHashtag;
import com.lodestar.lodestar_server.hashtag.repository.HashtagRepository;
import com.lodestar.lodestar_server.user.entity.User;
import com.lodestar.lodestar_server.user.respository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class BoardServiceCachingTest {

    @Autowired
    private BoardService boardService;
    @SpyBean
    private BoardServiceSupport boardServiceSupport;
    @Autowired
    @Qualifier("cacheManager")
    private CacheManager cacheManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private HashtagRepository hashtagRepository;
    @Autowired
    private CareerRepository careerRepository;


    private User user;
    private List<Career> careers;
    private Board board;
    private List<BoardHashtag> hashtags;
    private MockHttpSession session = new MockHttpSession();

    @BeforeEach
    void setUp() {
        user = User.create("username", "password", "email", List.of("USER"));
        careers = List.of(
                Career.create(user, "x1", 1l, 2l, "rangeName"),
                Career.create(user, "x2", 3l, 4l, "rangeName2"));
        board = Board.create(user, "title", "content");
        hashtags = List.of(
                BoardHashtag.create(board, "hashtag1"),
                BoardHashtag.create(board, "hashtag2"));

        userRepository.save(user);
        careerRepository.saveAll(careers);
        boardRepository.save(board);
        hashtagRepository.saveAll(hashtags);

    }

    @AfterEach
    void tearDown() {
        careerRepository.deleteAllInBatch();
        hashtagRepository.deleteAllInBatch();
        boardRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        cacheManager.getCacheNames().stream().forEach(name -> cacheManager.getCache(name).clear());
    }


    @Test
    @DisplayName("이전에 조회된 게시글이면, DB에서 조회하지 않고 캐싱되어 있는 값을 반환한다.")
    void getBoardCachingTest() {

        //given
        doNothing().when(boardServiceSupport).increaseViewIfNotViewedBefore(any(Board.class), any(User.class), any(HttpSession.class));

        //조회하면 캐시에 저장되고, 이후에 한 번 더 호출하면 데이터베이스를 조회하지 않고 캐시에서 조회해야함.
        GetBoardResponseDto dto = boardService.getBoard(session, user, board.getId());

        //when

        /** 캐시에서 조회한다. */
        GetBoardResponseDto cache = boardService.getBoard(session, user, board.getId());

        //then

        /** 캐시에서 조회하기 때문에, 이전 조회에서만 호출해야함 => 1번 */
        verify(boardServiceSupport, times(1)).getBoardWithHashtagsAndCommentsById(any());
        verify(boardServiceSupport, times(1)).increaseViewIfNotViewedBefore(any(Board.class), any(User.class), any(HttpSession.class));
        verify(boardServiceSupport, times(1)).getCommentsWithUserInfoByBoardId(any(Long.class));
        assertThat(dto.getBoardId()).isEqualTo(cache.getBoardId());
        assertThat(dto.getTitle()).isEqualTo(cache.getTitle());

        /** redis에 캐시가 저장되어 있어야 함. */
        assertThat(cacheManager.getCache("board").get(board.getId())).isNotNull();

    }

    @Test
    @DisplayName("게시글을 삭제하면, 캐시에 저장되어 있던 게시글 조회 데이터가 삭제 된다.")
    void deleteBoardCachingTest() {
        //given

        doNothing().when(boardServiceSupport).increaseViewIfNotViewedBefore(any(Board.class), any(User.class), any(HttpSession.class));

        //캐시에 저장하기 위한 조회
        boardService.getBoard(session, user, board.getId());

        //when

        /** 캐시도 같이 삭제되어야 함 */
        boardService.deleteBoard(user, board.getId());

        //then
        verify(boardServiceSupport, times(1)).getBoardById(anyLong());
        verify(boardServiceSupport, times(1)).checkIsBoardWriterForDelete(any(Board.class), any(User.class));
        verify(boardServiceSupport, times(1)).deleteBoardById(anyLong());

        /** 캐시에 데이터가 없어야 함 */
        assertThat(cacheManager.getCache("board").get(board.getId())).isNull();

    }


}