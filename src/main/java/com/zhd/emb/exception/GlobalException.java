package com.zhd.emb.exception;

import com.zhd.emb.pojo.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author asd48
 * 表示是一个控制器的增强类 如果有异常就会拦截异常
 */
@RestControllerAdvice
public class GlobalException {
    /**
     * 处理与sql相关的异常
     * */
    @ExceptionHandler(SQLException.class)
    public RespBean mySqlExcption(SQLException e){
        if(e instanceof SQLIntegrityConstraintViolationException){
            return RespBean.error("该数据有关联数据，操作失败");
        }
        return RespBean.error("数据库异常 操作失败");
    }
}
