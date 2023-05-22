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
  private Integer authIdx;
  private String role;
  private String authority;
  private LocalDateTime registerDate;
  private Integer registerIdx;
  private LocalDateTime modifyDate;
  private Integer modifyIdx;
  private String isDelete;

  @Builder
  public AuthDto(Integer authIdx, String role, String authority, LocalDateTime registerDate, Integer registerIdx, LocalDateTime modifyDate, Integer modifyIdx, String isDelete) {
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