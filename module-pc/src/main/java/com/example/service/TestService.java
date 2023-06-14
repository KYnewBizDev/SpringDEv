package com.example.service;

import com.example.db.test.domain.Test;
import com.example.db.test.dto.TestDto;
import com.example.db.test.repository.TestQueryRepository;
import com.example.db.test.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestService {
  private final TestRepository testRepository;
  private final TestQueryRepository testQueryRepository;

  // 리스트
  @Transactional(readOnly = true)
  public Page<TestDto> getList(int page, int perPage, String searchType, String searchWord) {
    Pageable pageable = PageRequest.of(page, perPage);
    Page<Test> testList= testQueryRepository.findLimit(pageable, searchType, searchWord);
    return testList.map(test-> {
      TestDto testDto = new TestDto();
      BeanUtils.copyProperties(test, testDto);
      return testDto;
    });
  }

  // 뷰
  @Transactional(readOnly = true)
  public TestDto getTest(Long id) {
    Optional<Test> test = testRepository.findByTestIdxAndIsDelete(id, "N");

    TestDto testDto = new TestDto();
    test.ifPresent(value -> BeanUtils.copyProperties(value, testDto));
    return testDto;
  }

  // insert, update
  @Transactional
  public Long saveTest(TestDto testDto) {
    return testRepository.save(testDto.toEntity()).getTestIdx();
  }


  // 엑셀 다운로드
  @Transactional(readOnly = true)
  public List<TestDto> getExcel(String searchType, String searchWord) {
    List<Test> testList = testQueryRepository.findAllTest(searchType, searchWord);
    List<TestDto> testDtoList = new ArrayList<>();

    for (Test test : testList) {
      TestDto dto = new TestDto();
      BeanUtils.copyProperties(test,dto,"pwd");
      testDtoList.add(dto);
    }
    return testDtoList;
  }
}