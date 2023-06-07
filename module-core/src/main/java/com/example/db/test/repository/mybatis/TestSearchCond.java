package com.example.db.test.repository.mybatis;

public class TestSearchCond {
    String tableName;
    Long id;
    String isDelete;

    public TestSearchCond() {
    }

    public TestSearchCond(String tableName, Long id, String isDelete) {
        this.tableName = tableName;
        this.id = id;
        this.isDelete = isDelete;
    }
}
