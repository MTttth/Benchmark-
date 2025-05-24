package com.example.server.service.Impl;

import com.example.pojo.general.DataCleaner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class CleanerFactory {
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 根据表名动态获取清洗器实例
     * @param tableName 数据库表名（需与Bean命名一致）
     * @return 对应的清洗器实例
     */
    @SuppressWarnings("unchecked")
    public <T> DataCleaner<T> getCleaner(String tableName) {
        String beanName = tableName + "Cleaner";

            // 通过Spring容器获取已注册的Bean
        return (DataCleaner<T>) applicationContext.getBean(beanName);

    }
}