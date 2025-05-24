package com.example.pojo.general;


import com.example.commom.exception.DataValidationException;

public interface DataCleaner<T> {
    /**
     * 数据清洗入口方法
     * @param rawData 原始数据行
     * @return 清洗后的实体对象
     * @throws DataValidationException
     */
    T clean(String[] rawData) throws DataValidationException;
}