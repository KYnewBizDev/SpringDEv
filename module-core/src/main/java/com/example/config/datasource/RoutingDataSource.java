package com.example.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Objects;

// DB 연결 routing
public class RoutingDataSource extends AbstractRoutingDataSource {
  @Override
  protected Object determineCurrentLookupKey() {
    if(Objects.equals(DbContextHolder.getDbType(), "sms")) return "sms";
    else return (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) ? "slave" : "master";
  }
}