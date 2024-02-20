package com.lodestar.lodestar_server.comment.service;


import com.lodestar.lodestar_server.board.service.BoardServiceSupport;
import com.lodestar.lodestar_server.comment.dto.request.CreateCommentDto;
import com.lodestar.lodestar_server.comment.dto.request.ModifyCommentDto;
import com.lodestar.lodestar_server.comment.dto.response.MyCommentResponseDto;
import com.lodestar.lodestar_server.board.entity.Board;
import com.lodestar.lodestar_server.comment.entity.Comment;
import com.lodestar.lodestar_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentServiceSupport commentServiceSupport;
    private final BoardServiceSupport boardServiceSupport;

    @Transactional(readOnly = false)
    public void createComment(User user, CreateCommentDto createCommentDto) {

        Board board = boardServiceSupport.getBoardById(createCommentDto.getBoardId());

        Comment comment = Comment.create(user, board, createCommentDto.getContent());

        commentServiceSupport.saveComment(comment);
    }

    @Transactional(readOnly = false)
    public void deleteComment(User user, Long commentId) {

        Comment comment = commentServiceSupport.findCommentById(commentId);

        commentServiceSupport.checkIsCommentWriterForDelete(comment, user);


        commentServiceSupport.deleteComment(comment);

    }

    @Transactional(readOnly = false)
    public void modifyComment(User user, Long commentId, ModifyCommentDto modifyCommentDto) {

        Comment comment = commentServiceSupport.findCommentById(commentId);

        commentServiceSupport.checkIsCommentWriterForModify(comment, user);

        comment.modifyContent(modifyCommentDto.getContent());

    }

    public MyCommentResponseDto getMyComments(User user, Pageable pageable) {

        List<Comment> comments = commentServiceSupport.getMyComments(user, pageable);

        return MyCommentResponseDto.of(comments);
    }
}
