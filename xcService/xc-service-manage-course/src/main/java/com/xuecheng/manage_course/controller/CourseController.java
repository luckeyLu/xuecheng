package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseResult;
import com.xuecheng.framework.model.pagination.PaginationVo;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lwenf on 2019-02-19.
 */
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {

    @Autowired
    private CourseService courseService;

    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public CourseResult<PaginationVo<CourseInfo>> queryCourseListByPagination(@PathVariable int page, @PathVariable int size, CourseListRequest courseListRequest) {
        return courseService.queryCourseListByPagination(page, size, courseListRequest);
    }

    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public CourseResult<TeachplanNode> queryTeachplanByCourseId(@PathVariable String courseId) {
        return courseService.queryTeachpalanByCourseId(courseId);
    }

    @Override
    @PostMapping("/teachplan/add")
    public CourseResult<Teachplan> addTeachplan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachplan(teachplan);
    }
}
