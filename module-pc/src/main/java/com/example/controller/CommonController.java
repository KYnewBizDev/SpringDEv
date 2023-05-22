package com.example.controller;

import com.example.Define;
import com.example.common.FileComponent;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CommonController {
  private final FileComponent fileComponent;

  // 썸머에디터 이미지업로드
  @PostMapping(value = "/common/EditorUploadImage", produces = "application/json")
  @ResponseBody
  public JsonObject EditorUploadImage(@RequestParam("file") MultipartFile multipartFile) {
    JsonObject jsonObject = new JsonObject();
    try {
      // 파일 업로드
      Map<String, String> attachFile = fileComponent.uploadFile(Define.FILE_DIR, multipartFile);

      jsonObject.addProperty("url", Define.FILE_URL + attachFile.get("fileName"));
      jsonObject.addProperty("responseCode", "success");
    } catch (IOException e) {
      jsonObject.addProperty("responseCode", "error");
      e.printStackTrace();
    }
    return jsonObject;
  }

  // 이미지 파일 뷰어
  @ResponseBody
  @GetMapping("/common/imgDown/{fileName}")
  public Resource imgDown(@PathVariable("fileName") String fileName) {
    return fileComponent.fileAsResource(Define.FILE_DIR, fileName);
  }

  // 파일 다운로드
  @GetMapping("/common/fileDown/{fileName}/{originalFileName}")
  @ResponseBody
  public ResponseEntity<Resource> fileDownload(@PathVariable("fileName") String fileName, @PathVariable("originalFileName") String originalFileName) {
    return fileComponent.downloadFile(Define.FILE_DIR, fileName, originalFileName);
  }

  // 파일 다운로드 Zip
  @GetMapping("fileDownZip")
  public void fileDownloadZip(HttpServletResponse response) {
    // 파일 다운로드 명
    String zipName = "일괄 ZIP 다운로드";

    // 다운로드할 파일 목록
    List<String> files = new ArrayList<>();
    files.add("test.jpg");
    files.add("test2.jpg");
    files.add("test3.txt");

    // 파일 다운로드 Zip
    fileComponent.downloadFileZip(Define.FILE_DIR, zipName, files, response);
  }
}