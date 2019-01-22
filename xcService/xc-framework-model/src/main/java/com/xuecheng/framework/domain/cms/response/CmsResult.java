package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;

/**
 *  通用从cms查询返回结果类
 * Created by lwenf on 2019-01-18.
 */
@Data
public class CmsResult<T> extends ResponseResult {
    private T resultData;
    public CmsResult(ResultCode resultCode,T resultData) {
        super(resultCode);
        this.resultData = resultData;
    }
}
