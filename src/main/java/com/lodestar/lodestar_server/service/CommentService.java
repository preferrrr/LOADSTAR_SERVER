package com.lodestar.lodestar_server.service;


import com.lodestar.lodestar_server.dto.request.CreateCommentDto;
import com.lodestar.lodestar_server.dto.request.ModifyCommentDto;
import com.lodestar.lodestar_server.entity.Board;
import com.lodestar.lodestar_server.entity.Comment;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.exception.AuthFailException;
import com.lodestar.lodestar_server.exception.NotFoundException;
import com.lodestar.lodestar_server.repository.BoardRepository;
import com.lodestar.lodestar_server.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


    public void createComment(User user, CreateCommentDto createCommentDto) {
        Comment comment = new Comment();

        Board board= boardRepository.getReferenceById(createCommentDto.getBoardId());

        comment.setUser(user);
        comment.setBoard(board);
        comment.setContent(createCommentDto.getContent());

        commentRepository.save(comment);
    }

    public void deleteComment(User user, Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("[delete comment] commentId: " + commentId));

        if(comment.getUser().getId() != user.getId())
            throw new AuthFailException("delete comment : " + commentId);

        commentRepository.deleteById(commentId);

    }

    public void modifyComment(User user, Long commentId, ModifyCommentDto modifyCommentDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("[modify comment] commentId: " + commentId));

        if(comment.getUser().getId() != user.getId())
            throw new AuthFailException("modify Comment Id: " + commentId);

        comment.setContent(modifyCommentDto.getContent());


    }
}
