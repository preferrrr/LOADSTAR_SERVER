package com.lodestar.lodestar_server.config;

import com.lodestar.lodestar_server.jwt.JwtAuthenticationFilter;
import com.lodestar.lodestar_server.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf((csrf) -> csrf.disable())
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authR -> {

                    //User
                    authR.requestMatchers("/users/my-page").hasAuthority("USER");
                    authR.requestMatchers("/users/**").permitAll();

                    //Email
                    authR.requestMatchers("/emails/**").permitAll();

                    //Board
                    authR.requestMatchers(HttpMethod.GET,"/boards").permitAll(); //메인페이지 게시글목록 조회
                    authR.requestMatchers(HttpMethod.POST, "/boards").hasAuthority("USER"); //작성
                    authR.requestMatchers("/boards/{boardId}").hasAuthority("USER"); //get, patch, delete (조회,수정,삭제)
                    authR.requestMatchers("/boards/new2").permitAll();
                    authR.requestMatchers("/boards/image").hasAuthority("USER");

                    //Comment
                    authR.requestMatchers("/comments/**").hasAuthority("USER");

                    //Bookmarks
                    authR.requestMatchers("/bookmarks/**").hasAuthority("USER");

                    //Careers
                    authR.requestMatchers("/careers").hasAuthority("USER");

                    authR.requestMatchers("/swagger-ui/**").permitAll();
                    authR.requestMatchers("/api-docs/**").permitAll();
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod(HttpMethod.GET);
        configuration.addAllowedMethod(HttpMethod.DELETE);
        configuration.addAllowedMethod(HttpMethod.POST);
        configuration.addAllowedMethod(HttpMethod.PATCH);
        configuration.addAllowedMethod(HttpMethod.OPTIONS);
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("*"); // CORS 문제, 포스트맨에는 보이지만 클라이언트에서 안 보이는거 해결
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
