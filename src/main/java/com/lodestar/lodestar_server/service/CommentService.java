package com.lodestar.lodestar_server.service;


import com.lodestar.lodestar_server.dto.CreateCommentDto;
import com.lodestar.lodestar_server.entity.Board;
import com.lodestar.lodestar_server.entity.Comment;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.repository.BoardRepository;
import com.lodestar.lodestar_server.repository.CommentRepository;
import com.lodestar.lodestar_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final UserRepository userRepository;
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

}
