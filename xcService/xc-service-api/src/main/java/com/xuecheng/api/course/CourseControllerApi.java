package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseResult;
import com.xuecheng.framework.model.pagination.PaginationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 *  课程计划管理接口
 * Created by lwenf on 2019-02-14.
 */
@Api(value="课程计划管理接口",description="课程计划管理接口，课程计划的增、删、改、查")
public interface CourseControllerApi {

    /**
     *  分页查询课程
     * @param page
     * @param size
     * @param courseListRequest
     * @return
     */
    @ApiOperation("分页查询课程列表")
    @ApiImplicitParams({@ApiImplicitParam(name="page",value="页码",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="size",value="每页记录数",required=true,paramType="path",dataType="int")})
    public CourseResult<PaginationVo<CourseInfo>> queryCourseListByPagination(int page, int size, CourseListRequest courseListRequest);

    /**
     *  根据课程id查询该课程的课程计划分类
     * @param courseId
     * @return
     */
    @ApiOperation("根据课程计划id查询课程计划分类")
    public CourseResult<TeachplanNode> queryTeachplanByCourseId(String courseId);

    /**
     *  添加课程计划
     * @param teachplan
     * @return
     */
    @ApiOperation("添加课程计划")
    public CourseResult<Teachplan> addTeachplan(Teachplan teachplan);
}
