package com.example.bloompoem.security;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfigure {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/","/shopping/**","/api/**","/sign/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/test")
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/sign/sign_in")
                .usernameParameter("userEmail")
                .passwordParameter("userOtp")
                .loginProcessingUrl("/loginProc")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .defaultSuccessUrl("/")
                .permitAll();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(
                "/css/**",
                "/build/**",
                "/favicon*/**",
                "/image/**"
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
