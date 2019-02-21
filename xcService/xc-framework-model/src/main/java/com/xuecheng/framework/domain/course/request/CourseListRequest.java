package com.xuecheng.framework.domain.course.request;

import com.xuecheng.framework.model.pagination.PaginationBaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by mrt on 2018/4/13.
 */
@Data
@ToString
public class CourseListRequest extends PaginationBaseVo {
    //公司Id
    @ApiModelProperty("公司Id")
    private String companyId;
}
