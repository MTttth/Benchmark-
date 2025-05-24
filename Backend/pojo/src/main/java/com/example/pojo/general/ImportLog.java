package com.example.pojo.general;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ImportLog {
    private String userName;
    private String tableName;
    private int totlal;
    private LocalDateTime importTime;

}
