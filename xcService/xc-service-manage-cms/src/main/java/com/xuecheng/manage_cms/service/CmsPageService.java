package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;


/**
 * Created by lwenf on 2019-01-03.
 */
@Service
public class CmsPageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CmsPageService.class);

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    /**
     *  页面分页查询
     * @param page 当前页，默认从第1页开始
     * @param size 每页记录数
     * @param queryPageRequest
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){
        // 1. 参数校验
        if(page <= 0){
            page = 1;
        }
        page = page -1;
        if (size <= 0){
            size = 10;
        }
        if (queryPageRequest == null){
            queryPageRequest = new QueryPageRequest();
        }

        // 2. 设置查询条件值
        CmsPage cmsPage = new CmsPage();
        // 2.1 精确查询 站点id、页面id、模板
        if (!StringUtils.isEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if (!StringUtils.isEmpty(queryPageRequest.getPageId())){
            cmsPage.setPageId(queryPageRequest.getPageId());
        }
        if (!StringUtils.isEmpty(queryPageRequest.getTemplateId())){
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        // 2.2 模糊查询 页面名称、别名
        if (!StringUtils.isEmpty(queryPageRequest.getPageAliase())){
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        if (!StringUtils.isEmpty(queryPageRequest.getPageName())){
            cmsPage.setPageName(queryPageRequest.getPageName());
        }
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);

        QueryResult<CmsPage> queryResult = new QueryResult<>();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());

        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     *  添加页面
     * @param cmsPage
     * @return
     */
    public CmsPageResult add(CmsPage cmsPage){
        // 1. 参数校验
        if (cmsPage == null){
            throw new CustomException(CommonCode.INVALIDPARAM);
        }
        CmsPage resultCmsPage = cmsPageRepository.findByPageNameAndPageWebPathAndSiteId(cmsPage.getPageName(), cmsPage.getPageWebPath(), cmsPage.getSiteId());
        if (resultCmsPage != null){
            throw new CustomException(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }

        cmsPage.setPageId(null);
        CmsPage save = cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, save);
    }

    /**
     *  根据id查询页面
     * @return
     */
    public CmsPageResult findById(String id){
        // 1. 查询校验
        if(StringUtils.isEmpty(id)){
            return new CmsPageResult(CommonCode.FAIL, null);
        }
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (!optional.isPresent()){
            return new CmsPageResult(CommonCode.FAIL, null);
        }
        return new CmsPageResult(CommonCode.SUCCESS, optional.get());
    }

    /**
     *  修改页面
     * @param id
     * @param cmsPage
     * @return
     */
    public CmsPageResult edit(String id, CmsPage cmsPage){
        // 1. 参数校验
        if(StringUtils.isEmpty(id)||cmsPage == null){
            throw new CustomException(CommonCode.INVALIDPARAM);
        }

        // 2. 校验被修改数据是否存在
        if(!StringUtils.isEmpty(cmsPage.getPageId())&&!StringUtils.equals(id, cmsPage.getPageId())){
            throw new CustomException(CommonCode.INVALIDPARAM, "页面主键有误！");
        }
        CmsPageResult result = this.findById(id);
        if (!result.isSuccess()||result.getCmsPage()==null){
            throw new CustomException(CmsCode.CMS_PAGE_NOTEXISTS);
        }

        /**
         * 更新操作
         */
        CmsPage one = result.getCmsPage();
        //更新模板id
        one.setTemplateId(cmsPage.getTemplateId());
        //更新所属站点
        one.setSiteId(cmsPage.getSiteId());
        //更新页面别名
        one.setPageAliase(cmsPage.getPageAliase());
        //更新页面名称
        one.setPageName(cmsPage.getPageName());
        //更新访问路径
        one.setPageWebPath(cmsPage.getPageWebPath());
        //更新物理路径
        one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
        // 更新创建时间
        one.setPageCreateTime(cmsPage.getPageCreateTime());
        // 更新数据url
        one.setDataUrl(cmsPage.getDataUrl());
        // 更新数据类型
        one.setPageType(cmsPage.getPageType());

        CmsPage save = cmsPageRepository.save(one);
        if (save == null){
           throw new CustomException(CommonCode.FAIL,"更新页面失败！");
        }
        return new CmsPageResult(CommonCode.SUCCESS, save);
    }

    /**
     *  根据id删除数据
     * @param id
     * @return
     */
    public ResponseResult del(String id){
        // 1. 参数校验
        if (StringUtils.isEmpty(id)){
            return new ResponseResult(CommonCode.INVALIDPARAM);
        }

        // 2.
        CmsPageResult result = this.findById(id);
        if (!result.isSuccess()||result.getCmsPage()==null){
            throw new CustomException(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        cmsPageRepository.deleteById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     *  页面静态化
     * @param pageId
     * @return
     */
    public CmsResult<String> getPageHtml(String pageId){
        // 校验入参
        if (StringUtils.isEmpty(pageId)){
            throw new CustomException(CommonCode.INVALIDPARAM, "入参为空！");
        }
        CmsPageResult pageResult = findById(pageId);
        CmsPage cmsPage = pageResult.getCmsPage();
        if (!pageResult.isSuccess()||cmsPage==null){
            throw new CustomException(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        // 1.获得页面模型数据
        Map model = getModel(cmsPage);

        // 2.获取页面模板
        String tempalate = getTempalate(cmsPage);

        // 3.执行静态化
        String html = generateHtml(tempalate, model);

        // 返回结果
        return new CmsResult<String>(CommonCode.SUCCESS, html);

    }

    /**
     *  通过freemaker 生成静态页面
     * @param template
     * @param model
     * @return
     */
    private String generateHtml(String template, Map model){
        String html = null;
        try {
            // 生成配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            //模板加载器
            StringTemplateLoader loader = new StringTemplateLoader();
            loader.putTemplate("template", template);
            //配置模板加载器
            configuration.setTemplateLoader(loader);
            // 获取模板
            Template pageTemplate = configuration.getTemplate("template");
            html = FreeMarkerTemplateUtils.processTemplateIntoString(pageTemplate, model);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(CmsCode.CMS_PAGE_FREEMAEKRERROR, e.getMessage());
        }
        if (StringUtils.isEmpty(html)){
            throw new CustomException(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return html;
    }

    /**
     *  获取页面模板
     * @param cmsPage
     * @return
     */
    private String getTempalate(CmsPage cmsPage){
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isEmpty(templateId)){
            throw new CustomException(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if (!optional.isPresent() && StringUtils.isEmpty(optional.get().getTemplateFileId())){
            throw new CustomException(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        CmsTemplate cmsTemplate = optional.get();
        // 获取模板文件内容
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(cmsTemplate.getTemplateFileId())));
        // 打开下载对象流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建GridFsResource
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);

        String content = null;
        try {
            content = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(CmsCode.CMS_GETTEMPALE_IOEXCEPTION, e.getMessage());
        }
        if (StringUtils.isEmpty(content)){
            throw new CustomException(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        return content;
    }

    /**
     *  获取模型数据
     * @param cmsPage
     * @return
     */
    private Map getModel(CmsPage cmsPage){
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)){
            throw new CustomException(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        // 远程http请求接口获得模型数据  ?? http请求是否要加密
        ResponseEntity<Map> entity = restTemplate.getForEntity(dataUrl, Map.class);
        if (entity==null || CollectionUtils.isEmpty(entity.getBody())){
            throw new CustomException(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        Object resultData = entity.getBody().get("resultData");
        String jsonString = JSON.toJSONString(resultData);
        Map data = (Map)JSON.parse(jsonString);
        return data;
    }

    public void setCmsPageRepository(CmsPageRepository cmsPageRepository) {
        this.cmsPageRepository = cmsPageRepository;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setCmsTemplateRepository(CmsTemplateRepository cmsTemplateRepository) {
        this.cmsTemplateRepository = cmsTemplateRepository;
    }

    public void setGridFsTemplate(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    public void setGridFSBucket(GridFSBucket gridFSBucket) {
        this.gridFSBucket = gridFSBucket;
    }
}
