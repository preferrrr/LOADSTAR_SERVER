package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findById(Long id);

    List<Board> findByUserId(Long userId);

    @Query("select b from Board b " +
            "left join fetch b.hashtag")
    Page<Board> findAll(Pageable pageable);


    @Query("select b from Board b " +
            "join fetch b.hashtag h " +
            "where b.id in :boardIds " +
            "order by b.createdAt desc")
    List<Board> findBoardsWhereInBoardIds(@Param("boardIds") List<Long> boardIds);


    @Query(nativeQuery = true,
            value = "select b.board_id from board b join board_hashtag h on b.board_id = h.board_id " +
                    "where hashtag_name in (:hashtags) " +
                    "group by b.board_id",
            countQuery = "select count(b.board_id) from board b join board_hashtag h on b.board_id = h.board_id")
    Page<Long> findBoardIdByHashtags(Pageable pageable, @Param("hashtags") List<String> hashtags);


    //TODO: distinct하지 않으면 중복돼서 조회됨. 예를들어 hathtag가 2개면 comment가 2쌍으로 중복됨.결과 수 만큼 나옴.이유 체크
    //FetchJoin에서 생기는 문제. 카테시안 곱이 발생하여 중복이 발생함
    //M*N으로 모든 경우의 수를 출력하게 됨 => distinct 또는 Set 자료구조 사용해서 해결
    //연관관계를 미리 함께 가져오는 만큼 중복을 제거해야함
    //TODO: comment에 fetch를 추가하면 Comment의 Notice 엔티티까지 같이 조회하게 되어서 댓글 갯수만큼 Notice를 더 조회하게 됨
    //그래서 comment는 fetch join하지 않고 쿼리 한번만 보내도록 함. 연관관계를 eager로 해주면 다른 로직에서 필요없는 댓글이 조회되므로 여기서 타협...
    @Query("select distinct b from Board b " +
            "left join fetch b.hashtag " +
            "left join b.comments " +
            "where b.id = :boardId")
    Board findByPathBoardId(@Param("boardId") Long boardId);

    List<Board> findBoardsByIdIn(@Param("userId") List<Long> userId);

    void deleteById(Long boardId);
}
