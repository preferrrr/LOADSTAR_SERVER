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

    @org.springframework.web.bind.annotation.ExceptionHandler({DuplicateEmailException.class})
    public ResponseEntity<?> handleDuplicateEmailException(final DuplicateEmailException e) {

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

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({SendEmailFailException.class})
    public ResponseEntity<?> handleSendEmailFailException(final SendEmailFailException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        ExceptionMessage exceptionMessage = new ExceptionMessage("메일 전송에 실패했습니다.");
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({NotCheckEmailException.class})
    public ResponseEntity<?> handleNotCheckEmailException(final NotCheckEmailException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        ExceptionMessage exceptionMessage = new ExceptionMessage("메일 인증을 해주세요.");
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({FirstQuestionFailException.class})
    public ResponseEntity<?> handleFirstQuestionFailException(final FirstQuestionFailException e) {

        String msg = e.getNAME() + ": [userId = " + e.getMessage() + "]";
        log.error(msg);

        ExceptionMessage exceptionMessage = new ExceptionMessage("질문을 저장하지 못했습니다.");
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({NotExistEmailException.class})
    public ResponseEntity<?> handleNotExistEmailException(final NotExistEmailException e) {

        String msg = e.getNAME() + ": [email = " + e.getMessage() + "]";
        log.error(msg);

        ExceptionMessage exceptionMessage = new ExceptionMessage("해당 이메일로 가입한 아이디가 없습니다.");
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({AuthFailException.class})
    public ResponseEntity<?> handleAuthFailException(final AuthFailException e) {

        String msg = e.getNAME() + ": [email = " + e.getMessage() + "]";
        log.error(msg);

        ExceptionMessage exceptionMessage = new ExceptionMessage("인증에 실패했습니다.");
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ChangePwdFailException.class})
    public ResponseEntity<?> handleChangePwdFailException(final ChangePwdFailException e) {

        String msg = e.getNAME() + ": [userId = " + e.getMessage() + "]";
        log.error(msg);

        ExceptionMessage exceptionMessage = new ExceptionMessage("비밀번호 변경에 실패했습니다.");
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<?> handleInvalidTokenException(final InvalidTokenException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        ExceptionMessage exceptionMessage = new ExceptionMessage("잘못된 접근입니다.");
        return new ResponseEntity<>(exceptionMessage, HttpStatus.FORBIDDEN);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ExistUsernameException.class})
    public ResponseEntity<?> handleExistUsernameException(final ExistUsernameException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        ExceptionMessage exceptionMessage = new ExceptionMessage("이미 존재하는 아이디입니다.");
        return new ResponseEntity<>(exceptionMessage, HttpStatus.CONFLICT);
    }

}
