package com.example.controller;

import com.example.db.board.dto.BoardDto;
import com.example.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class BoardController {
  private final BoardService boardService;

  // 게시판
  @GetMapping({"bbs/{table}","bbs/{table}/{mode}","bbs/{table}/{mode}/{id}"})
  public String board(@PathVariable("table") String table, @PathVariable(name = "mode", required = false) String mode, @PathVariable(name = "id", required = false) Long id, HttpServletRequest req, Model model) {
    // get skin
    // String skin = boardGroupService.getBoardGroupSkin(table);
    String skin = "basic";
    if(mode==null) mode="list";

    // basic 스킨
    if(skin.equals("basic")) {
      switch (mode) {
        case "list" -> {
          return basicList(table, model, req);
        }
        case "detail" -> {
          return basicDetail(table, id, model);
        }
        case "addForm" -> {
          return basicAddForm(table, model);
        }
        case "editForm" -> {
          return basicEditForm(table, id, model);
        }
      }
    }
//    else if(skin.equals("스킨2")){
//    }

    return null;
  }

  //region /** 스킨 **/

  //region basic
  // 리스트 (basic)
  public String basicList(String table, Model model, HttpServletRequest req) {
    int page = (req.getParameter("page")!=null && !Objects.equals(req.getParameter("page"), ""))? Integer.parseInt((req.getParameter("page"))) : 1;
    String perPage = (req.getParameter("perPage")!=null && !Objects.equals(req.getParameter("perPage"), ""))? (req.getParameter("perPage")) : "20";

    Page<BoardDto> list = boardService.getBasicList(table,page-1, Integer.parseInt(perPage), req.getParameter("searchType"), req.getParameter("searchWord"), req.getParameter("startDate"), req.getParameter("endDate"), req.getParameter("isOpen"), req.getParameter("category1"), req.getParameter("category2"));

    model.addAttribute("table", table);
    model.addAttribute("list", list);
    model.addAttribute("page", page);
    return "board/basic/list";
  }

  // 뷰 (basic)
  public String basicDetail(String table, Long boardIdx, Model model) {
    BoardDto row = boardService.getBoard(table, boardIdx);

    if(row == null){
      model.addAttribute("message", "잘못된 접근입니다.");
      model.addAttribute("href", "back");
      return "message";
    }
    model.addAttribute("table", table);
    model.addAttribute("row", row);
    return "board/basic/detail";
  }

  // 등록 폼 (basic)
  public String basicAddForm(String table, Model model) {
    model.addAttribute("table", table);
    model.addAttribute("row", new BoardDto());
    return "board/basic/addForm";
  }

  // 수정 폼 (basic)
  public String basicEditForm(String table, Long boardIdx, Model model) {
    BoardDto row = boardService.getBoard(table, boardIdx);

    if(row == null){
      model.addAttribute("message", "잘못된 접근입니다.");
      model.addAttribute("href", "back");
      return "message";
    }
    model.addAttribute("table", table);
    model.addAttribute("row", row);
    return "board/basic/editForm";
  }
  //endregion basic


  //endregion /** 스킨 **/


  // 등록
  @PostMapping("bbs/{table}/addForm")
  public String addBoard(@PathVariable("table") String table, BoardDto boardDto, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
    // 파일 업로드
//    Map<String, String> attachFile = fileComponent.uploadFile(Define.FILE_DIR, file);
//    if(attachFile!=null){
//      boardDto.setOriginalFileName(attachFile.get("originalFileName"));
//      boardDto.setFileName(attachFile.get("fileName"));
//    }

    boardDto.setIsOpen((boardDto.getIsOpen()==null)?"N":boardDto.getIsOpen());
    if(authentication != null) {
      boardDto.setRegisterIdx((Long) authentication.getPrincipal()); // 로그인 PK
      boardDto.setModifyIdx((Long) authentication.getPrincipal()); // 로그인 PK
    }
    Long addIdx = boardService.addBoard(table, boardDto);

    if(addIdx == 0){
      model.addAttribute("message", "저장 오류가 발생하였습니다.");
      model.addAttribute("href", "back");
      return "message";
    }

    redirectAttributes.addAttribute("table", table);
    redirectAttributes.addAttribute("boardIdx", addIdx);
    return "redirect:/bbs/{table}/detail/{boardIdx}";
  }

  // 수정
  @PostMapping("bbs/{table}/editForm/{id}")
  public String editBoard(@PathVariable("table") String table, @PathVariable("id") Long boardIdx, BoardDto boardDto, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
    BoardDto boardDtoUpdate = boardService.getBoard(table, boardIdx); // 기존정보
    BeanUtils.copyProperties(boardDto,boardDtoUpdate);

    // 파일 업로드
//    Map<String, String> attachFile = fileComponent.uploadFile(Define.FILE_DIR, file);
//    if(attachFile!=null){
//      // 기존 파일 삭제
//      if(boardDtoUpdate.getFileName() != null){
//        fileComponent.removeFile(Define.FILE_DIR, boardDtoUpdate.getFileName());
//      }
//
//      boardDtoUpdate.setOriginalFileName(attachFile.get("originalFileName"));
//      boardDtoUpdate.setFileName(attachFile.get("fileName"));
//    }

    boardDtoUpdate.setIsOpen((boardDto.getIsOpen()==null)?"N":boardDto.getIsOpen());
    if(authentication != null) boardDtoUpdate.setModifyIdx((Long) authentication.getPrincipal()); // 로그인 PK
    Long editIdx = boardService.editBoard(table, boardDtoUpdate);

    if(editIdx == 0){
      model.addAttribute("message", "수정 오류가 발생하였습니다.");
      model.addAttribute("href", "back");
      return "message";
    }

    redirectAttributes.addAttribute("table", table);
    redirectAttributes.addAttribute("boardIdx", editIdx);
    return "redirect:/bbs/{table}/detail/{boardIdx}";
  }

  // 삭제
  @PostMapping("bbs/{table}/delete")
  @ResponseBody
  public HashMap<String, String> deleteBoard(@PathVariable("table") String table, @RequestParam(name = "boardIdx", required = false) Long boardIdx, Authentication authentication) {
    HashMap<String, String> rtn = new HashMap<>();

    BoardDto boardDto = boardService.getBoard(table, boardIdx); // 기존정보
    // 본인 게시물만 삭제 등 조건 필요
    if(boardDto != null) {
      // 코멘트 파일 삭제
      // 코멘트 하트 삭제
      // 코멘트 삭제
      // 파일 삭제
//      if(boardDto.getFileName() != null){
//        fileComponent.removeFile(Define.FILE_DIR, boardDto.getFileName());
//      }

      boardDto.setIsDelete("Y");
      if(authentication != null) boardDto.setModifyIdx((Long) authentication.getPrincipal()); // 로그인 PK
      Long deleteIdx = boardService.deleteBoard(table, boardDto);

      if(deleteIdx == 0){
        rtn.put("status", "0001");
        rtn.put("msg", "삭제 오류가 발생하였습니다.");
      }else {
        rtn.put("status", "0000");
        rtn.put("msg", "데이터를 삭제하였습니다.");
      }
    }else{
      rtn.put("status", "0002");
      rtn.put("msg", "삭제 오류가 발생하였습니다.");
    }
    return rtn;
  }
}
