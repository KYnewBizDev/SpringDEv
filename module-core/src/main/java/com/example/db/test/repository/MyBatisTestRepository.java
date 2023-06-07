package com.example.db.test.repository;

import com.example.db.test.dto.TestDto;
import com.example.db.test.repository.mybatis.TestMapper;
import com.example.db.test.repository.mybatis.TestSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyBatisTestRepository implements TestMapper {
    private final TestMapper testMapper;

    @Override
    public List<TestDto> testList(TestSearchCond testSearch) {
        List<TestDto> result = testMapper.testList(testSearch);
        return result;
    }
}
