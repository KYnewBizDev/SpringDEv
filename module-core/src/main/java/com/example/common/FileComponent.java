package com.example.common;

import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.util.FileUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

// 파일업로드, 다운로드
public class FileComponent {
  // 파일 저장이름
  public String createFilename(String str) {
    String rtn;
    try {
      MessageDigest mdMD5;
      mdMD5 = MessageDigest.getInstance("MD5");
      mdMD5.update(str.getBytes(StandardCharsets.UTF_8));
      byte[] md5Hash = mdMD5.digest();
      StringBuilder hexMD5hash = new StringBuilder();
      for(byte b : md5Hash) {
        String hexString = String.format("%02x", b);
        hexMD5hash.append(hexString);
      }
      rtn = hexMD5hash.toString();
    } catch (NoSuchAlgorithmException e) {
      // throw new RuntimeException(e);
      rtn = UUID.randomUUID().toString();
    }

    return rtn + System.currentTimeMillis();
  }

  // 파일 업로드
  public Map<String, String> uploadFile(String savePath, MultipartFile multipartFile) throws IOException {
    if (multipartFile.isEmpty()) {
      return null;
    }

    // 폴더 생성
    if (!new File(savePath).exists()) {
      try {
        new File(savePath).mkdir();
      } catch (Exception e) {
        e.getStackTrace();
      }
    }

    String originalFileName = multipartFile.getOriginalFilename();
    String ext = Objects.requireNonNull(originalFileName).substring(originalFileName.lastIndexOf(".") + 1);
    String fileName = createFilename(originalFileName)+ '.' + ext.toLowerCase();

    // 파일 저장
    save(multipartFile, savePath, fileName);

    return new HashMap<>() {{
      put("fileName", fileName);
      put("originalFileName", originalFileName);
    }};
  }

  // 파일 저장
  public void save(MultipartFile multipartFile, String savePath, String fileName) throws IOException {
    multipartFile.transferTo(new File(savePath + fileName));
  }

  // 파일 삭제
  public void removeFile(String savePath, String fileName){
    File file = new File(savePath + fileName);
    if(file.exists() && !file.isDirectory()) {
      // del 폴더에 복제 후 삭제처리
      String bucket = savePath.substring(0,savePath.lastIndexOf("uploads/"))+"uploads/";
      String folder = savePath.substring(bucket.length());

      try {
        FileUtil.copyFile(file, new File(bucket+"del/"+folder+fileName));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      // 삭제
      file.delete();
    }
  }

  // 파일 resource
  public Resource fileAsResource(String savePath, String fileName) {
    try {
      Path file = Path.of(savePath).resolve(fileName);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return null;
  }

  // 파일 다운로드
  public ResponseEntity<Resource> downloadFile(String savePath, String fileName, String originalFileName) {
    Resource resource = fileAsResource(savePath, fileName);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + UriUtils.encode(originalFileName, StandardCharsets.UTF_8) + "\"")
        .body(resource);
  }

  // 파일 다운로드 ZIP
  public void downloadFileZip(String savePath, String zipName, List<String> files, HttpServletResponse response) {
    zipName = URLEncoder.encode(zipName, StandardCharsets.UTF_8);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("application/zip");
    response.setHeader("Content-Disposition", "attachment; filename="+zipName+".zip");

    try {
      List<File> fileList = new ArrayList<>();
      for(String fileName : files){
        fileList.add(new File(savePath + fileName));
      }

      ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
      for(File file : fileList) {
        zipOut.putNextEntry(new ZipEntry(file.getName()));

        FileInputStream fis = new FileInputStream(file);
        StreamUtils.copy(fis, zipOut);
        fis.close();

        zipOut.closeEntry();
      }
      zipOut.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
