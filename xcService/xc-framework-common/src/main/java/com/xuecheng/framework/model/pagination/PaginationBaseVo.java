package com.xuecheng.framework.model.pagination;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 *  分页基本信息
 * Created by lwenf on 2019-01-23.
 */
@Data
@ToString
public class PaginationBaseVo {

    /**
     *  当前页
     */
    @ApiModelProperty("当前页")
    private int currentPage;

    /**
     *  每页记录数
     */
    @ApiModelProperty("每页记录数")
    private int pageSize;

    /**
     * 总共记录数
     */
    @ApiModelProperty("总记录数")
    private long totalRecords;
}
