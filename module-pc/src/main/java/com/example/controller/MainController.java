package com.example.controller;

import com.example.Define;
import com.example.common.MailComponent;
import com.example.service.SmsService;
import com.example.db.sms.dto.SmsDto;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
  private final SmsService smsService;
  private final MailComponent mailComponent;

  // 메인
  @GetMapping("/")
  public String main() {
    return "main";
  }

  // 로그인
  @GetMapping("login")
  public String login(HttpServletRequest request) {
    // 로그인 이전 url 기억
    String uri = request.getHeader("Referer");
    if (uri != null && !uri.contains("/login")) {
      request.getSession().setAttribute("prevPage", uri);
    }

    return "login";
  }

  // 팝업
  @GetMapping("popup")
  public String popup() {
    return "test/popup";
  }


  // 메일 발송
  @GetMapping("email")
  public void email() throws MessagingException {
    String from = Define.SITE_EMAIL;
    String to = "test@test.com";
    String subject = "테스트 메일발송";
    String type = "template";
    String body = "test";
    Map<String, String> data = new HashMap<>();
    data.put("DOMAIN",Define.DOMAIN);

    // 메일 발송
    boolean status = mailComponent.send(from, to, subject, type, body, data);
  }

  // sms 리스트
  @GetMapping("sms")
  public String sms(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, HttpServletRequest req) {
    Page<SmsDto> list = smsService.getSms(page-1, 10, req.getParameter("searchType"), req.getParameter("searchWord"));
    model.addAttribute("list", list);
    model.addAttribute("page", page);
    return "test/sms";
  }
}
