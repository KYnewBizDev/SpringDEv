package com.example.security;

import com.example.db.auth.dto.AuthDto;
import com.example.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

// 페이지별 권한체크
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
  private final AuthService authService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    System.out.println("request.getRequestURI() = " + request.getRequestURI());
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    AntPathMatcher pathMatcher = new AntPathMatcher();
    List<AuthDto> authList = authService.getAuthority();

    boolean rtn;
    for (AuthDto auth : authList) {
      if ((auth.getRole() != null && auth.getAuthority() != null) && authentication.getAuthorities().contains(new SimpleGrantedAuthority(auth.getRole()))) {
        rtn = pathMatcher.match(auth.getAuthority(), request.getRequestURI());
        if (rtn) return true;
      }
    }

    if(authentication.getPrincipal()=="anonymousUser"){
      response.sendRedirect("/login"); // 로그인필요
    }else{
      response.sendRedirect("/error/403"); // 권한없음
    }
    return false;
  }
}
