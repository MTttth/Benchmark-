package com.example.pojo.entity;

import com.example.commom.JdbcColumn;
import lombok.Data;

@Data
public class Region {
    @JdbcColumn(order = 0)
    private int RRegionkey;
    @JdbcColumn(order = 1)
    private String RName;
    @JdbcColumn(order = 2)
    private String RComment;
}
