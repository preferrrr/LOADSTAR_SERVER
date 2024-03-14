package com.lodestar.lodestar_server.board.entity;

import com.lodestar.lodestar_server.bookmark.entity.Bookmark;
import com.lodestar.lodestar_server.comment.entity.Comment;
import com.lodestar.lodestar_server.common.BaseEntity;
import com.lodestar.lodestar_server.hashtag.entity.BoardHashtag;
import com.lodestar.lodestar_server.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    //유저가 북마크를 등록할 때마다 +-1 해주도록 함.
    //이 컬럼이 없다면, 메인 페이지 게시글 조회, 검색 등으로 게시글들을 가져올 때
    //각 게시글마다 Bookmark 테이블에서 계산해야함.
    //사용자가 북마크를 등록했는데, 이 컬럼에 1이 더해지지 않는 경우 ? => @Transactional로 해결됨.
    @Column(name = "bookmark_count", columnDefinition = "INT default 0")
    private Integer bookmarkCount;

    @Column(columnDefinition = "INT default 0")
    private Integer view;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardHashtag> hashtags = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @Builder
    private Board(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void addView() {
        this.view += 1;
    }

    public void addBookmarkCount() {
        this.bookmarkCount += 1;
    }

    public void subBookmarkCount() {
        this.bookmarkCount -= 1;
    }

    public void modifyBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Board create(User user, String title, String content) {
        return Board.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
    }


}
