package com.lodestar.lodestar_server.repository;

import com.lodestar.lodestar_server.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    Optional<Board> findById(Long id);

    List<Board> findByUserId(Long userId);

    @Query("select b from Board b " +
            "left join fetch b.hashtag")
    Page<Board> findAll(Pageable pageable);


    //TODO: distinct하지 않으면 중복돼서 조회됨. 예를들어 hathtag가 2개면 comment가 2쌍으로 중복됨.결과 수 만큼 나옴.이유 체크
    //FetchJoin에서 생기는 문제. 카테시안 곱이 발생하여 중복이 발생함
    //M*N으로 모든 경우의 수를 출력하게 됨 => distinct 또는 Set 자료구조 사용해서 해결
    //연관관계를 미리 함께 가져오는 만큼 중복을 제거해야함
    //TODO: comment에 fetch를 추가하면 Comment의 Notice 엔티티까지 같이 조회하게 되어서 댓글 갯수만큼 Notice를 더 조회하게 됨
    //그래서 comment는 fetch join하지 않고 쿼리 한번만 보내도록 함. 연관관계를 eager로 해주면 다른 로직에서 필요없는 댓글이 조회되므로 여기서 타협...
    @Query("select distinct b from Board b " +
            "left join fetch b.hashtag h " +
            "left join b.comments c " +
            "where b.id = :boardId")
    Optional<Board> findByPathBoardId(@Param("boardId") Long boardId);


    @Query(value = "select * from board b where b.board_id in (select m.board_id from bookmark m where m.user_id = :userId)",
        nativeQuery = true)
    List<Board> findBoardsByIdIn(@Param("userId") Long userId);

    void deleteById(Long boardId);



    /***********************************searchBoards********************************************/
    //띄어쓰기를 고려해야함.
    //예를 들어, 저장된 게시글 제목이 '프론트 엔드'이고
    //검색 키워드가 '프론트엔드'라면 검색결과에 나오지 않음.
    //그래서 저장된 게시글의 제목에 띄어쓰기(공백)이 있다면, 저장된 게시글의 공백을 제거하고 검색함.
    //그러기 위해서 mysql의 replace를 사용.
    //저장된 게시글의 제목이 '프론트엔드'이고
    //검색 키워드가 '프론트 엔드'라면 검색어에 있는 공백때문에 '프론트엔드'가 검색되지 않을 것인데,
    //그러지 않기 위해서 regexp를 사용. => like %프론트%, like %엔드%가 검색 결과로 나옴.
    //하지만 regexp를 이렇게 사용한다면 프론트엔드와 상관없는 백엔드도 검색될 것이다.
    //이 같은 경우와
    //키워드가 아닌 문장으로 검색하는 경우에 너무 많은 경우의 수를 고려해야 한다.
    //하지만 난 이정도에서 만족하도록 하겠다.
    @Query(nativeQuery = true,
            value = "select distinct b.board_id from board b " +
                    "where replace(b.title, ' ', '') regexp :keywords or replace(b.content, ' ','') regexp :keywords",
            countQuery = "select count(b.board_id) from board b")
    Page<Long> searchBoards(Pageable pageable, @Param("keywords") String keywords);


    @Query("select distinct b from Board b " +
            "join b.hashtag h " +
            "join fetch Career c on b.user = c.user " +
            "where b.id in :boardIds " +
            "order by b.createdAt desc")
    List<Board> findBoardsWhereInBoardIds(@Param("boardIds") List<Long> boardIds);

}
