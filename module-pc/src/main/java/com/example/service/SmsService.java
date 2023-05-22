package com.example.service;

import com.example.aop.DbTypeAnnotation;
import com.example.db.sms.domain.Sms;
import com.example.db.sms.dto.SmsDto;
import com.example.db.sms.repository.SmsQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {
  private final SmsQueryRepository smsQueryRepository;

  @DbTypeAnnotation("sms")
  public Page<SmsDto> getSms(int page, int perPage, String searchType, String searchWord) {
    Pageable pageable = PageRequest.of(page, perPage);
    Page<Sms> testList= smsQueryRepository.findLimit(pageable, searchType, searchWord);

    return testList.map(test-> SmsDto.builder()
        .testIdx(test.getTestIdx())
        .testName(test.getTestName())
        .originalFileName(test.getOriginalFileName())
        .isOpen(test.getIsOpen())
        .registerDate(test.getRegisterDate())
        .build());
  }
}