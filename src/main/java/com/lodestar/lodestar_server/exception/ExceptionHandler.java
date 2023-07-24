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


        return new ResponseEntity<>(HttpStatus.CONFLICT); /**409*/
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({DuplicateEmailException.class})
    public ResponseEntity<?> handleDuplicateEmailException(final DuplicateEmailException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        return new ResponseEntity<>(HttpStatus.CONFLICT); /**409*/
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({LoginFailException.class})
    public ResponseEntity<?> handleLoginFailException(final LoginFailException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); /**400*/
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

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); /**500,메일 전송 실패 서버 에러로 간주*/
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({NotCheckEmailException.class})
    public ResponseEntity<?> handleNotCheckEmailException(final NotCheckEmailException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); /**400 */
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({NotExistEmailException.class})
    public ResponseEntity<?> handleNotExistEmailException(final NotExistEmailException e) {

        String msg = e.getNAME() + ": [email = " + e.getMessage() + "]";
        log.error(msg);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);/**400, 해당 이메일로 가입한 유저 없음.*/
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({AuthFailException.class})
    public ResponseEntity<?> handleAuthFailException(final AuthFailException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        return new ResponseEntity<>(HttpStatus.FORBIDDEN); /**403, 권한 없음.*/
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ChangePwdFailException.class})
    public ResponseEntity<?> handleChangePwdFailException(final ChangePwdFailException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);/**400 */
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<?> handleInvalidTokenException(final InvalidTokenException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); /**401, 토큰을 보내지 않았거나, 만료, 유효하지 않음.*/
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ExistUsernameException.class})
    public ResponseEntity<?> handleExistUsernameException(final ExistUsernameException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        return new ResponseEntity<>(HttpStatus.CONFLICT); /**409, 중복된 아이디 존재.*/
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> handleExistUsernameException(final NotFoundException e) {

        String msg = e.getNAME() + ": [" + e.getMessage() + "]";
        log.error(msg);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND); /**404, db 조회 실패.*/
    }

}
