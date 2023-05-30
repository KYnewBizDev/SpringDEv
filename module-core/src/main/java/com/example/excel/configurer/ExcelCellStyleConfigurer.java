package com.example.excel.configurer;

import com.example.excel.style.align.ExcelAlign;
import com.example.excel.style.align.NoExcelAlign;
import com.example.excel.style.border.ExcelBorders;
import com.example.excel.style.border.NoExcelBorders;
import com.example.excel.style.color.DefaultExcelColor;
import com.example.excel.style.color.ExcelColor;
import com.example.excel.style.color.NoExcelColor;
import com.example.excel.style.font.ExcelFont;
import com.example.excel.style.font.NoExcelFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelCellStyleConfigurer {
	private ExcelAlign excelAlign = new NoExcelAlign();
	private ExcelColor foregroundColor = new NoExcelColor();
	private ExcelBorders excelBorders = new NoExcelBorders();
	private ExcelFont excelFont = new NoExcelFont();

	public ExcelCellStyleConfigurer() {

	}

	public ExcelCellStyleConfigurer excelAlign(ExcelAlign excelAlign) {
		this.excelAlign = excelAlign;
		return this;
	}

	public ExcelCellStyleConfigurer foregroundColor(int red, int blue, int green) {
		this.foregroundColor = DefaultExcelColor.rgb(red, blue, green);
		return this;
	}

	public ExcelCellStyleConfigurer excelBorders(ExcelBorders excelBorders) {
		this.excelBorders = excelBorders;
		return this;
	}

	public ExcelCellStyleConfigurer excelFont(ExcelFont excelFont) {
		this.excelFont = excelFont;
		return this;
	}

	public void configure(CellStyle cellStyle, Workbook workbook) {
		excelAlign.apply(cellStyle);
		foregroundColor.applyForeground(cellStyle);
		excelBorders.apply(cellStyle);
		excelFont.apply(cellStyle, workbook);
	}

}
