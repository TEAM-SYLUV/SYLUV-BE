package com.likelion.apimodule.security.config;

import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.commonmodule.exception.jwt.JwtAccessDeniedHandler;
import com.likelion.commonmodule.exception.jwt.JwtAuthenticationEntryPoint;
import com.likelion.commonmodule.redis.util.RedisUtil;
import com.likelion.commonmodule.security.config.CorsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(AbstractHttpConfigurer::disable);

        // csrf disable
        http
                .csrf(AbstractHttpConfigurer::disable);

        // form 로그인 방식 disable
        http
                .formLogin(AbstractHttpConfigurer::disable);

        // http basic 인증 방식 disable
        http
                .httpBasic(AbstractHttpConfigurer::disable);

        // 경로별 인가 작업
        http
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/swagger-ui/**").permitAll()
//                        .requestMatchers("/api-docs").permitAll()
//                        .requestMatchers("/v1/users/login/**").permitAll()
                        .requestMatchers("/v1/api/**").authenticated()
                        .anyRequest().permitAll()
                );

        // Jwt Filters
//        http
//                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, redisUtil), UsernamePasswordAuthenticationFilter.class);
//
//        http
//                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);
//
//        http
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                        .accessDeniedHandler(jwtAccessDeniedHandler)
//                );

        // 세션 사용 안함
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // Logout Filter
//        http
//                .logout(logout -> logout
//                        .logoutUrl("/api/v1/user/logout")
//                        .addLogoutHandler(new CustomLogoutHandler(redisUtil, jwtUtil))
//                        .logoutSuccessHandler((request, response, authentication) ->
//                                HttpResponseUtil.setSuccessResponse(
//                                        response,
//                                        HttpStatus.OK,
//                                        "로그아웃 성공"
//                                )
//                        )
//                )
//                .addFilterAfter(new LogoutFilter(
//                                (request, response, authentication) ->
//                                        HttpResponseUtil.setSuccessResponse(
//                                                response,
//                                                HttpStatus.OK,
//                                                "로그아웃 성공"
//                                        ), new CustomLogoutHandler(redisUtil, jwtUtil)),
//                        JwtAuthenticationFilter.class
//                );

        return http.build();
    }

}
