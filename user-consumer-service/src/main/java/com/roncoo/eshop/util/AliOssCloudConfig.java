package com.roncoo.eshop.util;
import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.swing.text.html.FormSubmitEvent;

@Configuration
@ConfigurationProperties(prefix = "aliyun")
public class AliOssCloudConfig {

    private String accessKeyId;

    private String accessKeySecret;


    private String ossEndpoint;

    private Long expireTime;


    private String region;

    private String roleArn;

    private String policy;

    private String version;

    private String sessionName;

    private String bucketName;

    private  String imageUrl ;

    /**
     * @return
     * @Description: 获取oss
     * @author: ivoter
     */
    @Bean
    public OSSClient createOSSClient() {
        OSSClient client = new OSSClient(ossEndpoint, accessKeyId, accessKeySecret);
        return client;
    }

    /**
     * @return
     * @Description: 获取acs
     * @author: ivoter
     */
    @Bean
    public DefaultAcsClient createAcsClient() {
        IClientProfile profile = DefaultProfile.getProfile(region, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        return client;
    }

    @Bean
    public AssumeRoleRequest createAssumeRoleRequest() {
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setVersion(version);
        request.setMethod(MethodType.POST);
        request.setProtocol(ProtocolType.HTTPS);
        request.setDurationSeconds(expireTime);
        // 发起请求，并得到response
        request.setRoleArn(roleArn);
        request.setRoleSessionName(sessionName);
        request.setPolicy(policy);

        return request;
    }


    public String getBucketName() {
        return bucketName;
    }

    public String getRegion() {
        return region;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getOssEndpoint() {
        return ossEndpoint;
    }

    public void setOssEndpoint(String ossEndpoint) {
        this.ossEndpoint = ossEndpoint;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRoleArn() {
        return roleArn;
    }

    public void setRoleArn(String roleArn) {
        this.roleArn = roleArn;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
