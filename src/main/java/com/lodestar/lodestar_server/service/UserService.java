package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.dto.LoginRequestDto;
import com.lodestar.lodestar_server.dto.LoginResponseDto;
import com.lodestar.lodestar_server.dto.SignUpRequestDto;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.exception.DuplicateMailException;
import com.lodestar.lodestar_server.exception.DuplicateUsernameException;
import com.lodestar.lodestar_server.exception.LoginFailException;
import com.lodestar.lodestar_server.exception.NotCheckMailException;
import com.lodestar.lodestar_server.jwt.JwtProvider;
import com.lodestar.lodestar_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private static final int ACCESS = 0;

    private static final int REFRESH = 1;


    public void signUp(SignUpRequestDto signUpRequestDto) {

        //닉네임 중복확인, 이메일 인증 되었는지.
        //1. 닉네임 중복확인 (username status = true)
        //2. 비밀번호
        //3. 이메일쓰고 인증번호받기 (아직 email status = false)
        //4. 인증코드 입력하고 맞으면 email status = true
        //5. 둘 다 true 되면 Join 활성화
        //6. Join 누르면 한 번 더 체크 해줄거냐? 안 해줄거냐 ?

        if (duplicateUsername(signUpRequestDto.getUsername()) || !signUpRequestDto.isUsernameCheck()) {
            throw new DuplicateUsernameException(signUpRequestDto.getUsername());
        }
        if (duplicateEmail(signUpRequestDto.getEmail())) {
            throw new DuplicateMailException(signUpRequestDto.getEmail());
        }
        if (!signUpRequestDto.isEmailCheck()) {
            throw new NotCheckMailException(signUpRequestDto.getEmail());
        }


        User user = new User();
        user.setUsername(signUpRequestDto.getUsername());
        user.setEmail(signUpRequestDto.getEmail());
        String password = passwordEncoder.encode(signUpRequestDto.getPassword());
        user.setPassword(password);
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        user.setRoles(roles);
        user.setRefreshTokenValue(null);

        userRepository.save(user);

    }


    public ResponseEntity<?> login(LoginRequestDto loginRequestDto) {


        if (!checkUsername(loginRequestDto.getUsername())
                || !checkPassword(loginRequestDto.getUsername(), loginRequestDto.getPassword()))
            throw new LoginFailException(loginRequestDto.getUsername() + ", " + loginRequestDto.getPassword());

        User user = userRepository.findByUsername(loginRequestDto.getUsername());
        String[] Tokens = createTokens(user, user.getRoles());

        LoginResponseDto loginResponseDto = new LoginResponseDto(user.getId(), Tokens[0]);
        HttpHeaders headers = new HttpHeaders();
        ResponseCookie cookie = ResponseCookie.from("refreshToken", Tokens[1])
                .maxAge(14 * 24 * 60 * 60)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
        headers.add(HttpHeaders.COOKIE, cookie.toString());
        return new ResponseEntity<>(loginResponseDto, headers, HttpStatus.OK);
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

    private String[] createTokens(User user, List<String> roles) {
        String accessToken = jwtProvider.createJwtAccessToken(user.getId(), roles);
        String refreshTokenValue = UUID.randomUUID().toString().replace("-", "");
        saveRefreshTokenValue(user, refreshTokenValue);
        String refreshToken = jwtProvider.createJwtRefreshToken(refreshTokenValue);
        return new String[]{accessToken, refreshToken};
    }

    private void saveRefreshTokenValue(User user, String refreshToken) {
        user.setRefreshTokenValue(refreshToken);
        userRepository.save(user);
    }


}