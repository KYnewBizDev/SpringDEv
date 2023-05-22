package com.example.config;

import com.amazonaws.services.s3.AmazonS3Client;
import com.example.common.FileComponent;
import com.example.common.FileS3Component;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

// 파일업로드, 다운로드
@Configuration
@RequiredArgsConstructor
public class FileConfig {
  private final AmazonS3Client amazonS3Client;

  @Value("${upload.file.area}")
  private String area;

  @Bean
  public FileComponent fileComponent() {
    if (Objects.equals(area, "S3")) {
      return new FileS3Component(amazonS3Client); // S3
    }else{
      return new FileComponent(); // local
    }
  }
}
