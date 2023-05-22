package com.example.config.datasource;

public class DbContextHolder {

  private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

  public static void setDbType(String dbType) {
    if(dbType == null){
      throw new NullPointerException();
    }
    contextHolder.set(dbType);
  }

  public static String getDbType() {
    return contextHolder.get();
  }

  public static void clearDbType() {
    contextHolder.remove();
  }
}