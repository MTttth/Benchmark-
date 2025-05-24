package com.example.pojo.dto;

import lombok.Data;

@Data
public class ExplianPlan {
    String selectType;
    String table;
    String type;
    String key;
    String ref;
    int rows;
}
