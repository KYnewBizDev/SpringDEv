package com.example.db.test.repository;

import com.example.db.test.domain.Test;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.db.test.domain.QTest.test;

@Repository
public class TestQueryRepository {
  private final JPAQueryFactory query;
  public TestQueryRepository(EntityManager em) {
    this.query = new JPAQueryFactory(em);
  }

  // 리스트
  public Page<Test> findLimit(Pageable pageable, String searchType, String searchWord) {
    List<Test> result = query
        .selectFrom(test)
        .where(
            test.isDelete.eq("N")
            , likeSearch(searchType, searchWord)
        )
        .orderBy(test.testIdx.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = query
        .select(test.count())
        .from(test)
        .where(
            test.isDelete.eq("N")
            , likeSearch(searchType, searchWord)
        )
        .fetchOne();

    return new PageImpl<>(result,pageable,(count==null)?0:count);
  }

  // 엑셀
  public List<Test> findAllTest(String searchType, String searchWord) {
    return query
        .selectFrom(test)
        .where(
            test.isDelete.eq("N")
            , likeSearch(searchType, searchWord)
        )
        .orderBy(test.testIdx.desc())
        .fetch();
  }

  // 검색조건 (리스트, 엑셀)
  private BooleanExpression likeSearch(String searchType, String searchWord) {
    if (StringUtils.hasText(searchWord)) {
      if (StringUtils.hasText(searchType)) {
        if (searchType.equals("testName")) {
          return test.testName.like("%" + searchWord + "%");
        } else if (searchType.equals("originalFileName")) {
          return test.originalFileName.like("%" + searchWord + "%");
        }
      } else {
        return test.testName.like("%" + searchWord + "%")
            .or(test.originalFileName.like("%" + searchWord + "%"));
      }
    }
    return null;
  }
}
