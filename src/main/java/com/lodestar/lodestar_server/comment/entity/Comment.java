package com.lodestar.lodestar_server.comment.entity;

import com.lodestar.lodestar_server.board.entity.Board;
import com.lodestar.lodestar_server.common.BaseEntity;
import com.lodestar.lodestar_server.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
            //foreignKey 속성 : 댓글을 저장할 때 Board 엔티티를 조회하지 않고 외래키로 바로 저장하도록 할건데,
            //이 때 존재하지 않는 외래키가 들어온다면 예외 발생하도록.
    Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    User user;

    @Column(nullable = false)
    String content;

    @Builder
    private Comment(Board board, User user, String content) {
        this.board = board;
        this.user = user;
        this.content = content;
    }

    public void modifyContent(String content) {
        this.content = content;
    }

    public static Comment create(User user, Board board, String content) {
        return Comment.builder()
                .user(user)
                .board(board)
                .content(content)
                .build();
    }
}
