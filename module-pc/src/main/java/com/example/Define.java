package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 상수 정의
@Component
public class Define {
  // 사이트 정보
  public static final String SITE_NAME = "유니스터디 테스트";
  public static final String DOMAIN = "localhost";
  public static final String IMG_DIR = "/img";
  public static final String SITE_EMAIL = "test@test.com";

  // 파일업로드 위치
  public static String FILE_DIR;
  @Value("${upload.file.dir}")
  public void setFILE_DIR(String dir) {
    Define.FILE_DIR = dir;
  }
  public static String FILE_URL;
  @Value("${upload.file.url}")
  public void setFILE_URL(String url) {
    Define.FILE_URL = url;
  }
}