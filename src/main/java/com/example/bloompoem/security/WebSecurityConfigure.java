package com.example.bloompoem.security;

import com.example.bloompoem.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:app.properties")
public class WebSecurityConfigure {
    private final SignService signService;
    @Value("#{environment['jwt.secret']}")
    private String secretKey;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().and().headers().frameOptions().sameOrigin().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/v1/sign/sign_out").permitAll()
//                .antMatchers("/","/shopping/**","/api/**","/sign/**").permitAll()
                .antMatchers("/user").authenticated()
//                .antMatchers("/pick_up").authenticated()
                .antMatchers("/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/**").permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtFilter(signService,secretKey), UsernamePasswordAuthenticationFilter.class);
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
}
