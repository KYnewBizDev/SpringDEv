package com.example.common;

import com.example.config.ThymeleafTemplateConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MailComponent {
  private final JavaMailSender javaMailSender;

  // 메일 발송
  public boolean send(String from, String to, String subject, String type, String body, Map<String, String> data) throws MessagingException {
    String htmlContent = body;

    // 메일 템플릿
    if(Objects.equals(type, "template")){
      AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ThymeleafTemplateConfig.class);
      Context ctx = new Context(Locale.KOREA);
      data.forEach(ctx::setVariable);
      TemplateEngine templateEngine = context.getBean(TemplateEngine.class);
      htmlContent = templateEngine.process(body, ctx);
    }

    // 메일발송
    MimeMessage mail = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mail, false, "UTF-8");
    mimeMessageHelper.setFrom(from);
    mimeMessageHelper.setTo(to);
    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(htmlContent, true);
    javaMailSender.send(mail);

    return true;
  }
}
