package com.example.db.sms.repository;

import com.example.db.sms.domain.Sms;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.db.sms.domain.QSms.sms;

@Repository
@RequiredArgsConstructor
public class SmsQueryRepository {
  private final JPAQueryFactory query;

  // 리스트
  public Page<Sms> findLimit(Pageable pageable, String searchType, String searchWord) {
    List<Sms> result = query
        .selectFrom(sms)
        .where(
            sms.isDelete.eq("N")
            , likeSearch(searchType, searchWord)
        )
        .orderBy(sms.testIdx.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = query
        .select(sms.count())
        .from(sms)
        .where(
            sms.isDelete.eq("N")
            , likeSearch(searchType, searchWord)
        )
        .fetchOne();

    return new PageImpl<>(result,pageable,(count==null)?0:count);
  }

  // 엑셀
  public List<Sms> findAllSms(String searchType, String searchWord) {
    return query
        .selectFrom(sms)
        .where(
            sms.isDelete.eq("N")
            , likeSearch(searchType, searchWord)
        )
        .orderBy(sms.testIdx.desc())
        .fetch();
  }

  // 검색조건 (리스트, 엑셀)
  private BooleanExpression likeSearch(String searchType, String searchWord) {
    if (StringUtils.hasText(searchWord)) {
      if (StringUtils.hasText(searchType)) {
        if (searchType.equals("testName")) {
          return sms.testName.like("%" + searchWord + "%");
        } else if (searchType.equals("originalFileName")) {
          return sms.originalFileName.like("%" + searchWord + "%");
        }
      } else {
        return sms.testName.like("%" + searchWord + "%")
            .or(sms.originalFileName.like("%" + searchWord + "%"));
      }
    }
    return null;
  }
}
