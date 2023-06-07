package com.example.db.test.repository.mybatis;

import com.example.db.test.dto.TestDto;
import com.example.db.test.repository.MyBatisTestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MyBatisBoardRepositoryTest {
    @Autowired
    private MyBatisTestRepository myBatisTestRepository;

    @Test
    public void dynamicTableQuery() {
        TestSearchCond testSearch = new TestSearchCond("test",null,"N");
        List<TestDto> result = myBatisTestRepository.testList(testSearch);
        System.out.println("result = " + result);
    }
}