package com.example.excel.style.custom;

import com.example.excel.configurer.ExcelCellStyleConfigurer;
import com.example.excel.style.align.DefaultExcelAlign;
import com.example.excel.style.border.DefaultExcelBorders;
import com.example.excel.style.border.ExcelBorderStyle;

public class BlueBodyStyle extends CustomExcelCellStyle {

    @Override
    public void configure(ExcelCellStyleConfigurer configurer) {
        configurer.foregroundColor(223, 235, 246)
                .excelBorders(DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN))
                .excelAlign(DefaultExcelAlign.CENTER_CENTER);
    }

}