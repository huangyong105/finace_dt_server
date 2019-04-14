package com.roncoo.eshop.web.controller;


import cn.com.taiji.user.User;
import com.roncoo.eshop.DTO.InvestmentDetailsDTO;
import com.roncoo.eshop.DTO.InvestorManagementDTO;
import com.roncoo.eshop.client.UserClient;
import com.roncoo.eshop.manager.InvestorManager;
import com.roncoo.eshop.model.InvestmentDetailsDO;
import com.roncoo.eshop.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户相关接口
 *
 * @author dongyuan
 * @date 2019-03-23 15:10
 **/
@RestController
@RequestMapping("/investor")
public class InvestorManagementController {
    @Autowired
    private InvestorManager investorManager;
    @Autowired
    private UserClient userClient;
    @Value("${image_path_url}")
    String imagePathUrl;
    /**
     * 获取用户基础信息
     * @return
     */
    @RequestMapping("/information")
    public Result getInformation(@RequestHeader("token")String token){
        cn.com.taiji.data.Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = userResult.getData();
        InvestorManagementDTO investorManagementDTO = new InvestorManagementDTO();
        investorManagementDTO.setId(user.getId());
        investorManagementDTO.setEmail(user.getEmail());
        investorManagementDTO.setTelNumber(user.getAccount());
        investorManagementDTO.setState(user.getState());
        investorManagementDTO.setIdCardNumber(user.getIdCardNumber());
        investorManagementDTO.setBankCardNumber(user.getBankCardNumber());
        investorManagementDTO.setIdCardPngDown(user.getIdCardPngDown());
        investorManagementDTO.setIdCardPngUp(user.getIdCardPngUp());
        return Result.ofSuccess(investorManagementDTO);
    }

    /**
     * 获取用户投资详情
     * @return
     */
    @RequestMapping("/myInvestment")
    public Result getMyInvestment(@RequestHeader("token")String token){
        cn.com.taiji.data.Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long id = userResult.getData().getId();
        List<InvestmentDetailsDTO> dtos = investorManager.getInvestmentDetailsDOSByuserId(id);
        return Result.ofSuccess(dtos);
    }

    /**
     * 获取实名认证状态
     * @return
     */
    @RequestMapping("/realNameState")
    public Result getRealNameState(@RequestHeader("token")String token){
        cn.com.taiji.data.Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = userResult.getData();
        if (user.getIdCardNumber()!=null||user.getIdCardPngDown()!=null||user.getIdCardPngUp()!=null){
            return Result.ofSuccess(1);
        }
        return Result.ofSuccess(0);
    }

    /**
     * 获取绑卡状态
     * @return
     */
    @RequestMapping("/bindCodeState")
    public Result getBindCodeState(@RequestHeader("token")String token){
        cn.com.taiji.data.Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = userResult.getData();
        if (user.getBankCardNumber()!=null){
            return Result.ofSuccess(1);
        }
        return Result.ofSuccess(0);
    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping("/uploadIdPhoto")
    public Result uploadIdPhoto(@RequestParam("file") MultipartFile file){
        String fileName = null;
        try {
            fileName = investorManager.uploadIdPhoto(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  Result.ofSuccess(imagePathUrl+fileName);
    }

    /**
     * 下载图片
     * @param fileName
     * @param response
     */
    @RequestMapping(value = "/downloadIdPhoto",method = RequestMethod.GET)
    public void downLoadIdPhoto(@RequestParam String fileName, HttpServletResponse response){
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "max-age=604800");
        try {
            investorManager.downloadIdPhoto(fileName,response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户认证
     * @param investorManagementDTO
     * @param token
     * @return
     */
    @RequestMapping(value = "/realNameCertification")
    public Result realNameCertification(@RequestBody InvestorManagementDTO investorManagementDTO,@RequestHeader("token")String token){
        cn.com.taiji.data.Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!userResult.isSuccess()||userResult.getData()==null)
        {
            return Result.ofError(5001,"无本用户");
        }
        if (investorManagementDTO.getIdCardNumber()==null||investorManagementDTO.getIdCardPngDown()==null||investorManagementDTO.getIdCardPngUp()==null){
            return Result.ofError(5002,"入参不完整!");
        }
        Long id = userResult.getData().getId();
        User user = new User();
        user.setId(id);
        user.setIdCardNumber(investorManagementDTO.getIdCardNumber());
        user.setIdCardPngUp(investorManagementDTO.getIdCardPngUp());
        user.setIdCardPngDown(investorManagementDTO.getIdCardPngDown());
        cn.com.taiji.data.Result result = userClient.realNameCertification(user);
        if (result.isSuccess()){
            return Result.ofSuccess("认证成功");
        }
        return Result.ofError(5000,"认证失败");
    }
}
