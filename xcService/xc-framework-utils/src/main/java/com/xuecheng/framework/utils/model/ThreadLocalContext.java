package com.xuecheng.framework.utils.model;

import lombok.Data;
import lombok.ToString;

/**
 *  业务本地线程上线文
 * Created by lwenf on 2019-01-29.
 */
@Data
@ToString
public class ThreadLocalContext {

    /**
     * 业务流水单号，全局唯一，贯穿整个线程
     * */
    private String bizNo;
}
