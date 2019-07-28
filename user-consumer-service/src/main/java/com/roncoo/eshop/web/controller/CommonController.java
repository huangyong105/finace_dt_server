package com.roncoo.eshop.web.controller;

import cn.com.taiji.result.MyResult;
import com.roncoo.eshop.manager.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * create by yong.huang on 14:52 2018/7/24
 */
@Slf4j
@RestController
@RequestMapping("/v1/common")
public class CommonController {

    @Autowired
    CommonService commonService ;







    @RequestMapping(value = "/uploadFile", method = {RequestMethod.POST})
    public MyResult uploadFile(HttpServletRequest request, @RequestAttribute(value = "file") MultipartFile file) {

        return commonService.uploadFile(request,file,false);
    }


}
