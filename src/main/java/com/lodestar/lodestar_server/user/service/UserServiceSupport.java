package com.lodestar.lodestar_server.user.service;

import com.lodestar.lodestar_server.user.exception.*;
import com.lodestar.lodestar_server.user.entity.User;
import com.lodestar.lodestar_server.user.respository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserServiceSupport {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Transactional(readOnly = false)
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void checkExistsUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException();
        }
    }

    public void checkExistsEmail(String email) {
        if (userRepository.existsByEmail(email))
            throw new DuplicateEmailException();
    }

    public void checkPasswordForLogin(String realPassword, String requestPassword) {
        if (!passwordEncoder.matches(requestPassword, realPassword))
            throw new NotMatchPasswordException();

    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundByUsernameException::new);
    }

    @Transactional(readOnly = false)
    public void setSessionAttribute(HttpSession httpSession, User user) {
        httpSession.setAttribute("user", user);
        httpSession.setAttribute("boards", new ArrayList<Long>());

    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundByIdException::new);
    }
}
