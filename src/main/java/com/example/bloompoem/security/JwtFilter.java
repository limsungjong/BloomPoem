package com.example.bloompoem.security;

import com.example.bloompoem.service.SignService;
import com.example.bloompoem.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@PropertySource("classpath:app.properties")
public class JwtFilter extends OncePerRequestFilter {

    private final SignService signService;
    @Value("#{environment['jwt.secret']}")
    private final String secretKey;

    Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        logger.info("authorization : " + authorization);

        final String cookie = request.getHeader(HttpHeaders.COOKIE);
        logger.info(cookie);

        // token 안 보내면 블락
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            logger.error("authorization is null");
            filterChain.doFilter(request, response);
            return;
        }

        // token 꺼내기
        String token = authorization.split(" ")[1];
        logger.info("token : " + token);

        // token Expired되었는지 여부
        if (JwtUtil.isExpired(token, secretKey)) {
            logger.error("authorization is expired");
            filterChain.doFilter(request, response);
            return;
        }

        // userName 꺼내기
        String userEmail = JwtUtil.getUserName(token, secretKey);
        logger.info("userEmail : " + userEmail);

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userEmail, null, List.of(new SimpleGrantedAuthority("user")));

        // detail을 넣어주기
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
