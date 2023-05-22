package com.example.security;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Getter
public class LoginDto implements Serializable {
  private Integer idx;
  private String id;
  private String name;
  private String role;

  @Builder
  public LoginDto(Integer idx, String id, String name, String  role) {
    this.idx = idx;
    this.id = id;
    this.name = name;
    this.role = role;
  }

  @Override
  public String toString() {
    return "UserDto{" +
        "idx='" + idx + '\'' +
        ", id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", role=" + role +
        '}';
  }
}