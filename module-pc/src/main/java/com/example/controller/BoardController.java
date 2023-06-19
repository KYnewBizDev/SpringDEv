package com.example.controller;

import com.example.Define;
import com.example.common.FileComponent;
import com.example.db.board.dto.BoardDto;
import com.example.db.board.dto.BoardSearchDto;
import com.example.db.boardComment.dto.BoardCommentDto;
import com.example.db.boardCommentFile.dto.BoardCommentFileDto;
import com.example.db.boardFile.dto.BoardFileDto;
import com.example.db.boardGroup.dto.BoardGroupDto;
import com.example.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;

import static com.example.utils.BeanUtil.getNullPropertyNames;

@Controller
@RequiredArgsConstructor
public class BoardController {
  private final BoardService boardService;
  private final BoardGroupService boardGroupService;
  private final BoardCommentService boardCommentService;
  private final BoardFileService boardFileService;
  private final BoardCommentFileService boardCommentFileService;
  private final FileComponent fileComponent;

  // 게시판
  @GetMapping({"bbs/{table}","bbs/{table}/{mode}","bbs/{table}/{mode}/{id}"})
  public String board(@PathVariable("table") String table, @PathVariable(name = "mode", required = false) String mode, @PathVariable(name = "id", required = false) Long id, HttpServletRequest req, Model model) {
    // get skin
    BoardGroupDto boardGroup = boardGroupService.getBoardGroupCode(table);
    String skin = boardGroup.getSkin();
    if(mode==null) mode="list";

    // basic 스킨
    if(skin.equals("basic")) {
      switch (mode) {
        case "list" -> {
          return basicList(table, model, req);
        }
        case "detail" -> {
          editHit(table, id);
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
    int page = (req.getParameter("page")!=null && !Objects.equals(req.getParameter("page"), ""))? Integer.parseInt(req.getParameter("page")) : 1;
    int perPage = (req.getParameter("perPage")!=null && !Objects.equals(req.getParameter("perPage"), ""))? Integer.parseInt(req.getParameter("perPage")) : 20;

    BoardSearchDto boardSearchDto = new BoardSearchDto(table, req.getParameter("searchType"), req.getParameter("searchWord"), req.getParameter("startDate"), req.getParameter("endDate"), req.getParameter("isOpen"), req.getParameter("category1"), req.getParameter("category2"));
    Page<BoardDto> list = boardService.getBoardBasicList(table,page-1, perPage, boardSearchDto);

    model.addAttribute("table", table);
    model.addAttribute("list", list);
    model.addAttribute("page", page);
    return "board/basic/list";
  }

  // 뷰 (basic)
  public String basicDetail(String table, Long boardIdx, Model model) {
    BoardDto row = boardService.getBoard(table, boardIdx);
    List<BoardFileDto> files = boardFileService.getBoardFileList(row.getBoardGroupIdx(), boardIdx);

    if(row.getBoardIdx() == null){
      model.addAttribute("message", "잘못된 접근입니다.");
      model.addAttribute("href", "back");
      return "message";
    }

    // 답변
    BoardDto reply = boardService.getBoardReply(table, boardIdx);
    reply.setParentIdx(boardIdx);
    List<BoardFileDto> replyFiles = new ArrayList<>();
    if(reply.getBoardIdx()!=null){
      replyFiles = boardFileService.getBoardFileList(row.getBoardGroupIdx(), reply.getBoardIdx());
    }

    // 댓글
    BoardCommentDto comment = new BoardCommentDto();
    comment.setBoardIdx(boardIdx);
    Page<BoardCommentDto> commentList = boardCommentService.getBoardCommentList(table, boardIdx, 0, 20);

    model.addAttribute("table", table);
    model.addAttribute("row", row);
    model.addAttribute("files", files);
    model.addAttribute("reply", reply);
    model.addAttribute("replyFiles", replyFiles);
    model.addAttribute("comment", comment);
    model.addAttribute("commentList", commentList);
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
    List<BoardFileDto> files = boardFileService.getBoardFileList(row.getBoardGroupIdx(), boardIdx);

    if(row.getBoardIdx() == null){
      model.addAttribute("message", "잘못된 접근입니다.");
      model.addAttribute("href", "back");
      return "message";
    }
    model.addAttribute("table", table);
    model.addAttribute("row", row);
    model.addAttribute("files", files);
    return "board/basic/editForm";
  }
  //endregion basic


  //endregion /** 스킨 **/

  // 조회수
  public void editHit(@PathVariable("table") String table, @PathVariable("id") Long boardIdx) {
    boardService.editHit(table, boardIdx);
  }

  // 상단고정/노출 적용
  @PostMapping("bbs/{table}/editTopOpen")
  @ResponseBody
  public HashMap<String, String> editTopOpen(@PathVariable("table") String table, @RequestParam("boardIdxs[]") List<Long> boardIdxs, @RequestParam("isTops[]") List<String> isTops, @RequestParam("isOpens[]") List<String> isOpens, Authentication authentication) {
    HashMap<String, String> rtn = new HashMap<>();

    int i = 0;
    Long editIdx = 0L;
    for (Long boardIdx : boardIdxs) {
      BoardDto boardDto = new BoardDto();
      boardDto.setBoardIdx(boardIdx);
      boardDto.setIsTop(isTops.get(i));
      boardDto.setIsOpen(isOpens.get(i));
      boardDto.setModifyIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
      editIdx = boardService.editTopOpen(table, boardDto);
      i++;
    }

    if (editIdx == 0) {
      rtn.put("status", "0001");
      rtn.put("msg", "수정 오류가 발생하였습니다.");
    } else {
      rtn.put("status", "0000");
      rtn.put("msg", "데이터를 수정하였습니다.");
    }
    return rtn;
  }


  //region /** crud **/
  // 등록
  @PostMapping({"bbs/{table}/addForm", "bbs/{table}/addReply"})
  public String addBoard(@PathVariable("table") String table, BoardDto boardDto, @RequestParam("file[]") MultipartFile[] files, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
    // get boardGroupIdx
    BoardGroupDto boardGroup = boardGroupService.getBoardGroupCode(table);
    Long boardGroupIdx = boardGroup.getBoardGroupIdx();
    boardDto.setBoardGroupIdx(boardGroupIdx);

    boardDto.setRegisterIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
    boardDto.setModifyIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
    Long addIdx = boardService.addBoard(table, boardDto);

    if(addIdx > 0) {
      // 파일 업로드
      Map<String, String> attachFile;
      int i=0;
      for (MultipartFile file : files) {
        i++;
        try {
          attachFile = fileComponent.uploadFile(Define.FILE_DIR, file);
          if (attachFile != null) {
            BoardFileDto boardFileDto = new BoardFileDto();
            boardFileDto.setBoardGroupIdx(boardGroupIdx);
            boardFileDto.setBoardIdx(addIdx);
            boardFileDto.setOriginalFileName(attachFile.get("originalFileName"));
            boardFileDto.setFileName(attachFile.get("fileName"));
            boardFileDto.setIsMobile("N");
            boardFileDto.setSort(i);
            boardFileDto.setRegisterIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
            boardFileDto.setModifyIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
            boardFileService.saveBoardFile(boardFileDto);
          }
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }else{
      model.addAttribute("message", "저장 오류가 발생하였습니다.");
      model.addAttribute("href", "back");
      return "message";
    }
    if(boardDto.getParentIdx()!=null) addIdx = boardDto.getParentIdx();

    redirectAttributes.addAttribute("table", table);
    redirectAttributes.addAttribute("boardIdx", addIdx);
    return "redirect:/bbs/{table}/detail/{boardIdx}";
  }

  // 수정
  @PostMapping({"bbs/{table}/editForm/{id}", "bbs/{table}/editReply/{id}"})
  public String editBoard(@PathVariable("table") String table, @PathVariable("id") Long boardIdx, BoardDto boardDto, @RequestParam("file[]") MultipartFile[] files, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
    // 기존정보
    BoardDto boardDtoUpdate = boardService.getBoard(table, boardIdx);
    BeanUtils.copyProperties(boardDto, boardDtoUpdate, getNullPropertyNames(boardDto));

    // 본인 게시물만 수정 등 조건 필요
    boardDtoUpdate.setModifyIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
    Long editIdx = boardService.editBoard(table, boardDtoUpdate);

    if(editIdx > 0) {
      // 파일 업로드
      Map<String, String> attachFile;
      int i=0;
      for (MultipartFile file : files) {
        i++;
        try {
          attachFile = fileComponent.uploadFile(Define.FILE_DIR, file);
          if (attachFile != null) {

            // 기존 파일 삭제
            BoardFileDto delDto = boardFileService.getBoardFile(boardDtoUpdate.getBoardGroupIdx(), editIdx, i);
            if(delDto.getBoardFileIdx()!=null){
              fileComponent.removeFile(Define.FILE_DIR, delDto.getFileName());
              delDto.setIsDelete("Y");
              delDto.setModifyIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
              boardFileService.saveBoardFile(delDto);
            }

            BoardFileDto boardFileDto = new BoardFileDto();
            boardFileDto.setBoardGroupIdx(boardDtoUpdate.getBoardGroupIdx());
            boardFileDto.setBoardIdx(editIdx);
            boardFileDto.setOriginalFileName(attachFile.get("originalFileName"));
            boardFileDto.setFileName(attachFile.get("fileName"));
            boardFileDto.setIsMobile("N");
            boardFileDto.setSort(i);
            boardFileDto.setRegisterIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
            boardFileDto.setModifyIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
            boardFileService.saveBoardFile(boardFileDto);
          }
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }else{
      model.addAttribute("message", "수정 오류가 발생하였습니다.");
      model.addAttribute("href", "back");
      return "message";
    }
    if(boardDto.getParentIdx()!=null) editIdx = boardDto.getParentIdx();

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
    if(boardDto.getBoardIdx() != null) {
      // 코멘트 하트 삭제
      // 코멘트 파일 삭제
      // 코멘트 삭제

      // 파일 삭제
      List<BoardFileDto> boardFileDtoList = boardFileService.getBoardFileList(boardDto.getBoardGroupIdx(), boardIdx);
      for (BoardFileDto boardFileDto : boardFileDtoList) {
        fileComponent.removeFile(Define.FILE_DIR, boardFileDto.getFileName());
        boardFileDto.setIsDelete("Y");
        boardFileDto.setModifyIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
        boardFileService.saveBoardFile(boardFileDto);
      }

      boardDto.setIsDelete("Y");
      boardDto.setModifyIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
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
  //endregion /** crud **/

  //region /** comment crud **/
  // 등록 (댓글)
  @PostMapping("bbs/{table}/addComment")
  public String addBoardComment(@PathVariable("table") String table, BoardCommentDto boardCommentDto, @RequestParam("file[]") MultipartFile[] files, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
    // get boardGroupIdx
    BoardGroupDto boardGroup = boardGroupService.getBoardGroupCode(table);
    Long boardGroupIdx = boardGroup.getBoardGroupIdx();
    boardCommentDto.setBoardGroupIdx(boardGroupIdx);

    boardCommentDto.setRegisterIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
    boardCommentDto.setModifyIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
    Long addIdx = boardCommentService.addBoardComment(table, boardCommentDto);

    if(addIdx > 0) {
      // 파일 업로드
      Map<String, String> attachFile;
      int i=0;
      for (MultipartFile file : files) {
        i++;
        try {
          attachFile = fileComponent.uploadFile(Define.FILE_DIR, file);
          if (attachFile != null) {
            BoardCommentFileDto boardCommentFileDto = new BoardCommentFileDto();
            boardCommentFileDto.setBoardGroupIdx(boardCommentDto.getBoardGroupIdx());
            boardCommentFileDto.setBoardCommentIdx(addIdx);
            boardCommentFileDto.setOriginalFileName(attachFile.get("originalFileName"));
            boardCommentFileDto.setFileName(attachFile.get("fileName"));
            boardCommentFileDto.setIsMobile("N");
            boardCommentFileDto.setSort(i);
            boardCommentFileDto.setRegisterIdx((authentication != null) ? (Long) authentication.getPrincipal() : null); // 로그인 PK
            boardCommentFileDto.setModifyIdx((authentication != null) ? (Long) authentication.getPrincipal() : null); // 로그인 PK
            boardCommentFileService.saveBoardCommentFile(boardCommentFileDto);
          }
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }else{
      model.addAttribute("message", "저장 오류가 발생하였습니다.");
      model.addAttribute("href", "back");
      return "message";
    }

    redirectAttributes.addAttribute("table", table);
    redirectAttributes.addAttribute("boardIdx", boardCommentDto.getBoardIdx());
    return "redirect:/bbs/{table}/detail/{boardIdx}";
  }

  // 수정 (댓글)
  @PostMapping("bbs/{table}/editComment/{id}")
  public String editBoardComment(@PathVariable("table") String table, @PathVariable("id") Long boardCommentIdx, BoardCommentDto boardCommentDto, @RequestParam("file[]") MultipartFile[] files, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
    // 기존정보
    BoardCommentDto boardCommentDtoUpdate = boardCommentService.getBoardComment(table, boardCommentIdx);
    BeanUtils.copyProperties(boardCommentDto, boardCommentDtoUpdate, getNullPropertyNames(boardCommentDto));

    // 본인 게시물만 수정 등 조건 필요
    boardCommentDtoUpdate.setModifyIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
    Long editIdx = boardCommentService.editBoardComment(table, boardCommentDtoUpdate);

    if(editIdx > 0) {
      // 파일 업로드
      Map<String, String> attachFile;
      int i=0;
      for (MultipartFile file : files) {
        i++;
        try {
          attachFile = fileComponent.uploadFile(Define.FILE_DIR, file);
          if (attachFile != null) {

            // 기존 파일 삭제
            BoardCommentFileDto delDto = boardCommentFileService.getBoardCommentFile(boardCommentDtoUpdate.getBoardGroupIdx(), editIdx, i);
            if(delDto.getBoardCommentFileIdx()!=null){
              fileComponent.removeFile(Define.FILE_DIR, delDto.getFileName());
              delDto.setIsDelete("Y");
              delDto.setModifyIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
              boardCommentFileService.saveBoardCommentFile(delDto);
            }

            BoardCommentFileDto boardCommentFileDto = new BoardCommentFileDto();
            boardCommentFileDto.setBoardGroupIdx(boardCommentDtoUpdate.getBoardGroupIdx());
            boardCommentFileDto.setBoardCommentIdx(editIdx);
            boardCommentFileDto.setOriginalFileName(attachFile.get("originalFileName"));
            boardCommentFileDto.setFileName(attachFile.get("fileName"));
            boardCommentFileDto.setIsMobile("N");
            boardCommentFileDto.setSort(i);
            boardCommentFileDto.setRegisterIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
            boardCommentFileDto.setModifyIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
            boardCommentFileService.saveBoardCommentFile(boardCommentFileDto);
          }
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }else{
      model.addAttribute("message", "수정 오류가 발생하였습니다.");
      model.addAttribute("href", "back");
      return "message";
    }
    if(boardCommentDto.getBoardIdx()!=null) editIdx = boardCommentDto.getBoardIdx();

    redirectAttributes.addAttribute("table", table);
    redirectAttributes.addAttribute("boardIdx", editIdx);
    return "redirect:/bbs/{table}/detail/{boardIdx}";
  }

  // 삭제 (댓글)
  @PostMapping("bbs/{table}/deleteComment")
  @ResponseBody
  public HashMap<String, String> deleteBoardComment(@PathVariable("table") String table, @RequestParam(name = "boardCommentIdx", required = false) Long boardCommentIdx, Authentication authentication) {
    HashMap<String, String> rtn = new HashMap<>();

    BoardCommentDto boardCommentDto = boardCommentService.getBoardComment(table, boardCommentIdx); // 기존정보
    // 본인 게시물만 삭제 등 조건 필요
    if(boardCommentDto.getBoardCommentIdx() != null) {
      // 코멘트 하트 삭제
      // 코멘트 파일 삭제
      List<BoardCommentFileDto> boardCommentFileDtoList = boardCommentFileService.getBoardCommentFileList(boardCommentDto.getBoardGroupIdx(), boardCommentIdx);
      for (BoardCommentFileDto boardCommentFileDto : boardCommentFileDtoList) {
        fileComponent.removeFile(Define.FILE_DIR, boardCommentFileDto.getFileName());
        boardCommentFileDto.setIsDelete("Y");
        boardCommentFileDto.setModifyIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
        boardCommentFileService.saveBoardCommentFile(boardCommentFileDto);
      }

      boardCommentDto.setIsDelete("Y");
      boardCommentDto.setModifyIdx((authentication!=null)?(Long) authentication.getPrincipal():null); // 로그인 PK
      Long deleteIdx = boardCommentService.deleteBoardComment(table, boardCommentDto);

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
  //endregion /** comment crud **/
}
