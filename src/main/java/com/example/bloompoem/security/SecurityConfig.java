package com.example.bloompoem.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("/css/**")
//                .antMatchers("/build/**")
//                .antMatchers("/favicon*/**")
//                .antMatchers("/image/**")
//        ;
//    }
//
//    // 디폴트 로그인 페이지를 다른 곳으로 변환
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http.csrf().disable();
//        http
//                .authorizeRequests()
//                .antMatchers("/")
//                .permitAll()
//                .anyRequest().authenticated()
//                .and().formLogin().loginPage("/login")
//                .permitAll();
//        http.authorizeRequests().antMatchers("/","/test").permitAll();
//        http
//                .cors().and().csrf().disable().authorizeRequests();
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
//    }

//    @Bean
//    public CorsConfigurationSource corsConfiguration () {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.addAllowedOriginPattern("*");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**",configuration);
//        return source;
//    }
}
