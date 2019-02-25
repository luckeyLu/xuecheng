package com.xuecheng.manage_course.dao.mapper;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

/**
 *  课程分类mapper
 * Created by lwenf on 2019-02-23.
 */
@Mapper
public interface CategoryMapper {

    /**
     *  查询全部课程分类
     * @return
     */
    public CategoryNode queryList();
}
