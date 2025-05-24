package com.example.pojo.cleaner;

import com.example.commom.exception.DataValidationException;
import com.example.pojo.entity.Nation;
import com.example.pojo.general.DataCleaner;
import org.springframework.stereotype.Component;

@Component
public class NationCleaner implements DataCleaner {

    @Override
    public Object clean(String[] rawData) throws DataValidationException {
        Nation nation = null;
        try {
            nation = new Nation();
            nation.setNNationkey(Integer.parseInt(rawData[0]));
            nation.setNName(rawData[1]);
            nation.setNRegionkey(Integer.parseInt(rawData[2]));
            nation.setNComment(rawData[3]);
        } catch (NumberFormatException e) {
            throw new DataValidationException("数据格式不正确");
        }

        return nation;
    }
}

