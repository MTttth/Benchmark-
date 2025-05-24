package com.example.pojo.cleaner;

import com.example.commom.exception.DataValidationException;
import com.example.pojo.entity.Partsupp;
import com.example.pojo.general.DataCleaner;
import org.springframework.stereotype.Component;

@Component
public class PartsuppCleaner implements DataCleaner {

    @Override
    public Object clean(String[] rawData) throws DataValidationException {
        Partsupp partsupp = new Partsupp();

        try {
            partsupp.setPsPartkey(Integer.parseInt(rawData[0]));
            partsupp.setPsSuppkey(Integer.parseInt(rawData[1]));
            partsupp.setPsAvailqty(Integer.parseInt(rawData[2]));
            partsupp.setPsSupplycost(Double.parseDouble(rawData[3]));
            partsupp.setPsComment(rawData[4]);
        } catch (NumberFormatException e) {
            throw new DataValidationException("数据格式错误");
        }

        return partsupp;
    }
}
