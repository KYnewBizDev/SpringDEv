package com.example.db.test.dto;

import com.example.db.test.domain.Test;
import com.example.excel.annotation.*;
import com.example.excel.style.DefaultExcelCellStyle;
import com.example.excel.style.custom.BlueBodyStyle;
import com.example.excel.style.custom.PinkHeaderStyle;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@DefaultHeaderStyle(
        style = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER")
)
@DefaultBodyStyle(
        style = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BODY")
)
public class TestDto {
  @ExcelColumn(headerName = "id", headerStyle = @ExcelColumnStyle(excelCellStyleClass = PinkHeaderStyle.class), bodyStyle = @ExcelColumnStyle(excelCellStyleClass = BlueBodyStyle.class))
  private Long testIdx;
  @ExcelColumn(headerName = "testName")
  private String testName;
  private String testPwd;
  private String memo;
  @ExcelColumn(headerName = "originalFileName")
  private String originalFileName;
  private String fileName;
  @ExcelColumn(headerName = "isOpen")
  private String isOpen;
  private LocalDateTime registerDate;
  private Long registerIdx;
  private LocalDateTime modifyDate;
  private Long modifyIdx;
  @ExcelColumn(headerName = "isDelete")
  private String isDelete;

  @Builder
  public TestDto(Long testIdx, String testName, String testPwd, String memo, String originalFileName, String fileName, String isOpen, LocalDateTime registerDate, Long registerIdx, LocalDateTime modifyDate, Long modifyIdx, String isDelete) {
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

  @Override
  public String toString() {
    return "TestDto{" +
            "testIdx=" + testIdx +
            ", testName='" + testName + '\'' +
            ", testPwd='" + testPwd + '\'' +
            ", memo='" + memo + '\'' +
            ", originalFileName='" + originalFileName + '\'' +
            ", fileName='" + fileName + '\'' +
            ", isOpen='" + isOpen + '\'' +
            ", registerDate=" + registerDate +
            ", registerIdx=" + registerIdx +
            ", modifyDate=" + modifyDate +
            ", modifyIdx=" + modifyIdx +
            ", isDelete='" + isDelete + '\'' +
            '}';
  }
}