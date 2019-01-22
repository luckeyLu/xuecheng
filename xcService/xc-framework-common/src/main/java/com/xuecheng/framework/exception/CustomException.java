package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * Created by lwenf on 2019-01-08.
 */
public class CustomException extends RuntimeException {

    private ResultCode resultCode;

    private String msg;

    public CustomException(ResultCode resultCode){
        this.resultCode = resultCode;
    }

    public CustomException(ResultCode resultCod, String msg){
        this.resultCode = resultCod;
        this.msg = msg;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
