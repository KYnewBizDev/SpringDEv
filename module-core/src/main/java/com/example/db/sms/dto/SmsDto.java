package com.example.db.sms.dto;

import com.example.db.sms.domain.Sms;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class SmsDto {
  private Integer testIdx;
  private String testName;
  private String testPwd;
  private String memo;
  private String originalFileName;
  private String fileName;
  private String isOpen;
  private LocalDateTime registerDate;
  private Integer registerIdx;
  private String isDelete;

  @Builder
  public SmsDto(Integer testIdx, String testName, String testPwd, String memo, String originalFileName, String fileName, String isOpen, LocalDateTime registerDate, Integer registerIdx, String isDelete) {
    this.testIdx = testIdx;
    this.testName = testName;
    this.testPwd = testPwd;
    this.memo = memo;
    this.originalFileName = originalFileName;
    this.fileName = fileName;
    this.isOpen = isOpen;
    this.registerDate = registerDate;
    this.registerIdx = registerIdx;
    this.isDelete = isDelete;
  }

  public Sms toEntity() {
    return Sms.builder()
        .testIdx(testIdx)
        .testName(testName)
        .testPwd(testPwd)
        .memo(memo)
        .originalFileName(originalFileName)
        .fileName(fileName)
        .isOpen(isOpen)
        .registerDate(registerDate)
        .registerIdx(registerIdx)
        .isDelete(isDelete)
        .build();
  }
}