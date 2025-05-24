package com.example.pojo.entity;

import com.example.commom.JdbcColumn;
import lombok.Data;

@Data
public class Nation {
    @JdbcColumn(order = 0)
    private int NNationkey;
    @JdbcColumn(order = 1)
    private String NName;
    @JdbcColumn(order = 2)
    private int NRegionkey;
    @JdbcColumn(order = 3)
    private String NComment;

}