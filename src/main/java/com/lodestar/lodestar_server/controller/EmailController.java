package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.request.EmailRequestDto;
import com.lodestar.lodestar_server.dto.request.FindPwdRequestDto;
import com.lodestar.lodestar_server.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;

    /**
     * 이메일 인증코드 전송, 중간에 중복체크도 함
     * /emails/check-email
     */
    @PostMapping("/check-email")
    @Operation(summary = "이메일 인증코드 전송", description = "해당 메일로 가입한 유저가 있는지 중복체크도 함.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
            @ApiResponse(responseCode = "409", description = "해당 메일로 가입한 유저 존재"),
            @ApiResponse(responseCode = "500", description = "메일 전송 실패")
    })
    public ResponseEntity checkEmail(@RequestBody EmailRequestDto emailRequestDto) throws Exception {

        emailRequestDto.validateFieldsNotNull();

        emailService.checkEmail(emailRequestDto.getEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * 인증코드 확인
     * /emails/check-key?email= ?key=
     */
    @GetMapping("/check-key")
    @Operation(summary = "인증코드 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "인증 실패")
    })
    public ResponseEntity checkKey(@Schema(name = "이메일") @RequestParam("email") String email,
                                      @Schema(name = "인증코드") @RequestParam("key") String key) {

        if (emailService.checkKey(email, key)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 비밀번호 찾기
     * emails/find-password/send-email
     */

    @PostMapping("/find-password")
    @Operation(summary = "비밀번호 찾기 이메일 인증코드 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
            @ApiResponse(responseCode = "400", description = "해당 이메일로 가입한 아이디 없음."),
            @ApiResponse(responseCode = "500", description = "메일 전송 실패")
    })
    public ResponseEntity findPwdSendEmail(@RequestBody FindPwdRequestDto requestDto) throws Exception {

        requestDto.validateFieldsNotNull();

        emailService.findPwdSendEmail(requestDto.getEmail(), requestDto.getUsername());

        return new ResponseEntity<>(HttpStatus.OK);

    }


}
