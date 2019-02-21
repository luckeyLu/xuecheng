package com.xuecheng.manage_course.dao.jpa;

import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator.
 */
public interface TeachplanDao extends JpaRepository<Teachplan,String> {
    public List<Teachplan> findByCourseidAndParentid(String courseId, String parentId);
}
