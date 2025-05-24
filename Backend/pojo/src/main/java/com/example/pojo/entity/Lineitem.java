package com.example.pojo.entity;

import com.example.commom.JdbcColumn;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class Lineitem {
    @JdbcColumn(order = 0)
    private int LOrderkey;
    @JdbcColumn(order = 1)
    private int LPartkey;
    @JdbcColumn(order = 2)
    private int LSuppkey;
    @JdbcColumn(order = 3)
    private int LLinenumber;
    @JdbcColumn(order = 4)
    private Double LQuantity;
    @JdbcColumn(order = 5)
    private Double LExtendedprice;
    @JdbcColumn(order = 6)
    private Double LDiscount;
    @JdbcColumn(order = 7)
    private Double LTex;
    @JdbcColumn(order = 8)
    private String  LReturnflag;
    @JdbcColumn(order = 9)
    private String LLinestatus;
    @JdbcColumn(order = 10)
    private LocalDate LShipdate;
    @JdbcColumn(order = 11)
    private LocalDate LCommitdate;
    @JdbcColumn(order = 12)
    private LocalDate LReceiptdate;
    @JdbcColumn(order = 13)
    private String LShipnstruct;
    @JdbcColumn(order = 14)
    private String LShipmode;
    @JdbcColumn(order = 15)
    private String LComment;
}
