package com.example.db.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserSaveDto {
  @NotBlank
  private String name;
  @NotBlank
  private String id;
  @NotBlank
  private String pwd;
  @NotBlank
  private String role;
}