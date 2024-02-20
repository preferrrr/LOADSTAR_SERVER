package com.lodestar.lodestar_server.comment.service;

import com.lodestar.lodestar_server.comment.entity.Comment;
import com.lodestar.lodestar_server.comment.exception.CommentNotFoundException;
import com.lodestar.lodestar_server.comment.exception.UnauthorizedDeleteCommentException;
import com.lodestar.lodestar_server.comment.exception.UnauthorizedModifyCommentException;
import com.lodestar.lodestar_server.comment.repository.CommentRepository;
import com.lodestar.lodestar_server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentServiceSupport {

    private final CommentRepository commentRepository;

    public List<Comment> getCommentsWithUserInfoByBoardId(Long id) {
        return commentRepository.findCommentsWithUserInfoByBoardId(id);
    }

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
    }

    public void checkIsCommentWriterForDelete(Comment comment, User user) {
        if(!comment.getUser().getId().equals(user.getId()))
            throw new UnauthorizedDeleteCommentException();
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    public void checkIsCommentWriterForModify(Comment comment, User user) {
        if(!comment.getUser().getId().equals(user.getId()))
            throw new UnauthorizedModifyCommentException();
    }

    public List<Comment> getMyComments(User user, Pageable pageable) {
        return commentRepository.getMyComments(user, pageable);
    }
}
