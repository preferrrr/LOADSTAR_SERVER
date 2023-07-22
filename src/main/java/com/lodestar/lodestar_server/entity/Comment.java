package com.lodestar.lodestar_server.entity;

import com.lodestar.lodestar_server.dto.response.CommentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Entity
@Getter
@Setter
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

//    @OneToOne(mappedBy = "comment")
//    Notice notice;

    public CommentDto createDto() {
        CommentDto dto = new CommentDto();
        dto.setCommentContent(this.content);
        dto.setCreatedAt(this.getCreatedAt());
        dto.setModifiedAt(this.getModifiedAt());
        dto.setCommentId(this.id);
        dto.setUsername(this.user.getUsername());
        dto.setUserId(this.user.getId());

        return dto;
    }

}
