package com.roncoo.eshop.manager;


import com.roncoo.eshop.util.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author dongyuan
 * @date 2019-03-24 22:45
 **/
@Service
public class InvestorManager {
    @Autowired
    FTPClient ftpClient;
    @Autowired
    FtpUtil ftpUtil;
    public String uploadIdPhoto(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (file.isEmpty()){
            return null;
        }
        String newName = ftpUtil.getFileSuffName(fileName);
        boolean upload = ftpUtil.uploadFile(file.getInputStream(), newName, ftpClient);
        if (upload){
            return newName;
        }
        return null;
    }

    public void downloadIdPhoto(String fileName,OutputStream ops){
        ftpUtil.downloadFile(fileName,ftpClient,ops);
    }

}
