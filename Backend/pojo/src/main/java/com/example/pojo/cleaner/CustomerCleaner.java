package com.example.pojo.cleaner;

import com.example.commom.exception.DataValidationException;
import com.example.pojo.entity.Customer;
import com.example.pojo.general.DataCleaner;
import org.springframework.stereotype.Component;

@Component
public class CustomerCleaner implements DataCleaner {

    @Override
    public Object clean(String[] rawData) throws DataValidationException {
        Customer customer = new Customer();
        try {
            customer.setCCustkey(Integer.parseInt(rawData[0]));
            customer.setCName(rawData[1]);
            customer.setCAddress(rawData[2]);
            customer.setCNationkey(Integer.parseInt(rawData[3]));
            customer.setCPhone(rawData[4]);
            customer.setCAcctbal(Double.parseDouble(rawData[5]));
            customer.setCMktsegment(rawData[6]);
            customer.setCComment(rawData[7]);
        } catch (NumberFormatException e) {
            throw new DataValidationException("数据格式错误");
        }

        return customer;
    }
}
