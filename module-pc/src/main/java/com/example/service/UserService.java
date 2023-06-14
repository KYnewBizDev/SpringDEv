package com.example.service;

import com.example.db.user.domain.User;
import com.example.db.user.dto.UserDto;
import com.example.db.user.repository.UserQueryRepository;
import com.example.db.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final UserQueryRepository userQueryRepository;

  // 로그인처리
  @Transactional(readOnly = true)
  public UserDto getUserId(String id) {
    Optional<User> user = userRepository.findByIdAndIsDelete(id, "N");

    UserDto userDto = new UserDto();
    user.ifPresent(value -> BeanUtils.copyProperties(value, userDto));
    return userDto;
  }

  // 리스트
  @Transactional(readOnly = true)
  public Page<UserDto> getList(int page, int perPage, String searchType, String searchWord) {
    Pageable pageable = PageRequest.of(page, perPage);
    Page<User> userList= userQueryRepository.findLimit(pageable, searchType, searchWord);

    return userList.map(user-> {
      UserDto userDto = new UserDto();
      BeanUtils.copyProperties(user, userDto);
      return userDto;
    });
  }

  // 뷰
  @Transactional(readOnly = true)
  public UserDto getUser(Long id) {
    Optional<User> user = userRepository.findByUserIdxAndIsDelete(id, "N");

    UserDto userDto = new UserDto();
    user.ifPresent(value -> BeanUtils.copyProperties(value, userDto));
    return userDto;
  }

  // insert, update
  @Transactional
  public Long saveUser(UserDto userDto) {
    return userRepository.save(userDto.toEntity()).getUserIdx();
  }
}