package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.pagination.PaginationBaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by lwenf on 2019-01-01.
 *
 *  接收页面查询的条件
 *
 */

@Data
public class QueryPageRequest extends PaginationBaseVo {

    // 站点ID
    @ApiModelProperty("站点ID")
    private String siteId;

    // 页面ID
    @ApiModelProperty("页面ID")
    private String pageId;

    // 页面名称
    @ApiModelProperty("页面名称")
    private String pageName;

    //别名
    @ApiModelProperty("别名")
    private String pageAliase;

    // 模板ID
    @ApiModelProperty("模板ID")
    private String templateId;

}
