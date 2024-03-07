package com.ApiJava.JavaApi.Model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRoleEnum {
  ADMIN("admin"),

  HOST("host"),

  CLIENT("client");

  private String value;

  UserRoleEnum(String value) {
    this.value = value;
  }

  public static UserRoleEnum fromValue(String value) {
    for (UserRoleEnum b : UserRoleEnum.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
