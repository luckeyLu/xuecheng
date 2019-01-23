package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  异常捕获类
 * Created by lwenf on 2019-01-08.
 */
@ControllerAdvice
public class ExceptionCatch {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    // 存放费自定义异常类型与错误代码映射，ImmutableMap特点：一点创建不可改变，且线程安全
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;

    //使用builder来构建一个异常类型和错误代码的异常
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();

    static {
        //在这里加入一些基础的异常类型判断
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALIDPARAM);
        builder.put(HttpRequestMethodNotSupportedException.class, CommonCode.INVALIDPARAM);
    }



    /**
     * 捕获自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public ResponseResult catchCustomException(CustomException e){
        LOGGER.error("catch CustomException : {}\r\nexception: ",e.getMessage(),e);
        ResultCode resultCode = e.getResultCode();
        String msg = e.getMsg();
        if (!StringUtils.isEmpty(msg)){
            return new ResponseResult(resultCode, msg);
        }
        return new ResponseResult(resultCode);
    }

    /**
     *  捕获非自定义的异常
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public ResponseResult catchException(Exception e){
        LOGGER.error("catch exception : {}\r\nexception: ",e.getMessage(),e);
        if (EXCEPTIONS == null) {
            EXCEPTIONS = builder.build();
        }
        ResultCode resultCode = EXCEPTIONS.get(e.getClass());
        if (resultCode != null) {
            return new ResponseResult((resultCode));
        }
        return new ResponseResult(CommonCode.SERVER_ERROR);
    }

}
