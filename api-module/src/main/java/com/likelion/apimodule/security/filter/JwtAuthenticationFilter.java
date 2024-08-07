package com.likelion.apimodule.security.filter;

import com.likelion.apimodule.security.user.CustomUserDetails;
import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.commonmodule.exception.jwt.SecurityCustomException;
import com.likelion.commonmodule.exception.jwt.SecurityErrorCode;
import com.likelion.commonmodule.redis.util.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final RedisUtil redisUtil;

	@Override
	protected void doFilterInternal(
		@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain
	) throws ServletException, IOException {
		logger.info("[*] Jwt Filter");

		try {
			String accessToken = jwtUtil.resolveAccessToken(request);

			// accessToken 없이 접근할 경우
			if (accessToken == null) {
				filterChain.doFilter(request, response);
				return;
			}

			// logout 처리된 accessToken
			if (redisUtil.get(accessToken) != null && redisUtil.get(accessToken).equals("logout")) {
				logger.info("[*] Logout accessToken");
				filterChain.doFilter(request, response);
				return;
			}

			logger.info("[*] Authorization with Token");
			authenticateAccessToken(accessToken);
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			logger.warn("[*] case : accessToken Expired");
			throw new SecurityCustomException(SecurityErrorCode.TOKEN_EXPIRED);
		}
	}

	private void authenticateAccessToken(String accessToken) {
		CustomUserDetails userDetails = new CustomUserDetails(
			jwtUtil.getId(accessToken),
			jwtUtil.getEmail(accessToken),
			null,
			jwtUtil.getAuthority(accessToken)
		);

		logger.info("[*] Authority Registration");

		// 스프링 시큐리티 인증 토큰 생성
		Authentication authToken = new UsernamePasswordAuthenticationToken(
			userDetails,
			null,
			userDetails.getAuthorities());

		// 컨텍스트 홀더에 저장
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
}
