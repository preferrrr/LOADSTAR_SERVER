package com.lodestar.lodestar_server.service;

import com.lodestar.lodestar_server.dto.*;
import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.exception.*;
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
import java.util.Optional;
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


    public User signUp(SignUpRequestDto signUpRequestDto) {

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
            throw new DuplicateEmailException(signUpRequestDto.getEmail());
        }
        if (!signUpRequestDto.isEmailCheck()) {
            throw new NotCheckEmailException(signUpRequestDto.getEmail());
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

        return userRepository.save(user);

    }


    public ResponseEntity<?> login(LoginRequestDto loginRequestDto) {

        if (!checkUsername(loginRequestDto.getUsername())
                || !checkPassword(loginRequestDto.getUsername(), loginRequestDto.getPassword()))
            throw new LoginFailException(loginRequestDto.getUsername() + ", " + loginRequestDto.getPassword());

        User user = userRepository.findByUsername(loginRequestDto.getUsername());
        String[] Tokens = createTokens(user, user.getRoles());

        LoginResponseDto loginResponseDto = new LoginResponseDto(user.getId(), "로그인에 성공했습니다.");
        HttpHeaders headers = new HttpHeaders();
        ResponseCookie cookie = ResponseCookie.from("X-REFRESH-TOKEN", Tokens[1])
                .maxAge(14 * 24 * 60 * 60)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
        headers.add(HttpHeaders.COOKIE, cookie.toString());
        headers.set("X-ACCESS-TOKEN", Tokens[0]);
        return new ResponseEntity<>(loginResponseDto, headers, HttpStatus.OK);
    }

    public void dupCheckUsername(String username) {
        boolean result = checkUsername(username);
        if(result) {
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

    private String[] createTokens(User user, List<String> roles) {
        String accessToken = jwtProvider.createJwtAccessToken(user.getId(), roles);
        String refreshTokenValue = UUID.randomUUID().toString().replace("-", "");
        saveRefreshTokenValue(user, refreshTokenValue);
        String refreshToken = jwtProvider.createJwtRefreshToken(user.getId(), refreshTokenValue);
        return new String[]{accessToken, refreshToken};
    }

    private void saveRefreshTokenValue(User user, String refreshToken) {
        user.setRefreshTokenValue(refreshToken);
        userRepository.save(user);
    }

    public void firstQuestion(FirstQuestionRequestDto requestDto) {
        Optional<User> findUser = userRepository.findById(requestDto.getUserId());
        User user;
        if (findUser.isPresent()) {
            user = findUser.get();
        } else {
            throw new FirstQuestionFailException(String.valueOf(requestDto.getUserId()));
        }

        user.setFront_back(requestDto.getFrontBack());
        user.setMajor(requestDto.getMajor());
        user.setCurrent(requestDto.getCurrent());
        user.setMonth(requestDto.getMonth());
        user.setYear(requestDto.getYear());

    }


    public String findId(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new NotExistEmailException(email);
        } else {
            User user = userRepository.findByEmail(email);
            String username = user.getUsername();

            return encryptPassword(username);
        }
    }

    public void changePassword(FindPasswordRequestDto requestDto) {
        Optional<User> findUser = userRepository.findById(requestDto.getUserId());
        User user;
        if (findUser.isPresent()) {
            user = findUser.get();
        } else {
            throw new ChangePwdFailException(String.valueOf(requestDto.getUserId()));
        }
        System.out.println(requestDto.getPassword());
        String password = passwordEncoder.encode(requestDto.getPassword());
        user.setPassword(password);

    }


    private String encryptPassword(String username) {
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

    public MyPageResponseDto myPage(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);

        if(findUser.isEmpty()) {
            throw new AuthFailException(String.valueOf(userId));
        } else {
            User user = findUser.get();
            MyPageResponseDto responseDto = new MyPageResponseDto();
            responseDto.setEmail(user.getEmail());
            responseDto.setUsername(user.getUsername());

            return responseDto;
        }

    }
}
