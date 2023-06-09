package com.example.db.test.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Test {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  @Comment("고유값")
  private Long testIdx;
  @Column(length = 100)
  @Comment("test 명")
  private String testName;
  @Column(length = 100)
  @Comment("test 비밀번호")
  private String testPwd;
  @Column(columnDefinition="LONGTEXT")
  @Comment("메모")
  private String memo;
  @Column(length = 200)
  @Comment("원본파일명")
  private String originalFileName;
  @Column(length = 200)
  @Comment("저장파일명")
  private String fileName;
  @Column(length = 1)
  @ColumnDefault("'N'")
  @Comment("오픈여부")
  private String isOpen;

  @Comment("등록일")
  @Column(updatable = false)
  @ColumnDefault("CURRENT_TIMESTAMP")
  @CreatedDate
  private LocalDateTime registerDate;
  @Comment("작성자 PK")
  private Long registerIdx;
  @Comment("수정일")
  @Column(columnDefinition="datetime(6) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
  @LastModifiedDate
  private LocalDateTime modifyDate;
  @Comment("수정자 PK")
  private Long modifyIdx;
  @Comment("삭제여부")
  @Column(length = 1, insertable = false)
  @ColumnDefault("'N'")
  private String isDelete;

  // 생성자
  @Builder
  public Test(Long testIdx, String testName, String testPwd, String memo, String originalFileName, String fileName, String isOpen, LocalDateTime registerDate, Long registerIdx, LocalDateTime modifyDate, Long modifyIdx, String isDelete) {
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
}