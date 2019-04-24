package com.roncoo.eshop.config;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.SocketException;


@Configuration
public class FtpConfig {
    private static Logger LOG= LoggerFactory.getLogger(FtpConfig.class);
    @Value("${ftp.port}")
    private int port;
    @Value("${ftp.host}")
    private String hostName;
    @Value("${ftp.loginName}")
    private String loginName;
    @Value("${ftp.loginPwd}")
    private String loginPwd;
    @Value("${ftp.basePath}")
    private String basePath;

//    @Bean
//    public FTPClient ftpClientFactory(){
//        FTPClient ftpClient = new FTPClient();
//        try {
//            ftpClient.connect(hostName, port);
//            ftpClient.login(loginName, loginPwd);
//            ftpClient.changeWorkingDirectory(basePath);
//            int reply=ftpClient.getReplyCode();
//            if(!FTPReply.isPositiveCompletion(reply)){
//                ftpClient.disconnect();
//            }
//            ftpClient.logout();
//        } catch (SocketException e) {
//            LOG.error("socket错误：",e);
//        }catch (IOException e) {
//            LOG.error("io错误：",e);
//        }finally {
//            if (ftpClient.isConnected()){
//                try{
//                    ftpClient.disconnect();
//                }catch (IOException ioe){
//                }
//            }
//        }
//        return ftpClient;
//    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
