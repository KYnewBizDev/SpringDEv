package com.example.common;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class ExcelComponent {
  // 엑셀 업로드
  public ArrayList<List<String>> uploadExcel(MultipartFile multipartFile) {
    ArrayList<List<String>> rtn = new ArrayList<>();

    String originalFileName = multipartFile.getOriginalFilename();
    String ext = Objects.requireNonNull(originalFileName).substring(originalFileName.lastIndexOf(".") + 1);
    ext = ext.toLowerCase();

    // 엑셀 파일만 업로드가능하도록 처리
    String[] extArr = {"xls", "xlsx"};
    List<String> extList = new ArrayList<>(Arrays.asList(extArr));
    if (!extList.contains(ext)) {
      return null;
    }

    try {
      OPCPackage opcPackage = OPCPackage.open(multipartFile.getInputStream());
      XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
      XSSFSheet sheet = workbook.getSheetAt(0); // 첫번째 시트

      int cells = 0; // 열 갯수
      if(sheet.getRow(0) != null) cells = sheet.getRow(0).getLastCellNum();

      for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        XSSFRow row = sheet.getRow(i);
        XSSFCell cell;
        if (row == null) continue;

        List<String> rowData = new ArrayList<>();
        for(int j=0; j<cells; j++){
          rowData.add(row.getCell(j).toString());
        }
        rtn.add(rowData);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rtn;
  }

  // 엑셀 다운로드
  public void downloadExcel(String fileName, List<String> header, ArrayList<List<String>> body, HttpServletResponse response) throws IOException {
    Workbook wb = new XSSFWorkbook();
    Sheet sheet = wb.createSheet("Sheet1");
    Row row;
    Cell cell;
    int rowNum = 0;
    int cellNum = -1;

    // Header
    row = sheet.createRow(rowNum);
    for(String h : header){
      cell = row.createCell(++cellNum);
      cell.setCellValue(h);
    }

    // Body
    for (List<String> rtn : body) {
      row = sheet.createRow(++rowNum);
      for (int i=0; i<rtn.size(); i++){
        cell = row.createCell(i);
        cell.setCellValue(rtn.get(i));
      }
    }

    LocalDateTime now = LocalDateTime.now();
    fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("ms-vnd/excel");
    response.setHeader("Content-Disposition", "attachment;filename="+fileName+"_"+now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+".xlsx");
    wb.write(response.getOutputStream());
    wb.close();
  }
}
