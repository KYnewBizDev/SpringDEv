package com.example.excel.annotation;

import com.example.excel.style.ExcelCellStyle;

public @interface ExcelColumnStyle {
    Class<? extends ExcelCellStyle> excelCellStyleClass();
    String enumName() default "";

}