package com.lodestar.lodestar_server.email.service;

import com.lodestar.lodestar_server.email.dto.request.FindPwdRequestDto;
import com.lodestar.lodestar_server.email.entity.Mail;
import com.lodestar.lodestar_server.user.entity.User;
import com.lodestar.lodestar_server.user.service.UserServiceSupport;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MailService {

    private final MailServiceSupport mailServiceSupport;
    private final UserServiceSupport userServiceSupport;

    @Transactional(readOnly = false)
    public void checkEmail(String to) throws Exception {

        //이미 존재하는 이메일인지 체크
        userServiceSupport.checkExistsEmail(to);

        //인증키
        String authKey = mailServiceSupport.createKey();

        //메일 내용
        MimeMessage message = mailServiceSupport.createMessage(to, authKey);

        Mail email = Mail.create(to, authKey);

        //메일 전송
        mailServiceSupport.sendMail(message);

        //메일 저장
        mailServiceSupport.saveMail(email);

    }


    public void checkAuthenticationKey(String email, String userKey) {

        Mail mail = mailServiceSupport.getLatestMailByEmail(email);

        //사용자가 입력한 키가 인증키와 같은지 확인
        mailServiceSupport.checkIsMatchedAuthKey(userKey, mail.getAuthKey());

        //제한시간 안에 입력했는지 확인
        mailServiceSupport.checkIsValidAuthTime(mail.getCreatedAt(), LocalDateTime.now());

    }


    @Transactional(readOnly = false)
    public void sendMailToFindPassword(FindPwdRequestDto requestDto) throws Exception {

        User user = userServiceSupport.getUserByEmail(requestDto.getEmail());

        //입력한 username이 일치하는지 확인
        mailServiceSupport.checkIsMatchedUsername(user.getUsername(), requestDto.getUsername());

        //바꿀 비밀번호
        String modifiedPassword = mailServiceSupport.createRandomPassword();

        //바꿀 비밀번호가 포함된 메일 메시지
        MimeMessage message = mailServiceSupport.createFindPasswordMessage(user.getEmail(), modifiedPassword);

        //비밀번호 변경
        mailServiceSupport.modifyPassword(user, modifiedPassword);

        //메일 전송
        mailServiceSupport.sendMail(message);

    }

}
