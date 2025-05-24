package com.example.pojo.cleaner;

import com.example.commom.exception.DataValidationException;
import com.example.pojo.entity.Lineitem;
import com.example.pojo.general.DataCleaner;
import org.springframework.stereotype.Component;

import java.text.ParseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LineitemCleaner implements DataCleaner {

    @Override
    public Object clean(String[] rawData) throws DataValidationException {
        Lineitem lineitem = new Lineitem();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        lineitem.setLOrderkey(Integer.parseInt(rawData[0]));
        lineitem.setLPartkey(Integer.parseInt(rawData[1]));
        lineitem.setLSuppkey(Integer.parseInt(rawData[2]));
        lineitem.setLLinenumber(Integer.parseInt(rawData[3]));
        lineitem.setLQuantity(Double.parseDouble(rawData[4]));
        lineitem.setLExtendedprice(Double.parseDouble(rawData[5]));
        lineitem.setLDiscount(Double.parseDouble(rawData[6]));
        lineitem.setLTex(Double.parseDouble(rawData[7]));
        lineitem.setLReturnflag(rawData[8]);
        lineitem.setLLinestatus(rawData[9]);
        lineitem.setLShipdate(LocalDate.parse(rawData[10],formatter));
        lineitem.setLCommitdate(LocalDate.parse(rawData[11],formatter));
        lineitem.setLReceiptdate(LocalDate.parse(rawData[12],formatter));

        lineitem.setLShipnstruct(rawData[13]);
        lineitem.setLShipmode(rawData[14]);
        lineitem.setLComment(rawData[15]);

        return lineitem;
    }
}
