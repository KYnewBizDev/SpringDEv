package com.example.excel.style.custom;

import com.example.excel.configurer.ExcelCellStyleConfigurer;
import com.example.excel.style.align.DefaultExcelAlign;
import com.example.excel.style.border.DefaultExcelBorders;
import com.example.excel.style.border.ExcelBorderStyle;

public class PinkHeaderStyle extends CustomExcelCellStyle {
    @Override
    public void configure(ExcelCellStyleConfigurer configurer) {
        configurer.foregroundColor(255, 203, 192)
                .excelBorders(DefaultExcelBorders.newInstance(ExcelBorderStyle.DOTTED))
                .excelAlign(DefaultExcelAlign.CENTER_CENTER);
    }

}