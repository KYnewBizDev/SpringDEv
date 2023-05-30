package com.example.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelCellStyle {

	void apply(CellStyle cellStyle, Workbook workbook);

}
