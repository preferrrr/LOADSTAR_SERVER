package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.*;
import com.lodestar.lodestar_server.service.EmailService;
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
     * */
    @PostMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestBody CheckEmailRequestDto checkEmailRequestDto) throws Exception {

        checkEmailRequestDto.validateFieldsNotNull();

        emailService.checkEmail(checkEmailRequestDto.getEmail());
        CheckEmailResponseDto responseDto = new CheckEmailResponseDto("메일 전송이 완료되었습니다.");

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    /**
     * 인증코드 확인
     * /emails/check-key?email= ?key=
     * */
    @GetMapping("/check-key")
    public ResponseEntity<?> checkKey(@RequestParam("email") String email,
                                      @RequestParam("key") String key) {

        if (emailService.checkKey(email, key)) {
            CheckKeyResponseDto responseDto = new CheckKeyResponseDto(true, "인증에 성공했습니다.");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        else {
            CheckKeyResponseDto responseDto = new CheckKeyResponseDto(false, "인증에 실패했습니다.");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }


}
