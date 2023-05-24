package com.example.db.sms.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Sms {
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
  @CreatedDate
  @Column(updatable = false)
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Comment("등록일")
  private LocalDateTime registerDate;
  @Comment("작성자 PK")
  private Long registerIdx;
  @Column(length = 1, insertable = false)
  @ColumnDefault("'N'")
  @Comment("삭제여부")
  private String isDelete;

  // 생성자
  @Builder
  public Sms(Long testIdx, String testName, String testPwd, String memo, String originalFileName, String fileName, String isOpen, LocalDateTime registerDate, Long registerIdx, String isDelete) {
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
}