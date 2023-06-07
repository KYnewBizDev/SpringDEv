package com.example.db.test.repository.mybatis;
import com.example.db.test.dto.TestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
    List<TestDto> testList(TestSearchCond testSearch);
}
