package com.xuecheng.manage_course.dao.jpa;

import com.xuecheng.framework.domain.course.CourseBase;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator.
 */
public interface CourseBaseDao extends JpaRepository<CourseBase,String> {

    public CourseBase findByName(String name);
}
