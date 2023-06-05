package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.*;
import com.lodestar.lodestar_server.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    /**로그인
     * /users/login
     * */
    @PostMapping("/login")
    public ResponseEntity<?> login(LoginRequestDto loginRequestDto) {

        loginRequestDto.validateFieldsNotNull();

        ResponseEntity response = userService.login(loginRequestDto);

        return response;
    }

    /**회원가입
     * /users/signup
     * */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {

        signUpRequestDto.validateFieldsNotNull();

        userService.signUp(signUpRequestDto);

        SignUpResponseDto responseDto = new SignUpResponseDto("회원가입에 성공했습니다.");

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * 아이디 중복 확인
     * /users/duplicated-username?username=
     */
    @GetMapping("/duplicated-username")
    public ResponseEntity<?> checkUsername(@RequestParam("username") String username) {

        boolean result = userService.checkUsername(username);
        CheckUsernameResponseDto responseDto = new CheckUsernameResponseDto();

        if (result) {
            responseDto.setCheck(false);
            responseDto.setMessage("이미 존재하는 아이디입니다.");
            return new ResponseEntity<>(responseDto, HttpStatus.CONFLICT);
        } else {
            responseDto.setCheck(true);
            responseDto.setMessage("사용 가능한 아이디입니다.");
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
    }

    @GetMapping("/test2")
    public String test2(@RequestParam("test") String test2) {
        return test2;
    }



}
