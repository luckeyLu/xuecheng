package com.xuecheng.framework.model.pagination;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 *  分页查询信息
 * Created by lwenf on 2019-01-23.
 */
@Data
@ToString
public class PaginationVo<E> extends PaginationBaseVo {

    /**
     *  数据列表
     */
    List<E> elements;
}
