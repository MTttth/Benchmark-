package com.example.commom.utils;

import com.example.commom.JdbcColumn;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JdbcArgsConverter {
    public static Object[] convert(Object entity) {
        List<Field> fields = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(JdbcColumn.class))
                .sorted(Comparator.comparingInt(f -> f.getAnnotation(JdbcColumn.class).order()))
                .collect(Collectors.toList());

        Object[] args = new Object[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).setAccessible(true);
            try {
                args[i] = fields.get(i).get(entity);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("字段访问失败: " + fields.get(i).getName(), e);
            }
        }
        return args;
    }
}