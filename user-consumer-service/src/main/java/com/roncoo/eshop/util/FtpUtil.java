package com.roncoo.eshop.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 文件上传工具
 *
 * @author dongyuan
 * @date 2019-03-25 10:46
 **/
@Service
public class FtpUtil {
    private static Logger logger= LoggerFactory.getLogger(FtpUtil.class);

    /**
     * 断开ftp连接
     */
    public void closeConnection(FTPClient ftpClient){
        if(ftpClient.isConnected()){
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                logger.error("io错误：",e);
            }
        }
    }

    /**
     * 修改文件名为uuid开头避免文件名重复
     * @param fileName 获取上传的文件名
     * @return 新的文件名
     */
    public String getFileSuffName(String fileName){
        File file=new File(fileName);
        String oldFileName=file.getName();
        String suffixFileName=oldFileName.substring(oldFileName.lastIndexOf(".")+1);
        String uuid= UUID.randomUUID().toString().replace("-","");
        String newFileName=uuid+"."+suffixFileName;
        return newFileName;
    }

    /**
     * 上传文件
     * @param is 文件流
     * @param fileName 文件名称
     * @return
     */
    public boolean uploadFile(InputStream is, String fileName, FTPClient ftpClient){
        boolean flag=false;
        try {
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftpClient.setDataTimeout(20000);
            ftpClient.setConnectTimeout(20000);
            if(!ftpClient.storeFile(fileName, is)){
                return flag;
            }
            is.close();
            flag=true;
        } catch (IOException e) {
            logger.error("io错误：",e);
        }
        return flag;
    }

    /**
     * Description: 从FTP服务器下载文件
     * @param fileName 要下载的文件名
     * @return
     */
    public boolean downloadFile(String fileName,FTPClient ftpClient,OutputStream ops) {
        boolean result = false;
        try {
            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile ff : files) {
                String f = new String(ff.getName().getBytes("ISO-8859-1"), "GBK");
                if (f.equals(fileName)) {
//                    File localFile = new File(localPath + "/" + ff.getName());
//                    OutputStream is = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(ff.getName(), ops);
                    ops.close();
                }
            }
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
