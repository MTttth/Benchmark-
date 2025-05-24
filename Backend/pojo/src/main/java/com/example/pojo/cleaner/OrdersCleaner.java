package com.example.pojo.cleaner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.example.commom.exception.DataValidationException;
import com.example.pojo.entity.Orders;
import com.example.pojo.general.DataCleaner;
import org.springframework.stereotype.Component;

@Component("ordersCleaner")
public class OrdersCleaner implements DataCleaner<Orders> {
    @Override
    public Orders clean(String[] rawData) throws DataValidationException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        Orders order = null;
        try {
            order = new Orders();
            // 字段解析与清洗
            order.setOOrderkey(Integer.parseInt(rawData[0]));
            order.setOCustkey(Integer.parseInt(rawData[1]));
            order.setOOrderstatus(rawData[2]);
            order.setOTotalprice(Double.parseDouble(rawData[3]));
            order.setOOrderdate(LocalDate.parse(rawData[4], formatter));
            order.setOOrderpriori(rawData[5]);
            order.setOClerk(rawData[6]);
            order.setOShipppriority(Integer.parseInt(rawData[7]));
            order.setOComment(rawData[8]);
        } catch (Exception e) {
            throw new DataValidationException("订单数据格式错误");
        }
        return order;
    }
}
