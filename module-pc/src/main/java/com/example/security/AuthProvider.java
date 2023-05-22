package com.example.security;

import com.example.db.user.dto.UserDto;
import com.example.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

// 로그인 정보확인 및 권한 부여
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {
  private final UserService userService;
  private final PasswordEncoder passwordEncoding;
  private final HttpSession httpSession;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String id = (String) authentication.getPrincipal();
    String pwd = (String) authentication.getCredentials();

    UsernamePasswordAuthenticationToken token;
    UserDto userDto = userService.getUserId(id);

    if (userDto != null && passwordEncoding.matches(pwd, userDto.getPwd())) {
      List<GrantedAuthority> roles = new ArrayList<>();
      roles.add(new SimpleGrantedAuthority(userDto.getRole())); // 권한 부여

      token = new UsernamePasswordAuthenticationToken(userDto.getUserIdx(), null, roles);

      // 세션
      LoginDto loginDto = LoginDto.builder()
          .idx(userDto.getUserIdx())
          .id(userDto.getId())
          .name(userDto.getName())
          .role(userDto.getRole())
          .build();
      httpSession.setAttribute("LOGIN", loginDto);

      return token;
    }
    throw new BadCredentialsException("No such user or wrong password.");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}