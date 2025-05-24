package com.example.pojo.entity;

import com.example.commom.JdbcColumn;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Orders {

    @JdbcColumn(order = 0)
    private int oOrderkey;
    @JdbcColumn(order = 1)
    private int oCustkey;
    @JdbcColumn(order = 2)
    private String  oOrderstatus;
    @JdbcColumn(order = 3)
    private double oTotalprice;
    @JdbcColumn(order = 4)
    private LocalDate oOrderdate;
    @JdbcColumn(order = 5)
    private String oOrderpriori;
    @JdbcColumn(order = 6)
    private String oClerk;
    @JdbcColumn(order = 7)
    private int oShipppriority;
    @JdbcColumn(order = 8)
    private String oComment;

}
