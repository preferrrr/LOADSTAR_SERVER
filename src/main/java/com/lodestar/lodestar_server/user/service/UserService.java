package com.lodestar.lodestar_server.user.service;

import com.lodestar.lodestar_server.user.dto.request.ModifyPasswordRequestDto;
import com.lodestar.lodestar_server.user.dto.request.LoginRequestDto;
import com.lodestar.lodestar_server.user.dto.request.SignUpRequestDto;
import com.lodestar.lodestar_server.user.dto.response.FindIdResponseDto;
import com.lodestar.lodestar_server.user.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserServiceSupport userServiceSupport;

    @Transactional(readOnly = false)
    public void signUp(SignUpRequestDto signUpRequestDto) {

        //닉네임 중복확인, 이메일 인증 되었는지.
        //1. 닉네임 중복확인 (username status = true)
        //2. 비밀번호
        //3. 이메일쓰고 인증번호받기 (아직 email status = false)
        //4. 인증코드 입력하고 맞으면 email status = true
        //5. 둘 다 true 되면 Join 활성화
        //6. Join 누르면 한 번 더 체크 해줄거냐? 안 해줄거냐 ?

        userServiceSupport.checkExistsUsername(signUpRequestDto.getUsername());

        userServiceSupport.checkExistsEmail(signUpRequestDto.getEmail());

        String encryptedPassword = userServiceSupport.encryptPassword(signUpRequestDto.getPassword());

        User user = User.create(signUpRequestDto.getUsername(), encryptedPassword, signUpRequestDto.getEmail(), List.of("USER"));

        userServiceSupport.saveUser(user);

    }


    @Transactional(readOnly = false)
    public void login(HttpSession httpSession, LoginRequestDto loginRequestDto) {

        User user = userServiceSupport.getUserByUsername(loginRequestDto.getUsername());

        userServiceSupport.checkPasswordForLogin(user.getPassword(), loginRequestDto.getPassword());

        userServiceSupport.setSessionAttribute(httpSession, user);

    }


    public void checkExistsUsername(String username) {
        userServiceSupport.checkExistsUsername(username);
    }


    public FindIdResponseDto findId(String email) {

        User user = userServiceSupport.getUserByEmail(email);

        return FindIdResponseDto.of(user.getUsername());
    }


    @Transactional(readOnly = false)
    public void modifyPassword(User me, ModifyPasswordRequestDto requestDto) {

        User user = userServiceSupport.getUserById(me.getId());

        userServiceSupport.checkPasswordForLogin(user.getPassword(), requestDto.getCurrentPassword());

        String modifyPassword = userServiceSupport.encryptPassword(requestDto.getModifyPassword());

        user.modifyPassword(modifyPassword);

    }


    @Transactional(readOnly = false)
    public void logout(HttpSession httpSession) {
        httpSession.invalidate();
    }
}
