package com.xuecheng.manage_course.dao.mapper;

import com.github.pagehelper.Page;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface CourseMapper {
   /**
    * 根据id查询课程基本信息
    * @param id
    * @return
    */
   CourseBase findCourseBaseById(String id);

   /**
    *  分页查询课程列表
    * @return
    */
   Page<CourseInfo> queryByPagination();
}
