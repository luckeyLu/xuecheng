package com.xuecheng.framework.domain.course.template;

import com.xuecheng.framework.model.response.ResponseResult;

/**
 * Created by lwenf on 2019-02-14.
 */
public interface CourseHandleCallback<K> {

    public String buildLog();

    /**
     *  入参校验
     */
    public void checkParams();

    /**
     *  执行核心计算或查询过程
     * @param <K>
     * @return
     */
    public <K extends ResponseResult>K doProcess();
}
