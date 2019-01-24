package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.domain.cms.template.CmsExecuteTemplate;
import com.xuecheng.framework.domain.cms.template.CmsHandleCallback;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.pagination.PaginationVo;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Created by lwenf on 2019-01-03.
 */
@Service
public class CmsPageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CmsPageService.class);

    private static final String PAGE_ID = "pageId";

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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     *  页面分页查询
     * @param page 当前页，默认从第1页开始
     * @param size 每页记录数
     * @param queryPageRequest
     * @return
     */
    public CmsResult<PaginationVo<CmsPage>> findList(int page, int size,final QueryPageRequest queryPageRequest){
        return CmsExecuteTemplate.execute(new CmsHandleCallback<CmsResult<PaginationVo<CmsPage>>>() {
            @Override
            public void checkParams() {
                if (page > 0 ){
                    queryPageRequest.setCurrentPage(page);
                }
                if (size > 0){
                    queryPageRequest.setPageSize(size);
                }
                if (queryPageRequest == null){
                    queryPageRequest.setCurrentPage(1);
                    queryPageRequest.setPageSize(10);
                }
            }
            @Override
            public CmsResult<PaginationVo<CmsPage>> doProcess() {

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
                Pageable pageable = PageRequest.of(queryPageRequest.getCurrentPage()-1,queryPageRequest.getPageSize());
                Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);

                if (all == null){
                    return CmsResult.newFailResult("查询出错！");
                }
                // 构造结果
                PaginationVo<CmsPage> pagePaginationVo = new PaginationVo<>();
                pagePaginationVo.setElements(all.getContent());
                pagePaginationVo.setCurrentPage(queryPageRequest.getCurrentPage());
                pagePaginationVo.setPageSize(queryPageRequest.getPageSize());
                pagePaginationVo.setTotalRecords(all.getTotalElements());

                return CmsResult.newSuccessResult(pagePaginationVo);
            }
        });

    }

    /**
     *  添加页面
     * @param cmsPage
     * @return
     */
    public CmsResult<CmsPage> add(CmsPage cmsPage){
        return CmsExecuteTemplate.execute(new CmsHandleCallback<CmsResult<CmsPage>>() {
            @Override
            public void checkParams() {
                // 1. 参数校验
                if (cmsPage == null){
                    throw new CustomException(CommonCode.INVALIDPARAM);
                }
            }
            @Override
            public CmsResult<CmsPage> doProcess() {
                CmsPage resultCmsPage = cmsPageRepository.findByPageNameAndPageWebPathAndSiteId(cmsPage.getPageName(), cmsPage.getPageWebPath(), cmsPage.getSiteId());
                if (resultCmsPage != null){
                    throw new CustomException(CmsCode.CMS_ADDPAGE_EXISTSNAME);
                }
                cmsPage.setPageId(null);
                CmsPage save = cmsPageRepository.save(cmsPage);
                return CmsResult.newSuccessResult(cmsPage);
            }
        });
    }

    /**
     *  根据id查询页面
     * @return
     */
    public CmsResult<CmsPage> findById(String id){
        return CmsExecuteTemplate.execute(new CmsHandleCallback<CmsResult<CmsPage>>(){
            @Override
            public void checkParams() {
                // 1. 查询校验
                if(StringUtils.isEmpty(id)){
                    throw new CustomException(CommonCode.INVALIDPARAM);
                }
            }

            @Override
            public CmsResult<CmsPage> doProcess() {

                Optional<CmsPage> optional = cmsPageRepository.findById(id);
                if (!optional.isPresent()){
                    return CmsResult.newFailResult("查询结果为空！");
                }
                return CmsResult.newSuccessResult(optional.get());
            }
        });
    }

    /**
     *  修改页面
     * @param id
     * @param cmsPage
     * @return
     */
    public CmsResult<CmsPage> edit(String id, CmsPage cmsPage){
        return CmsExecuteTemplate.execute(new CmsHandleCallback<CmsResult<CmsPage>>() {
            @Override
            public void checkParams() {
                // 1. 参数校验
                if(StringUtils.isEmpty(id)||cmsPage == null){
                    throw new CustomException(CommonCode.INVALIDPARAM);
                }
            }

            @Override
            public CmsResult<CmsPage> doProcess() {

                // 2. 校验被修改数据是否存在
                if(!StringUtils.isEmpty(cmsPage.getPageId())&&!StringUtils.equals(id, cmsPage.getPageId())){
                    throw new CustomException(CommonCode.INVALIDPARAM, "页面主键有误！");
                }
                CmsResult<CmsPage> result = findById(id);
                if (!result.isSuccess()||result.getResultData()==null){
                    throw new CustomException(CmsCode.CMS_PAGE_NOTEXISTS);
                }

                /**
                 * 更新操作
                 */
                CmsPage one = result.getResultData();
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
                    return CmsResult.newFailResult("更新页面失败！");
                }
                return CmsResult.newSuccessResult(save);
            }
        });
    }

    /**
     *  根据id删除数据
     * @param id
     * @return
     */
    public CmsResult del(String id){
        return CmsExecuteTemplate.execute(new CmsHandleCallback<CmsResult<Void>>() {
            @Override
            public void checkParams() {
                // 1. 参数校验
                if (StringUtils.isEmpty(id)){
                    throw new CustomException(CommonCode.INVALIDPARAM);
                }
            }

            @Override
            public CmsResult<Void> doProcess() {
                CmsResult<CmsPage> result = findById(id);
                if (!result.isSuccess()||result.getResultData()==null){
                    throw new CustomException(CmsCode.CMS_PAGE_NOTEXISTS);
                }
                cmsPageRepository.deleteById(id);
                return CmsResult.newSuccessResult();
            }
        });

    }

    /**
     *  页面静态化
     * @param pageId
     * @return
     */
    public CmsResult<String> getPageHtml(String pageId){
        return CmsExecuteTemplate.execute(new CmsHandleCallback<CmsResult<String>>() {
            @Override
            public void checkParams() {
                // 校验入参
                if (StringUtils.isEmpty(pageId)){
                    throw new CustomException(CommonCode.INVALIDPARAM, "入参为空！");
                }
            }

            @Override
            public CmsResult<String> doProcess() {
                CmsResult<CmsPage> pageResult = findById(pageId);
                CmsPage cmsPage = pageResult.getResultData();
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
        });
    }

    /**
     *  根据pageId发布页面到
     * @param pageId
     * @return
     */
    @Transactional
    public CmsResult<Void> postPage(String pageId){
        return CmsExecuteTemplate.execute(new CmsHandleCallback<CmsResult<Void>>() {
            @Override
            public void checkParams() {
                // 校验入参
                if (StringUtils.isEmpty(pageId)){
                    throw new CustomException(CommonCode.INVALIDPARAM, "入参为空！");
                }
            }

            @Override
            public CmsResult<Void> doProcess() {
                Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
                if (!optional.isPresent() || optional.get()==null || StringUtils.isEmpty(optional.get().getSiteId())){
                    throw new CustomException(CmsCode.CMS_PAGE_NOTEXISTS,"页面不存在或没有找到搜索站点！");
                }
                CmsPage cmsPage = optional.get();
                if (StringUtils.isEmpty(cmsPage.getPageName())){
                    cmsPage.setPageName(pageId+".html");
                }
                // 执行页面静态化
                CmsResult<String> pageHtmlResult = getPageHtml(pageId);
                if (!pageHtmlResult.isSuccess()||pageHtmlResult.getResultData()==null){
                    throw new CustomException(CmsCode.CMS_PAGE_FREEMAEKRERROR);
                }
                // 将静态化文件页面保存到GridFs
                savaPageHtmlToGridFs(cmsPage, pageHtmlResult.getResultData());
                // 向消息中间件发送消息
                sendPostPage(cmsPage);

                return CmsResult.newSuccessResult();
            }
        });
    }

    /**
     * 向RabbitMq发送消息
     * @param cmsPage
     */
    private void sendPostPage(CmsPage cmsPage){

        try {
            // 构造消息格式
            Map<String, String> msgMap = new HashMap<>();
            msgMap.put(PAGE_ID,cmsPage.getPageId());
            String msgJson = JSON.toJSONString(msgMap);

            // 发送消息
            rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE,cmsPage.getSiteId(), msgJson);

            LOGGER.info("RabbitMq sending messages success,pageId = {}, msg = {}", cmsPage.getPageId(), msgJson);
        }catch (Exception e){
            LOGGER.error("RabbitMq sending messages fail, pageId = {}", cmsPage.getPageId());
            throw new CustomException(CmsCode.CMS_PAGE_RABBITMQ_SENGMSSGFAIL);
        }
    }

    /**
     *  将页面静态化文件存到GridFs
     * @param cmsPage
     * @param pageHtml
     * @return
     */
    private CmsPage savaPageHtmlToGridFs(CmsPage cmsPage, String pageHtml){

        ObjectId objectId = null;
        try {
            // 将pageHtml转化为输入流
            InputStream inputStream = IOUtils.toInputStream(pageHtml, "utf-8");
            objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(CmsCode.CMS_GENERATEHTML_SAVEHTMLERROR);
        }
        cmsPage.setHtmlFileId(objectId.toHexString());
        CmsPage save = cmsPageRepository.save(cmsPage);
        return save;
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

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
