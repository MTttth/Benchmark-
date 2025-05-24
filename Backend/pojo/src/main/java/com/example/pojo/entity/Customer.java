package com.example.pojo.entity;

import com.example.commom.JdbcColumn;
import lombok.Data;

@Data
public class Customer {
    @JdbcColumn(order = 0)
    private int CCustkey;
    @JdbcColumn(order = 1)
    private String CName;
    @JdbcColumn(order = 2)
    private String CAddress;
    @JdbcColumn(order = 3)
    private int CNationkey;
    @JdbcColumn(order = 4)
    private String CPhone;
    @JdbcColumn(order = 5)
    private Double CAcctbal;
    @JdbcColumn(order = 6)
    private String CMktsegment;
    @JdbcColumn(order = 7)
    private String CComment;

}
