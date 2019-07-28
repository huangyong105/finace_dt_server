package com.roncoo.eshop.manager;

import cn.com.taiji.result.MyResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * create by yong.huang on 14:59 2018/7/24
 */
@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    public static final String SYSTEM_SMS_CODE = "magic_mirror_system_sms_code_%s";

    public static final String SYSTEM_SMS_CODE_COUNT = "magic_mirror_system_sms_time_count_%s";

    public static final Integer SYSTEM_SMS_CODE_TIME_LIMIT = 5;

    private static final String BAIDU_ACCESS_TOKEN = "magic_console_speech_token";

    Logger LOGGER = LoggerFactory.getLogger(CommonServiceImpl.class);



    @Autowired
    private OssService ossService;



    /**
     * 文件上传
     * @param request
     * @return
     */
    @Override
    public MyResult uploadFile(HttpServletRequest request, MultipartFile multipartFile, boolean mark) {
        Map filePathMap = new HashMap<>();
        try {
            request.setCharacterEncoding("UTF-8");
            if(null == multipartFile){
                LOGGER.error("上传文件为空");
                return MyResult.ofError(-1,"文件上传失败");
            }
            String url = ossService.uploadWithBytes(multipartFile.getBytes(), multipartFile.getOriginalFilename());
            filePathMap.put(multipartFile.getName(),url);
            return MyResult.ofSuccess(filePathMap);
        } catch (Exception e) {
            log.error("图片上传失败", e);
        }
        return MyResult.ofError(-1,"文件上传失败");
    }

}
