package com.lodestar.lodestar_server.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {
    //토큰이 만료되었거나, 유효하지 않은 값이 들어왔을 때 JwtAuthenticationFilter에서 ExceptionHandler를 사용해서 예외를 던지려고 했는데,
    //ExceptionHandler는 SecurityFilter에서 발생한 예외를 핸들링 해주지 못함.
    //filter는 dispatcher servlet보다 앞에 존재하고, handler intercepter는 뒤에 존재하기 때문에
    //filter에서 보낸 예외는 Exception Handler로 처리하지 못함.
    //따라서, 새로운 Filter를 정의해서 Filter Chain에 추가해주어야 함.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) { {
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }}
    }

}
