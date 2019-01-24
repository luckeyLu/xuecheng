package com.xuecheng.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.domain.cms.template.CmsExecuteTemplate;
import com.xuecheng.framework.domain.cms.template.CmsHandleCallback;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * Created by lwenf on 2019-01-21.
 */
@Service
public class PageService {
    private static  final Logger LOGGER = LoggerFactory.getLogger(PageService.class);

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    // 将页面html保存到服务器页面物理路径下
    public CmsResult<Void> savePageToServerPath(String pageId){
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

                Optional<CmsPage> pageResult = cmsPageRepository.findById(pageId);
                if (!pageResult.isPresent()||pageResult.get()==null){
                    throw new CustomException(CmsCode.CMS_PAGE_NOTEXISTS);
                }
                // 页面详情
                CmsPage cmsPage = pageResult.get();
                if (StringUtils.isEmpty(cmsPage.getHtmlFileId())){
                    throw new CustomException(CommonCode.INVALIDPARAM, "页面HtmlFileId为空！");
                }
                // 根据页面fileId查询到gridfs上查询页面的html文件
                InputStream inputStream = queryPageHtmlByFileId(cmsPage.getHtmlFileId());
                if (inputStream == null){
                    LOGGER.error("根据页面HtmlFIleId查询页面Html文件出错，HtmlFileId：{}", cmsPage.getHtmlFileId());
                }
                // 页面所属站点
                CmsSite cmsSite = queryCmsSitById(cmsPage.getSiteId());
                if (cmsSite == null){
                    throw new CustomException(CmsCode.CMS_PAGE_SITE_NOTEXISTS);
                }
                // 页面物理路径 = 站点物理路径+页面物理路径+页面名称
                String pageHtml = cmsSite.getSitePhysicalPath()+cmsPage.getPagePhysicalPath()+cmsPage.getPageName();

                // 将页面html文件写入服务器物理地址
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream  = new FileOutputStream(new File(pageHtml));
                    IOUtils.copy(inputStream, fileOutputStream);
                    return CmsResult.newSuccessResult();
                }catch (IOException e) {
                    e.printStackTrace();
                    LOGGER.error("将页面文件写入服务器物理路径失败！pageId:{},", pageId);
                    return CmsResult.newFailResult();
                }finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    /**
     *  根据页面htmlFileId 在GridFs查询页面html文件
     * @return
     */
    private InputStream queryPageHtmlByFileId(String htmlFileId){
        // 获取文件对象
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));
        // 打开下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        // 定义GridFsResource
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);

        try {
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("通过GridFs下载文件失！id：{}",htmlFileId);
        }
        return null;
    }

    /**
     * 根据找点id查询站点详情
     * @param id
     * @return
     */
    private CmsSite queryCmsSitById(String id){
        Optional<CmsSite> optional = cmsSiteRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }


    public void setCmsPageRepository(CmsPageRepository cmsPageRepository) {
        this.cmsPageRepository = cmsPageRepository;
    }

    public void setCmsSiteRepository(CmsSiteRepository cmsSiteRepository) {
        this.cmsSiteRepository = cmsSiteRepository;
    }

    public void setGridFSBucket(GridFSBucket gridFSBucket) {
        this.gridFSBucket = gridFSBucket;
    }

    public void setGridFsTemplate(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }
}
