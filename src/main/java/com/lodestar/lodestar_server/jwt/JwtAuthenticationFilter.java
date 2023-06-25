package com.lodestar.lodestar_server.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String[] tokens = jwtProvider.resolveToken(request);

        //TODO: 토큰 인증, 인가 방식 다시 해야함.
        int accessTokenValid = jwtProvider.isAccessTokenValid(tokens[0]);
        //1 : access 토큰 유효
        //2 : access 토큰 만료
        //0 : 잘못된 접근. 접근 제한
        if (tokens[0] != null && accessTokenValid == 1) {
            Authentication authentication = jwtProvider.getAuthentication(tokens[0]);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //System.out.println(authentication.toString());
        } else if(accessTokenValid == 2) { // 엑세스 토큰이 만료됐으면 header에 다시 실어서 보내줌
            //access이 만료됐으면
            //refresh 토큰 유효한지 확인
            //유효하다면 access 토큰 새로 발급
            //setHeader로 access 토큰 실어주기
            //refresh 토큰 유효하지 않으면 throw new 다시 로그인
            response.setHeader("X-ACCESS-TOKEN", jwtProvider.refreshToken(tokens[1]));
            Authentication authentication = jwtProvider.getAuthentication(tokens[1]);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 리프레시 토큰도 유효하다면 권한 줘서 인증 상태 유지되도록 함.
        }
        filterChain.doFilter(request, response);
    }
}
