package com.example.service;

import com.example.db.auth.domain.Auth;
import com.example.db.auth.dto.AuthDto;
import com.example.db.auth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthRepository authRepository;

  // 메뉴권한
  @Transactional(readOnly = true)
  public List<AuthDto> getAuthority() {
    List<Auth> authList = authRepository.findAllByIsDelete("N");

    List<AuthDto> authDtoList = new ArrayList<>();
    for (Auth auth : authList) {
      AuthDto authDto = AuthDto.builder()
          .authIdx(auth.getAuthIdx())
          .role(auth.getRole())
          .authority(auth.getAuthority())
          .build();
      authDtoList.add(authDto);
    }
    return authDtoList;
  }
}