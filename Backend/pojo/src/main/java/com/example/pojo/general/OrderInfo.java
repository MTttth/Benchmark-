package com.example.pojo.general;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderInfo {
    private String  oOrderstatus; //订单状态
    private double oTotalprice; //订单总价
    private LocalDate oOrderdate; //下订单日期
    private String oClerk; //收银员
    private Double LQuantity; // 商品数量
    private Double LExtendedprice; // 商品单价
    private Double LDiscount; // 折扣
    private Double LTex; // 税
    private LocalDate LReceiptdate; // 实际到达日期
}
