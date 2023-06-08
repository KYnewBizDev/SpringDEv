package com.example.db.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardSearchDto {

    String table;
    String searchType;
    String searchWord;
    String startDate;
    String endDate;
    String isOpen;
    String category1;
    String category2;
    String isDelete;

    @Override
    public String toString() {
        return "BoardSearchDto{" +
            "table='" + table + '\'' +
            ", searchType='" + searchType + '\'' +
            '}';
    }
}
