package com.example.db.user.repository;

import com.example.db.user.domain.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.db.user.domain.QUser.user;

@Repository
public class UserQueryRepository {
  private final JPAQueryFactory query;
  public UserQueryRepository(EntityManager em) {
    this.query = new JPAQueryFactory(em);
  }

  // 리스트
  public Page<User> findLimit(Pageable pageable, String searchType, String searchWord) {
    List<User> result = query
        .selectFrom(user)
        .where(
            user.isDelete.eq("N")
            , likeSearch(searchType, searchWord)
        )
        .orderBy(user.userIdx.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = query
        .select(user.count())
        .from(user)
        .where(
            user.isDelete.eq("N")
            , likeSearch(searchType, searchWord)
        )
        .fetchOne();

    return new PageImpl<>(result,pageable,(count==null)?0:count);
  }

  // 검색조건 (리스트, 엑셀)
  private BooleanExpression likeSearch(String searchType, String searchWord) {
    if (StringUtils.hasText(searchWord)) {
      if (StringUtils.hasText(searchType)) {
        if (searchType.equals("name")) {
          return user.name.like("%" + searchWord + "%");
        } else if (searchType.equals("id")) {
          return user.id.like("%" + searchWord + "%");
        }
      } else {
        return user.name.like("%" + searchWord + "%")
            .or(user.id.like("%" + searchWord + "%"));
      }
    }
    return null;
  }
}
