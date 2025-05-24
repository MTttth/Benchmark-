package com.example.pojo.entity;

import com.example.commom.JdbcColumn;
import lombok.Data;

@Data
public class Part {
    @JdbcColumn(order = 0)
    private int PPartkey;
    @JdbcColumn(order = 1)
    private String PName;
    @JdbcColumn(order = 2)
    private String PMfgr;
    @JdbcColumn(order = 3)
    private String PBrand;
    @JdbcColumn(order = 4)
    private String PType;
    @JdbcColumn(order = 5)
    private int PSize;
    @JdbcColumn(order = 6)
    private String PContainer;
    @JdbcColumn(order = 7)
    private Double PRetailprice;
    @JdbcColumn(order = 8)
    private String PComment;
}
