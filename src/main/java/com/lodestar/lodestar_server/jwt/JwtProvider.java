package com.lodestar.lodestar_server.jwt;

import com.lodestar.lodestar_server.entity.User;
import com.lodestar.lodestar_server.exception.AuthFailException;
import com.lodestar.lodestar_server.exception.InvalidTokenException;
import com.lodestar.lodestar_server.repository.UserRepository;
import com.lodestar.lodestar_server.service.CustomUserDetailsService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {


    @Value("${jwt.secret}")
    private String secretKey;
    //private final long ACCESS_TOKEN_VALID_TIME = 30 * 60 * 1000L;   // 30분
    private final long ACCESS_TOKEN_VALID_TIME =  2 * 30 * 60 * 1000L;   // 60분
    private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 14 * 1000L;   // 2주

    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(Long userId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put("roles", roles);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(Long userId, String value) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put("value", value);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        if (request.getHeader("X-ACCESS-TOKEN") != null)
            return request.getHeader("X-ACCESS-TOKEN");

        return null;
    }

    public Authentication getAuthentication(String token) {
        User user = new User();
        String userId = getUserId(token);
        user.setId(Long.parseLong(userId));
        user.setRoles(getRoles(token));
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    public String getUserId(String token) {
        return getClaimsFromJwt(token).getBody().getSubject();
    }

    public List<String> getRoles(String token) {
        return (ArrayList<String>) getClaimsFromJwt(token).getBody().get("roles");
    }

    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> claims = getClaimsFromJwt(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new JwtException("[expired jwt] token : " + token);
        } catch (SecurityException e) {
            throw new JwtException("[wrong signature] token : " + token);
        } catch (MalformedJwtException e) {
            throw new JwtException("[invalid jwt] token : " + token);
        } catch (UnsupportedJwtException e) {
            throw new JwtException("[unsupported JWT] token : " + token);
        }
    }

    public String refreshToken(String refresh) {
        try {
            Jws<Claims> claims = getClaimsFromJwt(refresh);
            Long userId = Long.parseLong(claims.getBody().getSubject());
            String value = String.valueOf(claims.getBody().get("value"));
            Optional<User> findUser = userRepository.findById(userId);
            User user;
            if(findUser.isPresent())
                user = findUser.get();
            else
                throw new AuthFailException(String.valueOf(userId));

            if (!value.equals(user.getRefreshTokenValue())){
                throw new InvalidTokenException(String.valueOf(userId));

            }
            String accessToken = createAccessToken(userId, user.getRoles());
            return accessToken;

        } catch (ExpiredJwtException e) {
            //리프레시 토큰 만료되면
            throw new AuthFailException("리프레시 토큰 만료");
        } catch (Exception e) {
            throw new AuthFailException("인증에 실패했습니다.");
        }
    }

    public Jws<Claims> getClaimsFromJwt(String jwtToken) throws JwtException {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
    }

}
