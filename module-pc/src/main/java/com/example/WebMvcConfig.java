package com.example;

import com.example.security.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
  private final AuthInterceptor authInterceptor;

  // 메뉴 권한 체크
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authInterceptor)
        .addPathPatterns("/test/**", "/user/**") /* 인터셉터가 실행될 url 패턴 */
        .excludePathPatterns("/test/list") /* 인터셉터가 실행되지 않을 url 패턴 */
    ;
  }

  // 업로드 폴더 외부링크 (local 업로드 사용시)
  @Override
  public final void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/uploads/**")
        .addResourceLocations("file:///"+System.getProperty("user.dir")+"/uploads/")
        .setCachePeriod(3600)
        .resourceChain(true)
        .addResolver(new PathResourceResolver());
  }
}