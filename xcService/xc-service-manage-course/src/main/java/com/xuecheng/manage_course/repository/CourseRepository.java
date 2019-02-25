package com.xuecheng.manage_course.repository;

import com.github.pagehelper.Page;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;

import java.util.List;

/**
 *  课程计划仓储接口
 * Created by lwenf on 2019-02-19.
 */
public interface CourseRepository {

    /**
     *  根据courseId 查询课程计划
     * @param courseId
     * @return
     */
    public TeachplanNode queryTeachplanByCourseId(String courseId);

    /**
     *  根据courseId 查询课程基本信息
     * @param id
     * @return
     */
    public CourseBase queryCourseById(String id);

    /**
     *  根据courseId和父节点id 查询课程计划
     * @param courseId
     * @param parentId
     * @return
     */
    public List<Teachplan> queryTeachplanByCourseIdAndParentid(String courseId, String parentId);

    /**
     *  添加课程计划
     * @param teachplan
     * @return
     */
    public Teachplan addTeachplan(Teachplan teachplan);

    /**
     *  根据id查询课程计划
     * @param id
     * @return
     */
    public Teachplan queryTeachplanById(String id);

    /**
     *  条件分页查询 课程列表
     * @param courseListRequest
     * @return
     */
    public Page<CourseInfo> queryCourseByPagination(CourseListRequest courseListRequest);

    /**
     *  查询课程分类
     * @return
     */
    public CategoryNode queryCategoryList();

    /**
     *  根据课程名称查询课程基本信息
     * @param name
     * @return
     */
    public CourseBase queryCourseByName(String name);

    /**
     *  添加课程基本信息
     * @param courseBase
     * @return
     */
    public CourseBase addCourseBase(CourseBase courseBase);
}
