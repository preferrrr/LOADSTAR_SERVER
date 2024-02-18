package com.lodestar.lodestar_server.user.service;

import com.lodestar.lodestar_server.user.dto.request.ModifyPasswordRequestDto;
import com.lodestar.lodestar_server.user.dto.request.LoginRequestDto;
import com.lodestar.lodestar_server.user.dto.request.SignUpRequestDto;
import com.lodestar.lodestar_server.user.dto.response.FindIdResponseDto;
import com.lodestar.lodestar_server.user.dto.response.LoginResponseDto;
import com.lodestar.lodestar_server.user.entity.User;
import com.lodestar.lodestar_server.exception.*;
import com.lodestar.lodestar_server.board.repository.BoardRepository;
import com.lodestar.lodestar_server.comment.repository.CommentRepository;
import com.lodestar.lodestar_server.user.respository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpRequestDto signUpRequestDto) {

        //닉네임 중복확인, 이메일 인증 되었는지.
        //1. 닉네임 중복확인 (username status = true)
        //2. 비밀번호
        //3. 이메일쓰고 인증번호받기 (아직 email status = false)
        //4. 인증코드 입력하고 맞으면 email status = true
        //5. 둘 다 true 되면 Join 활성화
        //6. Join 누르면 한 번 더 체크 해줄거냐? 안 해줄거냐 ?

        if (duplicateUsername(signUpRequestDto.getUsername())) {
            throw new DuplicateUsernameException(signUpRequestDto.getUsername());
        }
        if (duplicateEmail(signUpRequestDto.getEmail())) {
            throw new DuplicateEmailException(signUpRequestDto.getEmail());
        }
        if (!signUpRequestDto.isEmailCheck() || !signUpRequestDto.isUsernameCheck()) {
            throw new NotCheckException(signUpRequestDto.getEmail() + ", " + signUpRequestDto.getUsername());
        }

        String password = passwordEncoder.encode(signUpRequestDto.getPassword());
        List<String> roles = new ArrayList<>();
        roles.add("USER");

        User user = User.builder()
                .username(signUpRequestDto.getUsername())
                .email(signUpRequestDto.getEmail())
                .password(password)
                .roles(roles)
                .build();

        userRepository.save(user);

    }


    public ResponseEntity<LoginResponseDto> login(HttpSession httpSession, LoginRequestDto loginRequestDto) {

        if (!checkUsername(loginRequestDto.getUsername())
                || !checkPassword(loginRequestDto.getUsername(), loginRequestDto.getPassword()))
            throw new LoginFailException(loginRequestDto.getUsername() + ", " + loginRequestDto.getPassword());

        User user = userRepository.findByUsername(loginRequestDto.getUsername());

        List<Long> boardList = new ArrayList<>();

        httpSession.setAttribute("user", user);
        httpSession.setAttribute("boards", boardList);

        LoginResponseDto responseDto = LoginResponseDto.builder()
                .userId(user.getId())
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public void dupCheckUsername(String username) {
        boolean result = checkUsername(username);
        if (result) {
            throw new ExistUsernameException(username);
        }
    }

    public boolean checkUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean checkPassword(String username, String password) {
        User checkUser = userRepository.findByUsername(username);
        return passwordEncoder.matches(password, checkUser.getPassword());
    }

    private boolean duplicateUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean duplicateEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    public FindIdResponseDto findId(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotExistEmailException(email));

        FindIdResponseDto response = FindIdResponseDto.builder()
                .message(encryptUsername(user.getUsername()))
                .build();

        return response;
    }

    public void modifyPassword(User me, ModifyPasswordRequestDto requestDto) {

        Optional<User> findUser = userRepository.findById(me.getId());
        User user;
        if (findUser.isPresent()) {
            user = findUser.get();
        } else {
            throw new NotFoundException("[modify password] userId : " + me.getId());
        }

        if(!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword()))
            throw new ModifyPwdFailException("userId : " + user.getId());

        String modifyPassword = passwordEncoder.encode(requestDto.getModifyPassword());

        user.modifyPassword(modifyPassword);

    }


    private String encryptUsername(String username) {
        int length = username.length();
        int replaceLength = length / 3; // 문자열의 3분의 1 길이 계산

        StringBuilder sb = new StringBuilder(username); // 변경 가능한 문자열로 변환

        // 뒤에서 3분의 1 길이만큼 *로 대체
        for (int i = Math.max(length - replaceLength, 0); i < length; i++) {
            sb.setCharAt(i, '*');
        }

        String modifiedStr = sb.toString();

        return modifiedStr;
    }


    public void logout(HttpSession httpSession) {
        httpSession.invalidate();
    }
}
