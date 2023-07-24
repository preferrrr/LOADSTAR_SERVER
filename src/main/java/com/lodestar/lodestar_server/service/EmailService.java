package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.dto.response.FindPasswordResponseDto;
import com.lodestar.lodestar_server.entity.Mail;
import com.lodestar.lodestar_server.exception.AuthFailException;
import com.lodestar.lodestar_server.exception.DuplicateEmailException;
import com.lodestar.lodestar_server.exception.NotExistEmailException;
import com.lodestar.lodestar_server.exception.SendEmailFailException;
import com.lodestar.lodestar_server.repository.EmailRepository;
import com.lodestar.lodestar_server.repository.UserRepository;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class EmailService {

    private final JavaMailSender emailSender;

    private final UserRepository userRepository;

    private final EmailRepository emailRepository;

    private MimeMessage createMessage(String to, String authCode) throws Exception {
        System.out.println("보내는 대상 : " + to);
        System.out.println("인증 코드 : " + authCode);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);//보내는 대상
        message.setSubject("LODESTAR 인증 코드");//제목

        String msgg = "";
        msgg += "<div style='margin:20px;'>";
        msgg += "<h1> 안녕하세요 LODESTAR 입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 복사해 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다.<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += authCode + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("tjsgh2946@gmail.com", "LODESTAR"));//보내는 사람

        return message;
    }

    public static String createKey() {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((char) ((int) (rnd.nextInt(10)) + 48));
            // 0~9 (ex. 1+48=49 => (char)49 = '1')
        }

        return key.toString();
    }

    public void sendEmail(String to) throws Exception {

        String key = createKey();
        MimeMessage message = createMessage(to, key);
        Mail email = new Mail();
        email.setEmail(to);
        email.setAuthKey(key);

        try {
            emailSender.send(message);
            emailRepository.save(email);
        } catch (MailException e) {
            throw new SendEmailFailException(to);
        }
    }

    /**
     * 회원가입할 때 이메일 중복체크, 아니라면 이메일에 인증코드 보냄
     */
    public void checkEmail(String to) throws Exception {
        if (existEmail(to)) {
            throw new DuplicateEmailException(to);
        }

        sendEmail(to);
    }


    private boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    /**
     * 이메일로 테이블 조회해서 그 이메일에 대응되는 Key값과 비교
     */
    public boolean checkKey(String email, String key) {

        List<Mail> emails = emailRepository.findByEmail(email);
        Mail testEmail = emails.get(emails.size() - 1);
        String testKey = testEmail.getAuthKey();

        if (key.equals(testKey) && validKeyTime(testEmail.getCreatedAt())) {
            return true;
        } else return false;
    }

    //생성된 시간 + 3분보다 현재가 더 작아야함
    private boolean validKeyTime(LocalDateTime createdTime) {
        LocalDateTime now = LocalDateTime.now();

        return now.isBefore(createdTime.plusMinutes(3)) && now.isAfter(createdTime);
    }

    public void findPwdSendEmail(String email) throws Exception{
        if (!existEmail(email)) {
            throw new NotExistEmailException(email);
        }

        sendEmail(email);
    }

    public FindPasswordResponseDto findPwdCheckKey(String email, String key) {

        boolean result = checkKey(email, key);

        if(result) {
            FindPasswordResponseDto responseDto = new FindPasswordResponseDto();
            responseDto.setUserId(userRepository.findByEmail(email).getId());
            responseDto.setResult(true);
            return responseDto;
        } else {
            throw new AuthFailException(email);
        }
    }


}
