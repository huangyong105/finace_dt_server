package com.roncoo.eshop.manager;

import cn.com.taiji.result.MyResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * create by yong.huang on 14:59 2018/7/24
 */
public interface CommonService {


    MyResult uploadFile(HttpServletRequest request, MultipartFile imgFiles, boolean mark);


}
