package com.example.pojo.entity;

import com.example.commom.JdbcColumn;
import lombok.Data;

@Data
public class Partsupp {
    @JdbcColumn(order = 0)
    private int PsPartkey;
    @JdbcColumn(order = 1)
    private int PsSuppkey;
    @JdbcColumn(order = 2)
    private int PsAvailqty;
    @JdbcColumn(order = 3)
    private Double PsSupplycost;
    @JdbcColumn(order = 4)
    private String PsComment;
}
