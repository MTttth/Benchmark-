package com.example.pojo.dto;

import com.example.pojo.general.OrderInfo;
import com.example.pojo.general.UserInfoBase;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustInfoDTO {
    private UserInfoBase userInfoBase;
    private List<OrderInfo> orderInfos;
}
