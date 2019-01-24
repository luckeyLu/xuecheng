package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.model.pagination.PaginationVo;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lwenf on 2019-01-02.
 */
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    private CmsPageService cmsPageService;

    @Override
    @GetMapping("/list/{page}/{size}")
    public CmsResult<PaginationVo<CmsPage>> findList(@PathVariable int page, @PathVariable int size, QueryPageRequest queryPageRequest) {
        return cmsPageService.findList(page, size, queryPageRequest);
    }

    @PostMapping("/add")
    public CmsResult<CmsPage> add(@RequestBody CmsPage cmsPage){
        return cmsPageService.add(cmsPage);
    }

    @Override
    @GetMapping("/get/{id}")
    public CmsResult<CmsPage> findById(@PathVariable String id) {
        return cmsPageService.findById(id);
    }

    @Override
    @PutMapping("/edit/{id}")
    public CmsResult<CmsPage> edit(@PathVariable String id, @RequestBody CmsPage cmsPage) {
        return cmsPageService.edit(id, cmsPage);
    }

    @Override
    @GetMapping("/del/{id}")
    public CmsResult<Void> del(@PathVariable String id) {
        return cmsPageService.del(id);
    }

    @Override
    @GetMapping("/postPage/{id}")
    public CmsResult<Void> postPage(@PathVariable String id) {
        return cmsPageService.postPage(id);
    }

    public void setCmsPageService(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }
}
