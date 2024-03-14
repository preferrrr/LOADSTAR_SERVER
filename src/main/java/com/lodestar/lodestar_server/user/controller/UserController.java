package com.lodestar.lodestar_server.user.controller;

import com.lodestar.lodestar_server.common.response.BaseResponse;
import com.lodestar.lodestar_server.common.response.DataResponse;
import com.lodestar.lodestar_server.user.dto.request.ModifyPasswordRequestDto;
import com.lodestar.lodestar_server.user.dto.request.LoginRequestDto;
import com.lodestar.lodestar_server.user.dto.request.SignUpRequestDto;
import com.lodestar.lodestar_server.user.dto.response.FindIdResponseDto;

import com.lodestar.lodestar_server.user.entity.User;
import com.lodestar.lodestar_server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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


    /**
     * 로그인
     * /users/login
     */
    @PostMapping("/login")
    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
            @ApiResponse(responseCode = "400", description = "아이디 혹은 비밀번호 틀림")
    })
    public ResponseEntity<BaseResponse> login(@RequestBody @Valid LoginRequestDto loginRequestDto,
                                              HttpSession httpSession) {

        userService.login(httpSession, loginRequestDto);

        return ResponseEntity.ok(
                BaseResponse.of(HttpStatus.OK)
        );
    }

    /**
     * 회원가입
     * /users/signup
     */
    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "204", description = "body null 존재"),
            @ApiResponse(responseCode = "400", description = "이메일 인증을 하지 않음"),
            @ApiResponse(responseCode = "409", description = "중복된 이메일이나 아이디 존재")

    })
    public ResponseEntity<BaseResponse> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {

        userService.signUp(signUpRequestDto);

        return new ResponseEntity<>(
                BaseResponse.of(HttpStatus.CREATED),
                HttpStatus.CREATED
        );
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
    public ResponseEntity<BaseResponse> checkUsername(@Schema(name = "아이디") @RequestParam("username") String username) {

        userService.checkExistsUsername(username);

        return ResponseEntity.ok(
                BaseResponse.of(HttpStatus.OK)
        );
    }


    /**
     * 아이디 찾기
     * /users/find-id
     */
    @GetMapping("/find-id")
    @Operation(summary = "아이디 찾기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "해당 이메일로 가입한 유저 없음")
    })
    public ResponseEntity<DataResponse<FindIdResponseDto>> findId(@RequestParam("email") String email) {

        return ResponseEntity.ok(
                DataResponse.of(HttpStatus.OK, userService.findId(email))
        );
    }

    /**
     * 비밀번호 변경
     * /users/password
     */
    @PatchMapping("/password")
    @Operation(summary = "비밀번호 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "현재 비밀번호 틀림.")
    })
    public ResponseEntity<BaseResponse> modifyPassword(@AuthenticationPrincipal User user, @RequestBody @Valid ModifyPasswordRequestDto requestDto) {


        userService.modifyPassword(user, requestDto);

        return ResponseEntity.ok(
                BaseResponse.of(HttpStatus.OK)
        );
    }


    @GetMapping("/logout")
    public ResponseEntity<BaseResponse> logout(HttpSession httpSession) {

        userService.logout(httpSession);

        return ResponseEntity.ok(
                BaseResponse.of(HttpStatus.OK)
        );
    }


}
