package com.example.controller;

import com.example.db.user.dto.UserDto;
import com.example.db.user.dto.UserEditDto;
import com.example.db.user.dto.UserSaveDto;
import com.example.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final PasswordEncoder passwordEncoding;

  // 리스트
  @GetMapping("user/list")
  public String paging(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, HttpServletRequest req, Authentication authentication) {
    // 세션정보
    System.out.println("authentication = " + authentication);

    Page<UserDto> list = userService.getList(page-1, 10, req.getParameter("searchType"), req.getParameter("searchWord"));
    model.addAttribute("list", list);
    model.addAttribute("page", page);
    return "user/list";
  }

  // 뷰
  @GetMapping("user/detail/{id}")
  public String detail(@PathVariable("id") Long id, Model model) {
    UserDto row = userService.getUser(id);
    model.addAttribute("row", row);

    if(row == null){
      model.addAttribute("message", "잘못된 접근입니다.");
      model.addAttribute("href", "back");
      return "message";
    }

    return "user/detail";
  }

  // 등록 폼
  @GetMapping("user/add")
  public String addForm(Model model) {
    model.addAttribute("row", new UserDto());
    return "user/add";
  }

  // 등록
  @PostMapping("user/add")
  public String addUser(@Validated @ModelAttribute("row") UserSaveDto userSaveDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Authentication authentication) {
    // 아이디 중복체크
    UserDto user = userService.getUserId(userSaveDto.getId());
    if (user != null) bindingResult.addError(new FieldError("row", "id", "이미 사용중인 아이디 입니다."));

    if (bindingResult.hasErrors()) return "user/add"; // 검증실패시 return

//    String encPwd = passwordEncoding.encode(userSaveDto.getPwd()); // 비밀번호 암호화
//    UserDto userDto = new UserDto();
//    userDto.setName(userSaveDto.getName());
//    userDto.setId(userSaveDto.getId());
//    userDto.setPwd(encPwd);
//    userDto.setRole(userSaveDto.getRole());
//    userDto.setRegisterIdx((Long) authentication.getPrincipal()); // 로그인 PK
//    userDto.setModifyIdx((Long) authentication.getPrincipal()); // 로그인 PK
//    Long userIdx = userService.saveUser(userDto);

//    수정 후
    String encPwd = passwordEncoding.encode(userSaveDto.getPwd()); // 비밀번호 암호화
    UserDto userDto = UserDto.builder().pwd(encPwd).registerIdx((Long) authentication.getPrincipal()).build();
    BeanUtils.copyProperties(userSaveDto, userDto,"pwd","registerIdx");
    Long userIdx = userService.saveUser(userDto);

    if(userIdx == null){
      model.addAttribute("message", "오류가 발생하였습니다.");
      model.addAttribute("href", "back");
      return "message";
    }

    redirectAttributes.addAttribute("userIdx", userIdx);
    return "redirect:/user/detail/{userIdx}";
  }

  // 수정 폼
  @GetMapping("user/edit/{id}")
  public String editForm(@PathVariable("id") Long id, Model model) {
    UserDto row = userService.getUser(id);
    model.addAttribute("row", row);

    if(row == null){
      model.addAttribute("message", "잘못된 접근입니다.");
      model.addAttribute("href", "back");
      return "message";
    }

    return "user/edit";
  }

  // 수정
  @PostMapping("user/edit/{id}")
  public String editUser(@RequestParam("userIdx") Long id, @Validated @ModelAttribute("row") UserEditDto userEditDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Authentication authentication) {

    if (bindingResult.hasErrors()) return "/user/edit"; // 검증실패시 return

    String encPwd = passwordEncoding.encode(userEditDto.getPwd()); // 비밀번호 암호화
    UserDto userDtoUpdate = userService.getUser(id); // 기존정보
    if(userDtoUpdate == null){
      model.addAttribute("message", "오류가 발생하였습니다.");
      model.addAttribute("href", "back");
      return "message";
    }
    userDtoUpdate.setName(userEditDto.getName());
    userDtoUpdate.setPwd(encPwd);
    userDtoUpdate.setRole(userEditDto.getRole());
    userDtoUpdate.setModifyIdx((Long) authentication.getPrincipal()); // 로그인 PK
    Long userIdx = userService.saveUser(userDtoUpdate);

    if(userIdx == null){
      model.addAttribute("message", "오류가 발생하였습니다.");
      model.addAttribute("href", "back");
      return "message";
    }

    redirectAttributes.addAttribute("userIdx", userIdx);
    return "redirect:/user/detail/{userIdx}";
  }

  // 삭제
  @GetMapping("user/delete/{id}")
  public String delUser(@PathVariable("id") Long id, UserDto userDto, Authentication authentication) {
    UserDto userDtoUpdate = userService.getUser(id); // 기존정보
    if(userDtoUpdate != null) {
      userDtoUpdate.setIsDelete("Y");
      userDtoUpdate.setModifyIdx((Long) authentication.getPrincipal()); // 로그인 PK
      userService.saveUser(userDtoUpdate);
    }
    return "redirect:/user/list";
  }
}
