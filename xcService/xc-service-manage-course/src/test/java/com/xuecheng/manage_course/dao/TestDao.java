package com.xuecheng.manage_course.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.manage_course.dao.jpa.CourseBaseDao;
import com.xuecheng.manage_course.dao.mapper.CourseMapper;
import com.xuecheng.manage_course.dao.mapper.TeachplanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDao {
    @Autowired
    CourseBaseDao courseBaseRepository;
    @Autowired
    CourseMapper courseMapper;

    @Autowired
    TeachplanMapper teachplanMapper;

    @Test
    public void testCourseBaseRepository(){
        Optional<CourseBase> optional = courseBaseRepository.findById("402885816240d276016240f7e5000002");
        if(optional.isPresent()){
            CourseBase courseBase = optional.get();
            System.out.println(courseBase);
        }

    }

    @Test
    public void testCourseMapper(){
        CourseBase courseBase = courseMapper.findCourseBaseById("402885816240d276016240f7e5000002");
        System.out.println(courseBase);
    }

    @Test
    public void testTeachplanMapper(){
        TeachplanNode teachplanNode = teachplanMapper.queryTeachplanByCourseId("4028e58161bd22e60161bd23672a0001");
        System.out.println(teachplanNode.toString());
    }

    @Test
    public void testPaginationCourseInfo(){
        PageHelper.startPage(2,15);
        Page<CourseInfo> courseInfos = courseMapper.queryByPagination();
        List<CourseInfo> result = courseInfos.getResult();
        long total = courseInfos.getTotal();
        System.out.println(result);
        System.out.println(total);

    }
}
