package com.example.pojo.cleaner;

import com.example.commom.exception.DataValidationException;
import com.example.pojo.entity.Part;
import com.example.pojo.general.DataCleaner;
import org.springframework.stereotype.Component;

@Component
public class PartCleaner implements DataCleaner {

    @Override
    public Object clean(String[] rawData) throws DataValidationException {
        Part part = new Part();

        try {
            part.setPPartkey(Integer.parseInt(rawData[0]));
            part.setPName(rawData[1]);
            part.setPMfgr(rawData[2]);
            part.setPBrand(rawData[3]);
            part.setPType(rawData[4]);
            part.setPSize(Integer.parseInt(rawData[5]));
            part.setPContainer(rawData[6]);
            part.setPRetailprice(Double.parseDouble(rawData[7]));
            part.setPComment(rawData[8]);
        } catch (NumberFormatException e) {
            throw new DataValidationException("数据格式不正确");
        }


        return part;
    }
}

