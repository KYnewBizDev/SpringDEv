package com.example.security;

import com.example.service.AuthService;
import com.example.service.UserService;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

// 로그인, 비밀번호 암호화, 권한체크
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
  private final AuthService authService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeHttpRequests((requests) -> requests
            .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
            .requestMatchers("/**").permitAll() // 인증제외
            .anyRequest().authenticated()
        )
        .formLogin((login) -> login
            .loginPage("/login") // GET 요청 (login form 을 보여줌)
            .loginProcessingUrl("/login") // POST 요청 (login 창에 입력한 데이터를 처리)
            .usernameParameter("id") // default : username
            .passwordParameter("pwd") // default : password
            .defaultSuccessUrl("/") // login 성공 후 /로 redirect
            .successHandler(new AuthSuccessHandler()) // 로그인 후 redirect
        )
        .logout(withDefaults())	// 로그아웃은 기본설정으로 (/logout 으로 인증해제)
        .headers().frameOptions().sameOrigin() // 동일 도메인 iframe 접근 허용
    ;

    return http.build();
  }

  // 로그인 정보 확인 및 권한 부여
  @Bean
  public AuthenticationProvider authenticationProvider(UserService userService, PasswordEncoder passwordEncoding, HttpSession httpSession) {
    return new AuthProvider(userService, passwordEncoding, httpSession);
  }

  //passwordEncoder
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
