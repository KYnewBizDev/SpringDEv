package com.example.controller;

import com.example.Define;
import com.example.common.ExcelComponent;
import com.example.common.FileComponent;
import com.example.db.test.dto.TestDto;
import com.example.service.TestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class TestController {
  private final TestService testService;
  private final FileComponent fileComponent;
  private final ExcelComponent excelComponent;
  private final MessageSource ms;

  // 리스트
  @GetMapping(value = {"/", "test/list"})
  public String paging(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, HttpServletRequest req, Authentication authentication) {
    // 세션정보
    System.out.println("authentication = " + authentication);

    Page<TestDto> list = testService.getList(page-1, 10, req.getParameter("searchType"), req.getParameter("searchWord"));
    model.addAttribute("list", list);
    model.addAttribute("page", page);
    return "test/list";
  }

  // 뷰
  @GetMapping("test/detail/{id}")
  public String detail(@PathVariable("id") Long id, Model model, Authentication authentication, HttpSession httpSession) {
    // 세션정보
    System.out.println("loginDto = " + httpSession.getAttribute("LOGIN"));
//    LoginDto loginDto = (LoginDto) httpSession.getAttribute("LOGIN");
//    System.out.println("loginDto.getEmail() = " + loginDto.getEmail());
//
//    System.out.println("authentication = " + authentication);
//    System.out.println("authentication2 = " + authentication.getPrincipal());
//    System.out.println("authentication3 = " + authentication.getAuthorities());


    TestDto row = testService.getTest(id);
    model.addAttribute("row", row);

    if(row == null){
      model.addAttribute("message", "잘못된 접근입니다.");
      model.addAttribute("href", "back");
      return "message";
    }

    return "test/detail";
  }

  // 등록 폼
  @GetMapping("test/add")
  public String addForm(Model model) {
    model.addAttribute("row", new TestDto());
    return "test/add";
  }

  // 등록
  @PostMapping("test/add")
  public String addTest(@RequestParam("file") MultipartFile file, TestDto testDto, RedirectAttributes redirectAttributes, Model model, Authentication authentication) throws IOException {
    // 파일 업로드
    Map<String, String> attachFile = fileComponent.uploadFile(Define.FILE_DIR, file);
    if(attachFile!=null){
      testDto.setOriginalFileName(attachFile.get("originalFileName"));
      testDto.setFileName(attachFile.get("fileName"));
    }

    testDto.setIsOpen((testDto.getIsOpen()==null)?"N":testDto.getIsOpen());
    testDto.setRegisterIdx((Long) authentication.getPrincipal()); // 로그인 PK
    testDto.setModifyIdx((Long) authentication.getPrincipal()); // 로그인 PK
    Long testIdx = testService.saveTest(testDto);

    if(testIdx == null){
      model.addAttribute("message", ms.getMessage("error1",null,null));
      model.addAttribute("href", "back");
      return "message";
    }

    redirectAttributes.addAttribute("testIdx", testIdx);
    return "redirect:/test/detail/{testIdx}";
  }

  // 수정 폼
  @GetMapping("test/edit/{id}")
  public String editForm(@PathVariable("id") Long id, Model model) {
    TestDto row = testService.getTest(id);
    model.addAttribute("row", row);

    if(row == null){
      model.addAttribute("message", "잘못된 접근입니다.");
      model.addAttribute("href", "back");
      return "message";
    }

    return "test/edit";
  }

  // 수정
  @PostMapping("test/edit/{id}")
  public String editTest(@RequestParam("testIdx") Long id, @RequestParam("file") MultipartFile file, TestDto testDto, RedirectAttributes redirectAttributes, Model model, Authentication authentication) throws IOException {
    TestDto testDtoUpdate = testService.getTest(id); // 기존정보
    if(testDtoUpdate == null){
      model.addAttribute("message", "오류가 발생하였습니다.");
      model.addAttribute("href", "back");
      return "message";
    }

    // 파일 업로드
    Map<String, String> attachFile = fileComponent.uploadFile(Define.FILE_DIR, file);
    if(attachFile!=null){
      // 기존 파일 삭제
      if(testDtoUpdate.getFileName() != null){
        fileComponent.removeFile(Define.FILE_DIR, testDtoUpdate.getFileName());
      }

      testDtoUpdate.setOriginalFileName(attachFile.get("originalFileName"));
      testDtoUpdate.setFileName(attachFile.get("fileName"));
    }

    testDtoUpdate.setTestName(testDto.getTestName());
    testDtoUpdate.setMemo(testDto.getMemo());
    testDtoUpdate.setIsOpen((testDto.getIsOpen()==null)?"N":testDto.getIsOpen());
    testDtoUpdate.setModifyIdx((Long) authentication.getPrincipal()); // 로그인 PK
    Long testIdx = testService.saveTest(testDtoUpdate);

    if(testIdx == null){
      model.addAttribute("message", "오류가 발생하였습니다.");
      model.addAttribute("href", "back");
      return "message";
    }

    redirectAttributes.addAttribute("testIdx", testIdx);
    return "redirect:/test/detail/{testIdx}";
  }

  // 삭제
  @GetMapping("test/delete/{id}")
  public String delTest(@PathVariable("id") Long id, TestDto testDto, Authentication authentication) {
    TestDto testDtoUpdate = testService.getTest(id); // 기존정보
    if(testDtoUpdate != null) {
      // 기존 파일 삭제
      if(testDtoUpdate.getFileName() != null){
        fileComponent.removeFile(Define.FILE_DIR, testDtoUpdate.getFileName());
      }

      testDtoUpdate.setIsDelete("Y");
      testDtoUpdate.setModifyIdx((Long) authentication.getPrincipal()); // 로그인 PK
      testService.saveTest(testDtoUpdate);
    }
    return "redirect:/test/list";
  }

  // 엑셀 업로드
  @GetMapping("test/excelUpload")
  public String excelUploadForm() {
    return "test/excelUpload";
  }

  @PostMapping("test/excelUpload")
  public String excelUpload(@RequestParam("excelFile") MultipartFile excelFile, TestDto testDto, Model model, Authentication authentication) {

    if(excelFile.isEmpty()){
      model.addAttribute("message", "오류가 발생하였습니다.");
      model.addAttribute("href", "back");
      return "message";
    }else {
      // 엑셀 업로드
      ArrayList<List<String>> excelData = excelComponent.uploadExcel(excelFile);
      System.out.println("excelData = " + excelData);
      if(excelData == null){
        model.addAttribute("message", "엑셀 파일을 확인해주세요. xls, xlsx 형식만 업로드 가능합니다.");
        model.addAttribute("href", "back");
        return "message";

      }else{
        for (List<String> excel : excelData) {
          testDto.setTestName(excel.get(0));
          testDto.setIsOpen(excel.get(1));
          testDto.setRegisterIdx((Long) authentication.getPrincipal()); // 로그인 PK
          testDto.setModifyIdx((Long) authentication.getPrincipal()); // 로그인 PK
          Long testIdx = testService.saveTest(testDto);
        }
      }
    }

    return "redirect:/test/list";
  }

  // 엑셀 다운로드
  @GetMapping("test/excel")
  public void excel(HttpServletRequest req, HttpServletResponse response) throws IOException {
    List<TestDto> list = testService.getExcel(req.getParameter("searchType"), req.getParameter("searchWord"));

    // 파일 다운로드 명
    String fileName = "Test 리스트";

    // Header
    List<String> header = Arrays.asList(
        "idx"
        ,"이름"
        ,"오픈여부"
        ,"첨부파일"
        ,"등록일"
    );

    // Body
    ArrayList<List<String>> body = new ArrayList<>();
    for (TestDto rtn : list) {
      List<String> row = Arrays.asList(
          rtn.getTestIdx().toString()
          , rtn.getTestName()
          , rtn.getIsOpen()
          , rtn.getOriginalFileName()
          , rtn.getRegisterDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
      );
      body.add(row);
    }

    // 엑셀 다운로드
    excelComponent.downloadExcel(fileName, header, body, response);
  }
}
