package com.example.pojo.cleaner;

import com.example.commom.exception.DataValidationException;
import com.example.pojo.entity.Supplier;
import com.example.pojo.general.DataCleaner;
import org.springframework.stereotype.Component;

@Component("supplierCleaner")
public class SupplierCleaner implements DataCleaner {

    @Override
    public Object clean(String[] rawData) throws DataValidationException {
        Supplier supplier = null;
        try {
            supplier = new Supplier();
            supplier.setSSuppkey(Integer.parseInt(rawData[0]));
            supplier.setSName(rawData[1]);
            supplier.setSAddress(rawData[2]);
            supplier.setSNationkey(Integer.parseInt(rawData[3]));
            supplier.setSPhone(rawData[4]);
            supplier.setSAcctbal(Double.parseDouble(rawData[5]));
            supplier.setSComment(rawData[6]);
        } catch (NumberFormatException e) {
            throw new DataValidationException("数据格式错误");
        }

        return supplier;

    }
}
