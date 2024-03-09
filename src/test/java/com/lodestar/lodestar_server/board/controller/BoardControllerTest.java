package com.lodestar.lodestar_server.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lodestar.lodestar_server.board.dto.request.CreateBoardDto;
import com.lodestar.lodestar_server.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BoardController.class)
@WithMockUser(roles = "USER")
@AutoConfigureMockMvc(addFilters = false)
class BoardControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @DisplayName("CreateBoardDto의 title이 null 혹은 공백이면 HttpStatus 400을 반환한다.")
    @Test
    void notBlankTest() throws Exception {

        /** given */
        CreateBoardDto dto = new CreateBoardDto("", "content", List.of());

        /** when then */
        mockMvc.perform(post("/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("제목은 null 또는 공백일 수 없습니다."));
    }

}