package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseResult;
import com.xuecheng.framework.model.pagination.PaginationVo;

/**
 *  课程计划服务接口
 * Created by lwenf on 2019-02-19.
 */
public interface CourseService {

    /**
     *  根据courId查询课程计划
     * @param courseId
     * @return
     */
    public CourseResult<TeachplanNode> queryTeachpalanByCourseId(String courseId);

    /**
     *  添加课程计划
     * @param teachplan
     * @return
     */
    public CourseResult<Teachplan> addTeachplan(Teachplan teachplan);

    /**
     *  分页查询课程列表
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    public CourseResult<PaginationVo<CourseInfo>> queryCourseListByPagination(int page, int size, CourseListRequest courseListRequest);

    /**
     *  查询课程全部分类
     * @return
     */
    public CourseResult<CategoryNode> queryCategoryList();

    /**
     *  添加课程基本信息
     * @param courseBase
     * @return
     */
    public CourseResult<CourseBase> addCourseBase(CourseBase courseBase);
}
