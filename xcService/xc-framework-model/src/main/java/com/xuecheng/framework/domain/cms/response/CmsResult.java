package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;

/**
 *  通用从cms查询返回结果类
 * Created by lwenf on 2019-01-18.
 */
@Data
public class CmsResult<T> extends ResponseResult {

    // 结果对象
    private T resultData;

    public CmsResult(ResultCode resultCode) {
        super(resultCode);
    }

    public CmsResult(ResultCode resultCode,T resultData) {
        super(resultCode);
        this.resultData = resultData;
    }

    public CmsResult(ResultCode resultCode,T resultData, String msg) {
        super(resultCode,msg);
        this.resultData = resultData;
    }

    public static CmsResult newSuccessResult(){
        return new CmsResult(CommonCode.SUCCESS);
    }

    public static<T> CmsResult newSuccessResult(T obj){
        return new CmsResult(CommonCode.SUCCESS, obj);
    }

    public static<T> CmsResult newFailResult(T obj){
        return new CmsResult(CommonCode.FAIL, obj);
    }

    public static CmsResult newFailResult(String msg){
        return new CmsResult(CommonCode.FAIL, null, msg);
    }

    public static CmsResult newFailResult(){
        return new CmsResult(CommonCode.FAIL, null);
    }
}
