package com.xuecheng.manage_cms.repository;

import com.xuecheng.framework.domain.cms.CmsSite;

import java.util.List;

/**
 * cmsSite仓储
 * Created by lwenf on 2019-01-29.
 */
public interface CmsSitRepository {

    /**
     *  查询全部记录
     * @return
     */
    public List<CmsSite> queryAll();
}
