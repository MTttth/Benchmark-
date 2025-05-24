package com.example.pojo.general;

import lombok.Data;

@Data
public class ErrorLog {
    private String error;
    private int line;
    private String tableName;
}
