package com.example.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 로그인 성공 후 이전페이지로 이동처리
@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    String prevPage = (String) request.getSession().getAttribute("prevPage");
    if(prevPage==null) prevPage="/";

    redirectStrategy.sendRedirect(request, response, prevPage);
  }
}