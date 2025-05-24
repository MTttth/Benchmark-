package com.example.server.hander;


import com.example.commom.constant.MessageConstant;
import com.example.commom.exception.BaseException;
import com.example.commom.exception.DataValidationException;
import com.example.commom.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        if (ex instanceof DataValidationException) {
            throw ex; // 重新抛出后不会被此方法处理
        }
        log.error("异常信息：{}"+ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error("异常信息：{}", ex.getMessage());
        String message = ex.getMessage();
        if(message.contains("Duplicate entry")){
            String msg = "用户名已存在";
            return Result.error(msg);
        }else{
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }

    }
}
