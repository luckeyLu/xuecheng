package com.xuecheng.manage_course.repository.impl;

import com.github.pagehelper.Page;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.manage_course.dao.jpa.CourseBaseDao;
import com.xuecheng.manage_course.dao.jpa.TeachplanDao;
import com.xuecheng.manage_course.dao.mapper.CategoryMapper;
import com.xuecheng.manage_course.dao.mapper.CourseMapper;
import com.xuecheng.manage_course.dao.mapper.TeachplanMapper;
import com.xuecheng.manage_course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 *  课程计划仓储接口实现
 * Created by lwenf on 2019-02-19.
 */
@Component
public class CourseRepositoryImpl implements CourseRepository {

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private CourseBaseDao courseBaseDao;

    @Autowired
    private TeachplanDao teachplanDao;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public TeachplanNode queryTeachplanByCourseId(String courseId) {
        return teachplanMapper.queryTeachplanByCourseId(courseId);
    }

    @Override
    public CourseBase queryCourseById(String id) {
        Optional<CourseBase> optional = courseBaseDao.findById(id);
        if (optional != null && optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Teachplan> queryTeachplanByCourseIdAndParentid(String courseId, String parentId) {
        return teachplanDao.findByCourseidAndParentid(courseId, parentId);
    }

    @Override
    public Teachplan addTeachplan(Teachplan teachplan) {
        return teachplanDao.save(teachplan);
    }

    @Override
    public Teachplan queryTeachplanById(String id) {
        Optional<Teachplan> optional = teachplanDao.findById(id);
        if (optional != null && optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public Page<CourseInfo> queryCourseByPagination(CourseListRequest courseListRequest) {
        return courseMapper.queryByPagination();
    }

    @Override
    public CategoryNode queryCategoryList() {
        return categoryMapper.queryList();
    }

    @Override
    public CourseBase queryCourseByName(String name) {
        return courseBaseDao.findByName(name);
    }

    @Override
    public CourseBase addCourseBase(CourseBase courseBase) {
        return courseBaseDao.save(courseBase);
    }
}
