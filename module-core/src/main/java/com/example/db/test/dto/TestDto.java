package com.example.db.test.dto;

import com.example.db.test.domain.Test;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class TestDto {
  private Integer testIdx;
  private String testName;
  private String testPwd;
  private String memo;
  private String originalFileName;
  private String fileName;
  private String isOpen;
  private LocalDateTime registerDate;
  private Integer registerIdx;
  private LocalDateTime modifyDate;
  private Integer modifyIdx;
  private String isDelete;

  @Builder
  public TestDto(Integer testIdx, String testName, String testPwd, String memo, String originalFileName, String fileName, String isOpen, LocalDateTime registerDate, Integer registerIdx, LocalDateTime modifyDate, Integer modifyIdx, String isDelete) {
    this.testIdx = testIdx;
    this.testName = testName;
    this.testPwd = testPwd;
    this.memo = memo;
    this.originalFileName = originalFileName;
    this.fileName = fileName;
    this.isOpen = isOpen;
    this.registerDate = registerDate;
    this.registerIdx = registerIdx;
    this.modifyDate = modifyDate;
    this.modifyIdx = modifyIdx;
    this.isDelete = isDelete;
  }

  public Test toEntity() {
    return Test.builder()
        .testIdx(testIdx)
        .testName(testName)
        .testPwd(testPwd)
        .memo(memo)
        .originalFileName(originalFileName)
        .fileName(fileName)
        .isOpen(isOpen)
        .registerDate(registerDate)
        .registerIdx(registerIdx)
        .modifyDate(modifyDate)
        .modifyIdx(modifyIdx)
        .isDelete(isDelete)
        .build();
  }
}