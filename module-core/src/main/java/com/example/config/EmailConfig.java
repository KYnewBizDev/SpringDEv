package com.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Objects;
import java.util.Properties;

// 이메일발송
@Configuration
@RequiredArgsConstructor
public class EmailConfig {
  private final Environment env;

  @Bean
  public JavaMailSender javaMailSender(){
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    Properties properties = new Properties();
    properties.put("mail.transport.protocol", env.getProperty("spring.mail.transport.protocol"));
    properties.put("mail.smtp.auth", env.getProperty("spring.mail.properties.mail.smtp.auth"));
    properties.put("mail.smtp.starttls.enable", env.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
    properties.put("mail.smtp.debug", env.getProperty("spring.mail.debug"));
    properties.put("mail.smtp.ssl.trust", "*");


    mailSender.setHost(env.getProperty("spring.mail.host"));
//    mailSender.setUsername(env.getProperty("spring.mail.username"));
//    mailSender.setPassword(env.getProperty("spring.mail.password"));
    mailSender.setPort(Integer.parseInt(Objects.requireNonNull(env.getProperty("spring.mail.port"))));
    mailSender.setProtocol(env.getProperty("spring.mail.transport.protocol"));
    mailSender.setJavaMailProperties(properties);
    mailSender.setDefaultEncoding(env.getProperty("spring.mail.default.encoding"));

    return mailSender;
  }
}