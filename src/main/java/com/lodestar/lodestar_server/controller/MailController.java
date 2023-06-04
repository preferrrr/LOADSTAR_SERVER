package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.CheckKeyResponseDto;
import com.lodestar.lodestar_server.dto.CheckMailResponseDto;
import com.lodestar.lodestar_server.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mails")
@AllArgsConstructor
public class MailController {

    private final MailService mailService;

    /**
     * 이메일 인증코드 전송, 중간에 중복체크도 함
     * /mails/check-mail
     * */
    @PostMapping("/check-mail")
    public ResponseEntity<?> checkMail(@RequestParam("mail") String mail) throws Exception {

        System.out.println("##################################################");
        mailService.sendMail(mail);
        CheckMailResponseDto responseDto = new CheckMailResponseDto("메일 전송이 완료되었습니다.");

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    /**
     * 인증코드 확인
     * /mails/check-key?mail= ?key=
     * */
    @GetMapping("/check-key")
    public ResponseEntity<?> checkKey(@RequestParam("mail") String mail,
                                      @RequestParam("key") String key) {

        if (mailService.checkKey(mail, key)) {
            CheckKeyResponseDto responseDto = new CheckKeyResponseDto(true, "인증에 성공했습니다.");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        else {
            CheckKeyResponseDto responseDto = new CheckKeyResponseDto(false, "인증에 실패했습니다.");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

}
