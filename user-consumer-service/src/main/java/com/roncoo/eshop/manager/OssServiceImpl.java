package com.roncoo.eshop.manager;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.roncoo.eshop.util.AliOssCloudConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 描述:
 * OSS Img服务处理
 *
 * @outhor huangbo
 * @create 2017-11-14 下午9:18
 */
@Service
public class OssServiceImpl implements OssService {
    private static final Logger LOG = LoggerFactory.getLogger(OssServiceImpl.class);


    private Random random = ThreadLocalRandom.current();


    @Autowired
    private AliOssCloudConfig aliOssCloudConfig;
    /**
     * 文件存储目录
     */
    private String filedir = "finace/";

    @Override
    public String uploadWithBytes(byte[] bytes, String fileName, Boolean withTrueName) {
        if(null == bytes) {
            return "";
        }
        InputStream input = new ByteArrayInputStream(bytes);
        OSSClient client = getDefaultOSSClient();
        String url = "";
        try {
            String substring = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            String name;
            if(withTrueName){
                name = fileName;
            }else {
                name = random.nextInt(10000) + System.currentTimeMillis() + substring;
            }
            String etag = uploadFile2OSS(input,name);
            //上传成功
            if(StringUtils.isNotBlank(etag)){
                url = getFileUrl(fileName);
            }else{//上传失败
                return url;
            }
        } catch (Exception e) {
            return url;
        } finally {
            client.shutdown();
        }
        return url;
    }
    @Override
    public String uploadWithBytes(byte[] bytes, String fileName) {
        if(null == bytes) {
            return "";
        }
        InputStream input = new ByteArrayInputStream(bytes);
        OSSClient client = getDefaultOSSClient();
        String imgUrl = "";
        try {
            String substring = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
            String etag = uploadFile2OSS(input,name);
            //上传成功
            if(StringUtils.isNotBlank(etag)){
                imgUrl = getFileUrl(name);
            }else{//上传失败
                return imgUrl;
            }
        } catch (Exception e) {
            return imgUrl;
        } finally {
            client.shutdown();
        }
        return imgUrl;
    }


    @Override
    public String uploadWithStream(InputStream input, String fileName) {
        OSSClient client = getDefaultOSSClient();
        String url = "";
        try {
            if(input != null){
                String etag = uploadFile2OSS(input,fileName);
                //上传成功
                if(StringUtils.isNotBlank(etag)){
                    url = getFileUrl(fileName);
                }else{//上传失败
                    return url;
                }
            }
            client.shutdown();
        } catch (Exception e) {
            return url;
        }
        return url;
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getFileUrl(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(this.filedir + split[split.length - 1]);
        }
        return "";
    }
    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = getDefaultOSSClient().generatePresignedUrl(aliOssCloudConfig.getBucketName(), key, expiration);
        if (url != null) {
            return url.getProtocol()+"://"+url.getHost()+url.getPath();
        }
        return "";
    }

    /**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2OSS(InputStream instream, String fileName){
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf(".")+1)));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);

            //上传文件
            PutObjectResult putResult = getDefaultOSSClient().putObject(aliOssCloudConfig.getBucketName(), filedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            LOG.info("OssFacadeServiceImpl#uploadWithBytes#uploadFile2OSS,exception:{}",e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                LOG.info("OssFacadeServiceImpl#uploadWithBytes#uploadFile2OSS,exception:{}",e);
            }
        }
        return ret;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    private String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase("jpeg") ||
                FilenameExtension.equalsIgnoreCase("jpg") ||
                FilenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase("pptx") ||
                FilenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase("docx") ||
                FilenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equalsIgnoreCase("csv")) {
            return "text/csv";
        }if (FilenameExtension.equalsIgnoreCase("xlsx")) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        return "image/jpeg";
    }


    /**
     * 获取oss默认客户端
     * @return
     */
    private OSSClient getDefaultOSSClient(){
        return  new OSSClient(aliOssCloudConfig.getOssEndpoint(), aliOssCloudConfig.getAccessKeyId(), aliOssCloudConfig.getAccessKeySecret());
    }


}
