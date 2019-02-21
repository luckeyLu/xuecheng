package com.xuecheng.framework.domain.course.response;

import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;

/**
 * Created by lwenf on 2019-02-14.
 */
@Data
public class CourseResult<T> extends ResponseResult {

    // 结果对象
    private T resultData;

    /**
     * 改造函数
     * @param resultCode
     */
    public CourseResult(ResultCode resultCode){
        super(resultCode);
    }

    /**
     * 改造函数
     * @param resultCode
     * @param resultData
     */
    public CourseResult(ResultCode resultCode, T resultData){
        super(resultCode);
        this.resultData = resultData;
    }

    /**
     *  带自定义信息的改造函数
     * @param resultCode
     * @param resultData
     * @param message
     */
    public CourseResult(ResultCode resultCode, T resultData, String message){
        super(resultCode, message);
        this.resultData = resultData;
    }

    public static CourseResult newSuccessResult(){
        return new CourseResult(CommonCode.SUCCESS);
    }

    public static<T> CourseResult newSuccessResult(T obj){
        return new CourseResult(CommonCode.SUCCESS, obj);
    }

    public static<T> CourseResult newFailResult(T obj){
        return new CourseResult(CommonCode.FAIL, obj);
    }

    public static<T> CourseResult newFailResult(String message){
        return new CourseResult(CommonCode.FAIL, null, message);
    }

    public static<T> CourseResult newFailResult(T obj, String message){
        return new CourseResult(CommonCode.FAIL, obj, message);
    }
}
