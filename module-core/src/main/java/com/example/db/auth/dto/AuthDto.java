package com.example.db.auth.dto;

import com.example.db.auth.domain.Auth;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class AuthDto {
  private Long authIdx;
  private String role;
  private String authority;
  private LocalDateTime registerDate;
  private Long registerIdx;
  private LocalDateTime modifyDate;
  private Long modifyIdx;
  private String isDelete;

  @Builder
  public AuthDto(Long authIdx, String role, String authority, LocalDateTime registerDate, Long registerIdx, LocalDateTime modifyDate, Long modifyIdx, String isDelete) {
    this.authIdx = authIdx;
    this.role = role;
    this.authority = authority;
    this.registerDate = registerDate;
    this.registerIdx = registerIdx;
    this.modifyDate = modifyDate;
    this.modifyIdx = modifyIdx;
    this.isDelete = isDelete;
  }

  public Auth toEntity() {
    return Auth.builder()
        .authIdx(authIdx)
        .role(role)
        .authority(authority)
        .registerDate(registerDate)
        .registerIdx(registerIdx)
        .modifyDate(modifyDate)
        .modifyIdx(modifyIdx)
        .isDelete(isDelete)
        .build();
  }
}