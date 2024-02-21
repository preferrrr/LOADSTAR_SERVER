package com.lodestar.lodestar_server.email.service;

import com.lodestar.lodestar_server.email.entity.Mail;
import com.lodestar.lodestar_server.email.exception.NotInvalidAuthenticationTime;
import com.lodestar.lodestar_server.email.exception.NotMatchedAuthenticationKey;
import com.lodestar.lodestar_server.email.exception.NotMatchedUsernameException;
import com.lodestar.lodestar_server.email.repository.MailRepository;
import com.lodestar.lodestar_server.user.entity.User;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MailServiceSupport {

    private final JavaMailSender emailSender;
    private final MailRepository mailRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$&*()[]?";

    public MimeMessage createMessage(String to, String authCode) throws Exception {
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

    public MimeMessage createFindPasswordMessage(String to, String modifiedPassword) throws Exception {
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
        msgg += modifiedPassword + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("tjsgh2946@gmail.com", "LOADSTAR"));//보내는 사람

        return message;
    }

    public String createKey() {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((char) ((int) (rnd.nextInt(10)) + 48));
        }

        return key.toString();
    }

    public void sendMail(MimeMessage message) {
        emailSender.send(message);
    }

    @Transactional(readOnly = false)
    public void saveMail(Mail mail) {
        mailRepository.save(mail);
    }

    public Mail getLatestMailByEmail(String email) {
        return mailRepository.findFirstByEmailOrderByCreatedAtDesc(email);
    }

    public void checkIsMatchedAuthKey(String key, String authenticationKey) {
        if (!key.equals(authenticationKey))
            throw new NotMatchedAuthenticationKey();
    }

    public void checkIsValidAuthTime(LocalDateTime createdAt, LocalDateTime now) {
        if (now.isAfter(createdAt.plusMinutes(3)) && now.isBefore(createdAt))
            throw new NotInvalidAuthenticationTime();
    }

    public void checkIsMatchedUsername(String requestUsername, String username) {
        if(!requestUsername.equals(username))
            throw new NotMatchedUsernameException();
    }

    public String createRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    @Transactional(readOnly = false)
    public void modifyPassword(User user, String modifiedPassword) {
        user.modifyPassword(passwordEncoder.encode(modifiedPassword));
    }
}
