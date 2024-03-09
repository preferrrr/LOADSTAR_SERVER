package com.lodestar.lodestar_server.email.controller;

import com.lodestar.lodestar_server.email.dto.request.EmailRequestDto;
import com.lodestar.lodestar_server.email.dto.request.FindPwdRequestDto;
import com.lodestar.lodestar_server.email.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
@AllArgsConstructor
public class MailController {

    private final MailService mailService;

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
    public ResponseEntity<HttpStatus> checkEmail(@RequestBody @Valid EmailRequestDto emailRequestDto) throws Exception {


        mailService.checkEmail(emailRequestDto.getEmail());

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
    public ResponseEntity<HttpStatus> checkAuthenticationKey(@Schema(name = "이메일") @RequestParam("email") String email,
                                                             @Schema(name = "인증코드") @RequestParam("key") String key) {

        mailService.checkAuthenticationKey(email, key);

        return new ResponseEntity(HttpStatus.OK);
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
    public ResponseEntity<HttpStatus> sendMailToFindPassword(@RequestBody @Valid FindPwdRequestDto requestDto) throws Exception {


        mailService.sendMailToFindPassword(requestDto);

        return new ResponseEntity<>(HttpStatus.OK);

    }


}
