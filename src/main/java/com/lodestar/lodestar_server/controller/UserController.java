package com.lodestar.lodestar_server.controller;

import com.lodestar.lodestar_server.dto.request.FindPasswordRequestDto;
import com.lodestar.lodestar_server.dto.request.LoginRequestDto;
import com.lodestar.lodestar_server.dto.request.SignUpRequestDto;
import com.lodestar.lodestar_server.dto.response.MessageResponseDto;
import com.lodestar.lodestar_server.dto.response.MyPageResponseDto;
import com.lodestar.lodestar_server.entity.User;

import com.lodestar.lodestar_server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
            @ApiResponse(responseCode = "400", description = "아이디 혹은 비밀번호 틀림")
    })
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto,
                                   HttpSession httpSession) {

        loginRequestDto.validateFieldsNotNull();

        ResponseEntity response = userService.login(httpSession, loginRequestDto);

        return response;
    }

    /**회원가입
     * /users/signup
     * */
    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
            @ApiResponse(responseCode = "400", description = "이메일 인증을 하지 않음"),
            @ApiResponse(responseCode = "409", description = "중복된 이메일이나 아이디 존재")

    })
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {

        signUpRequestDto.validateFieldsNotNull();

        userService.signUp(signUpRequestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 아이디 중복 확인
     * /users/duplicated-username?username=
     */
    @GetMapping("/duplicated-username")
    @Operation(summary = "아이디 중복 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "409", description = "중복되는 아이디 존재")

    })
    public ResponseEntity<?> checkUsername(@Schema(name = "아이디") @RequestParam("username") String username) {

        userService.dupCheckUsername(username);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * 아이디 찾기
     * /users/find-id
     * */
    @GetMapping("/find-id")
    @Operation(summary = "아이디 중복 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "해당 이메일로 가입한 유저 없음")
    })//TODO: 아이디 찾기도 이메일 인증 필요하도록 ?
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
    @Operation(summary = "비밀번호 찾기 인증 후 변경, 추후에 지울 수도")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400")
    })//TODO: 비밀번호 찾기 => 이메일로 임시 비밀번호 보낸다면 이거 필요없음.
    public ResponseEntity<?> findPassword(@RequestBody FindPasswordRequestDto requestDto) {

        requestDto.validateFieldsNotNull();

        userService.changePassword(requestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * 마이페이지
     * /users/my-page
     * */
    @Operation(summary = "마이페이지")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "403", description = "잘못된 접근")
    })
    @GetMapping("/my-page")
    public ResponseEntity<?> myPage(@AuthenticationPrincipal User user) {

        MyPageResponseDto responseDto = userService.myPage(user);

        return new ResponseEntity<>(responseDto,HttpStatus.OK);

    }



}
