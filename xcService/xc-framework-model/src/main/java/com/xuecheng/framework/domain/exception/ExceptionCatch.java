package com.xuecheng.framework.domain.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.constants.CommonConstants;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import com.xuecheng.framework.utils.LoggerUtil;
import com.xuecheng.framework.utils.ThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  异常捕获类
 * Created by lwenf on 2019-01-29.
 */
@ControllerAdvice
public class ExceptionCatch {
    private static final Logger COMMON_ERROR_LOGGER = LoggerFactory.getLogger(CommonConstants.COMMON_ERROR_LOGGER);

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
        if (StringUtils.isEmpty(e.getMessage())){
            LoggerUtil.errorLog(COMMON_ERROR_LOGGER, e, "catch CustomException : "+e.getResultCode().message()+"\r\nexception: ");
        }else {
            LoggerUtil.errorLog(COMMON_ERROR_LOGGER, e, "catch CustomException : "+e.getResultCode().message()+e.getMessage()+"\r\nexception: ");
        }
        // 清除本地线程变量
        ThreadLocalUtil.clearRef();

        ResultCode resultCode = e.getResultCode();
        if (!StringUtils.isEmpty(e.getMessage())){
            return new ResponseResult(resultCode, e.getMessage());
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
        LoggerUtil.errorLog(COMMON_ERROR_LOGGER, e, "catch UnknownException : "+e.getMessage()+"\r\nexception: ");
        // 清除本地线程变量
        ThreadLocalUtil.clearRef();

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
