package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.domain.cms.template.CmsExecuteTemplate;
import com.xuecheng.framework.domain.cms.template.CmsHandleCallback;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.utils.LoggerUtil;
import com.xuecheng.manage_cms.repository.SysDictionaryRepository;
import com.xuecheng.manage_cms.service.SysDictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统字典管理服务层接口实现
 * Created by lwenf on 2019-02-23.
 */
@Service
public class SysDictionaryServiceImpl implements SysDictionaryService {

    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    @Override
    public CmsResult<SysDictionary> queryByType(String type) {
        return CmsExecuteTemplate.execute(new CmsHandleCallback<CmsResult<SysDictionary>>() {
            @Override
            public String buildLog() {
                return LoggerUtil.buildParaLog("SysDictionaryService.queryByType", "type", type);
            }

            @Override
            public void checkParams() {
                if (StringUtils.isEmpty(type)){
                    throw new CustomException(CommonCode.INVALIDPARAM, "入参为空！");
                }
            }

            @Override
            public CmsResult<SysDictionary> doProcess() {
                SysDictionary sysDictionary = sysDictionaryRepository.queryByType(type);
                return CmsResult.newSuccessResult(sysDictionary);
            }
        });
    }
}
