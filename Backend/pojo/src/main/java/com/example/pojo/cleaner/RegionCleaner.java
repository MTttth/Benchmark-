package com.example.pojo.cleaner;

import com.example.commom.exception.DataValidationException;
import com.example.pojo.entity.Region;
import com.example.pojo.general.DataCleaner;
import org.springframework.stereotype.Component;

@Component("RegionCleaner")
public class RegionCleaner implements DataCleaner {

    @Override
    public Object clean(String[] rawData) throws DataValidationException {
        Region region = null;

        try {
            region = new Region();

            region.setRRegionkey(Integer.parseInt(rawData[0]));
            region.setRName(rawData[1]);
            region.setRComment(rawData[2]);
        } catch (NumberFormatException e) {
            throw new DataValidationException("数据格式错误");
        }

        return region;
    }
}

