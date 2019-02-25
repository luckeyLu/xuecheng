package com.xuecheng.manage_course.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.domain.course.response.CourseResult;
import com.xuecheng.framework.domain.course.template.CourseExecuteTemplate;
import com.xuecheng.framework.domain.course.template.CourseHandleCallback;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.pagination.PaginationVo;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.utils.LoggerUtil;
import com.xuecheng.manage_course.repository.CourseRepository;
import com.xuecheng.manage_course.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 *  课程计划服务接口实现
 * Created by lwenf on 2019-02-19.
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public CourseResult<TeachplanNode> queryTeachpalanByCourseId(String courseId) {
        return CourseExecuteTemplate.execute(new CourseHandleCallback<CourseResult<TeachplanNode>>() {
            @Override
            public String buildLog() {
                return LoggerUtil.buildParaLog("CourseService.queryTeachpalanByCourseId", "courseId", courseId);
            }

            @Override
            public void checkParams() {
                if (StringUtils.isEmpty(courseId)){
                    throw new CustomException(CommonCode.INVALIDPARAM, "入参为空！");
                }
            }

            @Override
            public CourseResult<TeachplanNode> doProcess() {
                TeachplanNode teachplanNode = courseRepository.queryTeachplanByCourseId(courseId);
                if (teachplanNode == null){
                    return CourseResult.newFailResult("查询结果为空！");
                }
                return CourseResult.newSuccessResult(teachplanNode);
            }
        });
    }

    @Override
    @Transactional
    public CourseResult<Teachplan> addTeachplan(Teachplan teachplan) {
        return CourseExecuteTemplate.execute(new CourseHandleCallback<CourseResult<Teachplan>>() {
            @Override
            public String buildLog() {
                return LoggerUtil.buildParaLog("CourseService.queryTeachpalanByCourseId", "Teachplan", teachplan);
            }

            @Override
            public void checkParams() {
                if (teachplan == null ||
                        StringUtils.isEmpty(teachplan.getCourseid())||StringUtils.isEmpty(teachplan.getPname())){
                    throw new CustomException(CommonCode.INVALIDPARAM, "入参为空，或者课程计划名称为空或者课程计划courseId为空！");
                }
                CourseBase courseBase = courseRepository.queryCourseById(teachplan.getCourseid());
                if (courseBase == null){
                    throw new CustomException(CourseCode.COURSE_TEACHPLAN_NOTOCOURSEBASE , "课程计划未关联到课程！");
                }
            }

            @Override
            public CourseResult<Teachplan> doProcess() {
                String parentId = teachplan.getParentid();
                if (StringUtils.isEmpty(teachplan.getParentid())){
                    // 父节点为空,则取根节点；
                    Teachplan teachplanRoot = getTeachplanRoot(teachplan.getCourseid());
                    parentId = teachplanRoot.getId();
                    teachplan.setParentid(parentId);
                }
                Teachplan pTeachplan = courseRepository.queryTeachplanById(parentId);
                if (pTeachplan == null){
                    throw new CustomException(CourseCode.COURSE_TEACHPLAN_PTEACHPLANISNULL , "课程计划父节点为空");
                }
                if (StringUtils.equals("1", pTeachplan.getGrade())){
                    teachplan.setGrade("2");
                }else if (StringUtils.equals("2", pTeachplan.getGrade())){
                    teachplan.setGrade("3");
                }
                // 保存课程计划
                Teachplan newTeachplan = courseRepository.addTeachplan(teachplan);

                return CourseResult.newSuccessResult(newTeachplan);
            }
        });
    }

    @Override
    public CourseResult<PaginationVo<CourseInfo>> queryCourseListByPagination(int page, int size, CourseListRequest courseListRequest) {
        return CourseExecuteTemplate.execute(new CourseHandleCallback<CourseResult<PaginationVo<CourseInfo>>>() {
            @Override
            public String buildLog() {
                return LoggerUtil.
                        buildParaLog("CourseService.queryCourseListByPagination", "page", page, "size", size, "CourseListRequest", courseListRequest);
            }

            @Override
            public void checkParams() {
                if (courseListRequest == null){
                    courseListRequest.setCurrentPage(1);
                    courseListRequest.setPageSize(10);
                }
                if (page > 0){
                    courseListRequest.setCurrentPage(page);
                }
                if (size > 0){
                    courseListRequest.setPageSize(size);
                }
            }

            @Override
            public CourseResult<PaginationVo<CourseInfo>> doProcess() {
                // 第一页从 1 开始
                if (0 == courseListRequest.getCurrentPage()){
                    courseListRequest.setCurrentPage(1);
                }
                /**使用 mybatis的PageHelper分页插件设置分页参数
                 * 第一个参数：当前页；
                 * 第二个参数：每页显示条数
                 * */
                PageHelper.startPage(courseListRequest.getCurrentPage(), courseListRequest.getPageSize());
                Page<CourseInfo> courseInfos = courseRepository.queryCourseByPagination(courseListRequest);
                PaginationVo<CourseInfo> paginationVo = new PaginationVo<>();
                paginationVo.setElements(courseInfos.getResult());
                paginationVo.setTotalRecords(courseInfos.getTotal());
                paginationVo.setCurrentPage(courseListRequest.getCurrentPage());
                paginationVo.setPageSize(courseListRequest.getPageSize());

                return CourseResult.newSuccessResult(paginationVo);
            }
        });
    }

    @Override
    public CourseResult<CategoryNode> queryCategoryList() {
        return CourseExecuteTemplate.execute(new CourseHandleCallback<CourseResult<CategoryNode>>() {
            @Override
            public String buildLog() {
                return LoggerUtil.
                        buildParaLog("CourseService.queryCategoryList");
            }

            @Override
            public void checkParams() {

            }

            @Override
            public CourseResult<CategoryNode> doProcess() {
                return CourseResult.newSuccessResult(courseRepository.queryCategoryList());
            }
        });
    }

    @Override
    public CourseResult<CourseBase> addCourseBase(CourseBase courseBase) {
        return CourseExecuteTemplate.execute(new CourseHandleCallback<CourseResult<CourseBase>>() {
            @Override
            public String buildLog() {
                return LoggerUtil.
                        buildParaLog("CourseService.addCourseBase", "CourseBase", courseBase);
            }

            @Override
            public void checkParams() {
                if ((courseBase == null) ||
                        StringUtils.isEmpty(courseBase.getName()) ||
                        StringUtils.isEmpty(courseBase.getGrade()) ||
                        StringUtils.isEmpty(courseBase.getStudymodel())){
                    throw new CustomException(CommonCode.INVALIDPARAM, "添加课程基本信息入参有误！");
                }
                CourseBase courseBaseNew = courseRepository.queryCourseByName(courseBase.getName());
                if (courseBaseNew != null){
                    throw new CustomException(CourseCode.COURSE_BASE_ISNOTONLY,"课程名称重复！");
                }
                if (StringUtils.isNotEmpty(courseBase.getCompanyId())){
                    courseBase.setCompanyId(null);
                }
            }

            @Override
            public CourseResult<CourseBase> doProcess() {
                return CourseResult.newSuccessResult(courseRepository.addCourseBase(courseBase));
            }
        });
    }

    /**
     *  根据courseId 获取课程计划的根节点
     * @param courseId
     * @return
     */
    private Teachplan getTeachplanRoot(String courseId){
        List<Teachplan> teachplans = courseRepository.queryTeachplanByCourseIdAndParentid(courseId, "0");
        if (!CollectionUtils.isEmpty(teachplans)&&teachplans.size()>1){
            throw new CustomException(CourseCode.COURSE_TEACHPLAN_ROOTNOTONLY ,"根节点课程计划不唯一！");
        }
        CourseBase courseBase = courseRepository.queryCourseById(courseId);
        if (CollectionUtils.isEmpty(teachplans)){
            // 根节点为空，则添加一条根节点
            Teachplan teachplan = new Teachplan();
            teachplan.setCourseid(courseId);
            teachplan.setPname(courseBase.getName());
            teachplan.setParentid("0");//0 - 根节点
            teachplan.setGrade("1"); // 1级
            teachplan.setOrderby(1); // 排序
            teachplan.setStatus("0"); // 0 - 未发布
            Teachplan resultTeachplan = courseRepository.addTeachplan(teachplan);
            return resultTeachplan;
        }
        return teachplans.get(0);
    }
}
