package com.example.pojo.general;

import lombok.Data;

@Data
public class UserInfoBase {
    private String cName; //客户姓名
    private String address; // 客户地址
    private String phone; // 客户电话号码
    private String nationName; // 客户的国家
    private String CAcctbal; // 客户账户余额
    private String CMktsegment; // 客户市场领域
}
