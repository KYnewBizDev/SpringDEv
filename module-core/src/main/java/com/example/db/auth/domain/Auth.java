package com.example.db.auth.domain;

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
public class Auth {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  @Comment("고유값")
  private Long authIdx;
  @Column(length = 100)
  @Comment("권한")
  private String role;
  @Column(length = 100)
  @Comment("메뉴별 권한")
  private String authority;

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
  public Auth(Long authIdx, String role, String authority, LocalDateTime registerDate, Long registerIdx, LocalDateTime modifyDate, Long modifyIdx, String isDelete) {
    this.authIdx = authIdx;
    this.role = role;
    this.authority = authority;
    this.registerDate = registerDate;
    this.registerIdx = registerIdx;
    this.modifyDate = modifyDate;
    this.modifyIdx = modifyIdx;
    this.isDelete = isDelete;
  }
}