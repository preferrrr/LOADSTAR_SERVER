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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {


    @Value("${jwt.secret}")
    private String secretKey;
    //private final long ACCESS_TOKEN_VALID_TIME = 30 * 60 * 1000L;   // 30분
    private final long ACCESS_TOKEN_VALID_TIME = 30 * 60 * 1000L;   // 30분
    private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 14 * 1000L;   // 2주

    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createJwtAccessToken(Long userId, List<String> roles) {
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

    public String createJwtRefreshToken(Long userId, String value) {
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

    public String[] resolveToken(HttpServletRequest request) {
        String access = request.getHeader("X-ACCESS-TOKEN");
        String refresh = request.getHeader("X-REFRESH-TOKEN");
        String[] tokens = {access, refresh};

        return tokens;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
        //System.out.println("this.getId() : " + this.getUserId(token));
        //System.out.println("userDetails.getAuth : " + userDetails.getAuthorities().toString());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserId(String token) {
        return getClaimsFromJwtToken(token).getBody().getSubject();
    }

    public int isAccessTokenValid(String access) {
        try {
            Jws<Claims> claims = getClaimsFromJwtToken(access);
            return 1;
        } catch (ExpiredJwtException e) {
            return 2;
        }
        catch (Exception e) {
            return 0;
        }
    }

    public String refreshToken(String refresh) {
        try {
            Jws<Claims> claims = getClaimsFromJwtToken(refresh);
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
            String accessToken = createJwtAccessToken(userId, user.getRoles());
            return accessToken;

        } catch (ExpiredJwtException e) {
            //리프레시 토큰 만료되면
            throw new AuthFailException("리프레시 토큰 만료");
        } catch (Exception e) {
            throw new AuthFailException("인증에 실패했습니다.");
        }
    }

    public Jws<Claims> getClaimsFromJwtToken(String jwtToken) throws JwtException {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
    }

}
