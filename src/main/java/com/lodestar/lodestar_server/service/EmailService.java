package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.entity.Mail;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.exception.*;
import com.lodestar.lodestar_server.repository.EmailRepository;
import com.lodestar.lodestar_server.repository.UserRepository;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class EmailService {

    private final JavaMailSender emailSender;

    private final UserRepository userRepository;

    private final EmailRepository emailRepository;
    private final PasswordEncoder passwordEncoder;
    private final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$&*()[]?";

    private MimeMessage createMessage(String to, String authCode) throws Exception {
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

    private MimeMessage createFindPwdMessage(String to, String authCode) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to);//보내는 대상
        message.setSubject("LOADSTAR 임시 비밀번호");//제목

        String msgg = "";
        msgg += "<div style='margin:20px;'>";
        msgg += "<h1> 안녕하세요 LODESTAR 입니다. </h1>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>임시 비밀번호</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += authCode + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("tjsgh2946@gmail.com", "LOADSTAR"));//보내는 사람

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

    public void sendAuthCode(String to) throws Exception {

        String key = createKey();
        MimeMessage message = createMessage(to, key);
        Mail email = Mail.builder()
                .email(to)
                .authKey(key)
                .build();

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

        sendAuthCode(to);
    }


    private boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    /**
     * 이메일로 테이블 조회해서 그 이메일에 대응되는 Key값과 비교
     */
    @Transactional(readOnly = true)
    public boolean checkKey(String email, String key) {

        Mail emails = emailRepository.findFirstByEmailOrderByCreatedAtDesc(email);

        String testKey = emails.getAuthKey();

        if (key.equals(testKey) && validKeyTime(emails.getCreatedAt())) {
            return true;
        } else return false;
    }

    //생성된 시간 + 3분보다 현재가 더 작아야함
    private boolean validKeyTime(LocalDateTime createdTime) {
        LocalDateTime now = LocalDateTime.now();

        return now.isBefore(createdTime.plusMinutes(3)) && now.isAfter(createdTime);
    }

    public void findPwdSendEmail(String email, String username) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(()->new NotExistEmailException(email));
        if(!user.getUsername().equals(username))
            throw new NotExistUserException(email + ", " + username);

        sendAndSaveRandomPwd(email, user);
    }

    public void sendAndSaveRandomPwd(String to, User user) throws Exception {

        String key = createRandomPwd();
        MimeMessage message = createMessage(to, key);

        try {
            emailSender.send(message);
            user.modifyPassword(passwordEncoder.encode(key));
        } catch (MailException e) {
            throw new SendEmailFailException(to);
        }
    }



    private String createRandomPwd() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

}
