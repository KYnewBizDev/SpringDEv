package com.example.common;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

// 파일업로드, 다운로드 (S3)
@RequiredArgsConstructor
public class FileS3Component extends FileComponent{
  private final AmazonS3Client amazonS3Client;

  // 파일 저장
  public void save(MultipartFile multipartFile, String savePath, String fileName) {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(multipartFile.getSize());
    objectMetadata.setContentType(multipartFile.getContentType());

    try (InputStream inputStream = multipartFile.getInputStream()) {
      amazonS3Client.putObject(new PutObjectRequest(savePath, fileName, inputStream, objectMetadata)
          .withCannedAcl(CannedAccessControlList.PublicRead));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  // 파일 삭제
  public void removeFile(String savePath, String fileName){
    boolean isObjectExist = amazonS3Client.doesObjectExist(savePath, fileName);
    if (isObjectExist) {
      // del 폴더에 복제 후 삭제처리
      String bucket = savePath.substring(0,savePath.lastIndexOf("/"));
      String folder = savePath.substring(bucket.length()+1)+"/";

      CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucket, folder+fileName, bucket, "del/"+folder+fileName);
      amazonS3Client.copyObject(copyObjectRequest);

      // 삭제
      amazonS3Client.deleteObject(savePath, fileName);
    }
  }

  // 파일 resource
  public Resource fileAsResource(String savePath, String fileName) {
    URL file = amazonS3Client.getUrl(savePath, fileName);
    Resource resource = new UrlResource(file);
    if (resource.exists() || resource.isReadable()) {
      return resource;
    }
    return null;
  }

  // 파일 다운로드 ZIP
  public void downloadFileZip(String savePath, String zipName, List<String> files, HttpServletResponse response) {
    zipName = URLEncoder.encode(zipName, StandardCharsets.UTF_8);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("application/zip");
    response.setHeader("Content-Disposition", "attachment; filename="+zipName+".zip");

    try {
      ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
      for(String fileName : files) {
        zipOut.putNextEntry(new ZipEntry(fileName));

        S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(savePath, fileName));
        InputStream is = s3Object.getObjectContent();
        byte[] bytes = new byte[is.available()];
        int length;
        while ((length = is.read(bytes)) >= 0) {
          zipOut.write(bytes, 0, length);
        }
        is.close();

        zipOut.closeEntry();
      }
      zipOut.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}