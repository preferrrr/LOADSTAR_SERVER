package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.request.CheckEmailRequestDto;
import com.lodestar.lodestar_server.dto.response.CheckKeyResponseDto;
import com.lodestar.lodestar_server.dto.response.FindPasswordResponseDto;
import com.lodestar.lodestar_server.dto.response.MessageResponseDto;
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
     */
    @PostMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestBody CheckEmailRequestDto checkEmailRequestDto) throws Exception {

        checkEmailRequestDto.validateFieldsNotNull();

        emailService.checkEmail(checkEmailRequestDto.getEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * 인증코드 확인
     * /emails/check-key?email= ?key=
     */
    @GetMapping("/check-key")
    public ResponseEntity<?> checkKey(@RequestParam("email") String email,
                                      @RequestParam("key") String key) {

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

    @PostMapping("/find-password/send-email")
    public ResponseEntity<?> findPwdSendEmail(@RequestBody CheckEmailRequestDto requestDto) throws Exception {

        requestDto.validateFieldsNotNull();

        emailService.findPwdSendEmail(requestDto.getEmail());


        return new ResponseEntity<>(HttpStatus.OK);

    }

    /**
     * 비밀번호 찾기 이메일 인증코드 확인
     * /emails/find-password/check-key
     * */
    @GetMapping("/find-password/check-key")
    public ResponseEntity<?> findPwdCheckKey(@RequestParam("email") String email,
                                             @RequestParam("key") String key) {

        FindPasswordResponseDto responseDto = emailService.findPwdCheckKey(email, key);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
