package com.xuecheng.manage_course.dao.mapper;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by lwenf on 2019-02-19.
 */
@Mapper
public interface TeachplanMapper {
    public TeachplanNode queryTeachplanByCourseId(String courseId);
}
