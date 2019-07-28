package com.roncoo.eshop.manager;

import java.io.InputStream;

/**
 * 描述:
 * oss图片服务处理
 *
 * @outhor huangbo
 * @create 2017-11-14 下午9:17
 */
public interface OssService {
    String uploadWithBytes(byte[] bytes, String fileName, Boolean withTrueName);

    /**
     * 通过字节数组上传文件
     * @param bytes
     * @param fileName
     * @return
     */
    String uploadWithBytes(byte[] bytes, String fileName);

    /**
     * 通过文件流上传文件
     * @param inputstream
     * @param fileName
     * @return
     */
    String uploadWithStream(InputStream inputstream, String fileName);
}
