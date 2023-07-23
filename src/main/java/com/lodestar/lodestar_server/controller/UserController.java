package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.*;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.exception.AuthFailException;
import com.lodestar.lodestar_server.jwt.JwtProvider;
import com.lodestar.lodestar_server.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
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
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {

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

        User user = userService.signUp(signUpRequestDto);

        SignUpResponseDto responseDto = new SignUpResponseDto("회원가입에 성공했습니다.", user.getId());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * 아이디 중복 확인
     * /users/duplicated-username?username=
     */
    @GetMapping("/duplicated-username")
    public ResponseEntity<?> checkUsername(@RequestParam("username") String username) {

        userService.dupCheckUsername(username);
        CheckUsernameResponseDto responseDto = new CheckUsernameResponseDto();

        responseDto.setCheck(true);
        responseDto.setMessage("사용 가능한 아이디입니다.");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }


    /**
     * 아이디 찾기
     * /users/find-id
     * */
    @GetMapping("/find-id")
    public ResponseEntity<?> findId(@RequestParam("email") String email) {
        String username = userService.findId(email);
        MessageResponseDto responseDto = new MessageResponseDto(username);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 비밀번호 찾기 인증 후 변경
     * /users/find-password
     * */
    @PatchMapping("/find-password")
    public ResponseEntity<?> findPassword(@RequestBody FindPasswordRequestDto requestDto) {

        requestDto.validateFieldsNotNull();

        userService.changePassword(requestDto);
        MessageResponseDto responseDto = new MessageResponseDto("비밀번호 변경에 성공했습니다.");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    /**
     * 마이페이지
     * /users/my-page
     * */

    @GetMapping("/my-page")
    public ResponseEntity<?> myPage(@AuthenticationPrincipal User user) {

        MyPageResponseDto responseDto = userService.myPage(user);

        return new ResponseEntity<>(responseDto,HttpStatus.OK);

    }

    /**access token 재발급
     * /users/token*/
    @GetMapping("/token")
    public ResponseEntity<?> reissueToken(@RequestHeader(value = "X-REFRESH-TOKEN") String refreshToken) {

        HttpHeaders headers = userService.reissueToken(refreshToken);

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }



}
