package com.example.db.user.dto;

import com.example.db.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
  private Integer userIdx;
  private String name;
  private String id;
  private String pwd;
  private String role;
  private LocalDateTime registerDate;
  private Integer registerIdx;
  private LocalDateTime modifyDate;
  private Integer modifyIdx;
  private String isDelete;

  @Builder
  public UserDto(Integer userIdx, String name, String id, String pwd, String role, LocalDateTime registerDate, Integer registerIdx, LocalDateTime modifyDate, Integer modifyIdx, String isDelete) {
    this.userIdx = userIdx;
    this.name = name;
    this.id = id;
    this.pwd = pwd;
    this.role = role;
    this.registerDate = registerDate;
    this.registerIdx = registerIdx;
    this.modifyDate = modifyDate;
    this.modifyIdx = modifyIdx;
    this.isDelete = isDelete;
  }

  public User toEntity() {
    return User.builder()
        .userIdx(userIdx)
        .name(name)
        .id(id)
        .pwd(pwd)
        .role(role)
        .registerDate(registerDate)
        .registerIdx(registerIdx)
        .modifyDate(modifyDate)
        .modifyIdx(modifyIdx)
        .isDelete(isDelete)
        .build();
  }
}