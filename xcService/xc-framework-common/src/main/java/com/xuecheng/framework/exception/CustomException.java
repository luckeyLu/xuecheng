package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

/**
 *  自定义异常类
 * Created by lwenf on 2019-01-08.
 */
@Data
@ToString
public class CustomException extends RuntimeException {

    //  错误代码
    private ResultCode resultCode;

    // 自定义错误信息
    private String message;

    /**
     * 构造函数
     * @param resultCode
     */
    public CustomException(ResultCode resultCode){
        super(resultCode.message());
        this.resultCode = resultCode;
    }

    /**
     * 构造函数
     * @param resultCod
     * @param message
     */
    public CustomException(ResultCode resultCod, String message){
        super(resultCod.message()+"\n"+message);
        this.resultCode = resultCod;
        this.message = message;
    }

    /**
     * 构造函数
     * @param resultCod
     * @param message
     * @param tr
     */
    public CustomException(ResultCode resultCod, String message, Throwable tr){
        super(resultCod.message()+"\n"+message,tr);
        this.resultCode = resultCod;
        this.message = message;
    }
}
