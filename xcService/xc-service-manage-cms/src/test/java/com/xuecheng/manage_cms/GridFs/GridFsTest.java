package com.xuecheng.manage_cms.GridFs;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by lwenf on 2019-01-19.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFsTest {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Test
    // GridFs 存文件
    public void testGridFs() throws FileNotFoundException {
        File file = new File("F:/xuecheng/index_banner.ftl");
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectId objectId = gridFsTemplate.store(fileInputStream, "index_banner.ftl");
        System.out.println(objectId);
    }

    @Test
    // GridFs 读取文件
    public void gridFsGetFile() throws IOException {

        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("5c432778e02255532c64b41f")));

        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());

        GridFsResource gridFsResours = new GridFsResource(gridFSFile,gridFSDownloadStream);

        String s = IOUtils.toString(gridFsResours.getInputStream(), "utf-8");

        System.out.println(s);

    }

}
