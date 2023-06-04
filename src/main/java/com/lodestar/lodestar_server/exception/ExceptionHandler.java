package com.lodestar.lodestar_server.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler({DuplicateUsernameException.class})
    public ResponseEntity<?> handleDuplicateUsernameException(final DuplicateUsernameException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        ExceptionMessage exceptionMessage = new ExceptionMessage("이미 존재하는 아이디입니다.");

        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({DuplicateMailException.class})
    public ResponseEntity<?> handleDuplicateMailException(final DuplicateMailException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        ExceptionMessage exceptionMessage = new ExceptionMessage("이미 존재하는 이메일입니다.");

        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({LoginFailException.class})
    public ResponseEntity<?> handleLoginFailException(final LoginFailException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        ExceptionMessage exceptionMessage = new ExceptionMessage("아이디 혹은 비밀번호가 틀렸습니다.");

        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({InvalidRequestParameterException.class})
    public ResponseEntity<?> handleInvalidRequestParameterException(final InvalidRequestParameterException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        ExceptionMessage exceptionMessage = new ExceptionMessage("비어있는 값입니다.");
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NO_CONTENT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({SendMailFailException.class})
    public ResponseEntity<?> handleSendMailFailException(final SendMailFailException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        ExceptionMessage exceptionMessage = new ExceptionMessage("메일 전송에 실패했습니다.");
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({NotCheckMailException.class})
    public ResponseEntity<?> handleNotCheckMailException(final NotCheckMailException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        ExceptionMessage exceptionMessage = new ExceptionMessage("메일 인증을 해주세요.");
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

}
