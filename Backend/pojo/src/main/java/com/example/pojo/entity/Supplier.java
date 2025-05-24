package com.example.pojo.entity;

import com.example.commom.JdbcColumn;
import lombok.Data;

@Data
public class Supplier {
    @JdbcColumn(order = 0)
    private int SSuppkey;
    @JdbcColumn(order = 1)
    private String SName;
    @JdbcColumn(order = 2)
    private String SAddress;
    @JdbcColumn(order = 3)
    private int SNationkey;
    @JdbcColumn(order = 4)
    private String SPhone;
    @JdbcColumn(order = 5)
    private Double SAcctbal;
    @JdbcColumn(order = 6)
    private String SComment;
}
