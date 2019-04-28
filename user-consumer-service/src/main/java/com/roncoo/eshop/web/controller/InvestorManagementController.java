package com.roncoo.eshop.web.controller;


import cn.com.taiji.DTO.InvestmentDetailsDTO;
import cn.com.taiji.DTO.InvestorManagementDTO;
import cn.com.taiji.data.Result;
import cn.com.taiji.data.Token;
import cn.com.taiji.data.User;
import cn.com.taiji.data.UserEntity;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.roncoo.eshop.client.UserClient;
import com.roncoo.eshop.config.AliPayConfig;
import com.roncoo.eshop.manager.InvestorManager;


import cn.com.taiji.result.MyResult;
import com.roncoo.eshop.manager.PayOrderManager;
import com.roncoo.eshop.mapper.PayOrderMapper;
import com.roncoo.eshop.model.PayOrderDO;
import com.roncoo.eshop.util.OrderCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
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
    private static Logger LOG= LoggerFactory.getLogger(InvestorManagementController.class);
    @Autowired
    private InvestorManager investorManager;
    @Autowired
    private PayOrderManager payOrderManager;
    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private UserClient userClient;
    @Value("${image_path_url}")
    String imagePathUrl;
    @Value("${notifyUrl}")
    String notifyUrl;
    /**
     * 获取用户基础信息
     * @return
     */
    @RequestMapping("/information")
    public MyResult getInformation(@RequestHeader("token")String token){
        Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!userResult.isSuccess()||userResult.getData()==null)
        {
            return MyResult.ofError(4000,"未登陆");
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
        return MyResult.ofSuccess(investorManagementDTO);
    }

    /**
     * 获取用户投资详情
     * @return
     */
    @RequestMapping("/myInvestment")
    public MyResult getMyInvestment(@RequestHeader("token")String token){
        Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!userResult.isSuccess()||userResult.getData()==null)
        {
            return MyResult.ofError(4000,"未登陆");
        }
        Long id = userResult.getData().getId();
        List<InvestmentDetailsDTO> dtos = investorManager.getInvestmentDetailsDOSByuserId(id);
        return MyResult.ofSuccess(dtos);
    }

    @RequestMapping("/payProject")
    public MyResult payProject(@RequestHeader("token")String token,@RequestBody InvestmentDetailsDTO investmentDetailsDTO){
        String infoStr = null;
        Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!userResult.isSuccess()||userResult.getData()==null)
        {
            return MyResult.ofError(4000,"未登陆");
        }
        //生成唯一支付订单id
        Long userId = userResult.getData().getId();
        String orderCode = OrderCodeUtil.getOrderCode(userId);
        PayOrderDO payOrderDO = payOrderManager.savePayOrder(orderCode, investmentDetailsDTO, userId);
        if (payOrderDO == null){
            return MyResult.ofError(4000,"预下单失败");
        }
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        model.setBody(payOrderDO.getPayDescribe());
        model.setSubject(payOrderDO.getPayTitle());
        model.setOutTradeNo(payOrderDO.getOrderId());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(payOrderDO.getInputMargin().toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        try {
        //这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        infoStr = response.getBody();
        LOG.info(response.getBody());
    } catch (AlipayApiException e) {
        e.printStackTrace();
        }
        return MyResult.ofSuccess(infoStr);

    }

    /**
     * 用户投资项目
     * @param token
     * @param investmentDetailsDTO
     * @return
     */
    @RequestMapping("/addMyInvestment")
    public MyResult addMyInvestment(@RequestHeader("token")String token,@RequestBody InvestmentDetailsDTO investmentDetailsDTO){
        return MyResult.ofSuccess();
    }

    /**
     * 获取用户投资详情
     * todo 提供内部调用
     * @return
     */
    @RequestMapping("/getMyInvestment")
    public MyResult getMyInvestment(Long id){
        List<InvestmentDetailsDTO> dtos = investorManager.getInvestmentDetailsDOSByuserId(id);
        return MyResult.ofSuccess(dtos);
    }


    /**
     * 获取实名认证状态
     * @return
     */
    @RequestMapping("/realNameState")
    public MyResult getRealNameState(@RequestHeader("token")String token){
        Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!userResult.isSuccess()||userResult.getData()==null)
        {
            return MyResult.ofError(4000,"未登陆");
        }
        User user = userResult.getData();
        if (user.getIdCardNumber()!=null||user.getIdCardPngDown()!=null||user.getIdCardPngUp()!=null){
            return MyResult.ofSuccess(1);
        }
        return MyResult.ofSuccess(0);
    }

    /**
     * 获取绑卡状态
     * @return
     */
    @RequestMapping("/bindCodeState")
    public MyResult getBindCodeState(@RequestHeader("token")String token){
        Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!userResult.isSuccess()||userResult.getData()==null)
        {
            return MyResult.ofError(4000,"未登陆");
        }
        User user = userResult.getData();
        if (user.getBankCardNumber()!=null){
            return MyResult.ofSuccess(1);
        }
        return MyResult.ofSuccess(0);
    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping("/uploadIdPhoto")
    public MyResult uploadIdPhoto(@RequestParam("file") MultipartFile file,@RequestHeader("token")String token){
        Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!userResult.isSuccess()||userResult.getData()==null)
        {
            return MyResult.ofError(4000,"未登陆");
        }
        String fileName = null;
        try {
            fileName = investorManager.uploadIdPhoto(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  MyResult.ofSuccess(imagePathUrl+fileName);
    }

    /**
     * 下载图片
     * @param fileName
     * @param response
     */
    @RequestMapping(value = "/downloadIdPhoto",method = RequestMethod.GET)
    public MyResult downLoadIdPhoto(@RequestParam String fileName, HttpServletResponse response,@RequestHeader("token")String token){
        Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!userResult.isSuccess()||userResult.getData()==null)
        {
            return MyResult.ofError(4000,"未登陆");
        }
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "max-age=604800");
        try {
            investorManager.downloadIdPhoto(fileName,response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return MyResult.ofSuccess("下载成功");
    }

    /**
     * 用户认证
     * @param investorManagementDTO
     * @param token
     * @return
     */
    @RequestMapping(value = "/realNameCertification")
    public MyResult realNameCertification(@RequestBody InvestorManagementDTO investorManagementDTO,@RequestHeader("token")String token){
        Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!userResult.isSuccess()||userResult.getData()==null)
        {
            return MyResult.ofError(4000,"未登陆");
        }
        if (investorManagementDTO.getIdCardNumber()==null||investorManagementDTO.getIdCardPngDown()==null||investorManagementDTO.getIdCardPngUp()==null){
            return MyResult.ofError(5002,"入参不完整!");
        }
        Long id = userResult.getData().getId();
        User user = new User();
        user.setId(id);
        user.setIdCardNumber(investorManagementDTO.getIdCardNumber());
        user.setIdCardPngUp(investorManagementDTO.getIdCardPngUp());
        user.setIdCardPngDown(investorManagementDTO.getIdCardPngDown());
        Result result = userClient.realNameCertification(user);
        if (result.isSuccess()){
            return MyResult.ofSuccess("认证成功");
        }
        return MyResult.ofError(5000,"认证失败");
    }

    /**
     * 绑定银行卡
     * @param investorManagementDTO
     * @param token
     * @return
     */
    @RequestMapping(value = "/bindCard")
    public MyResult bindCard(@RequestBody InvestorManagementDTO investorManagementDTO,@RequestHeader("token")String token){
        Result<User> userResult = null;
        try {
            userResult = userClient.getUserInfo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!userResult.isSuccess()||userResult.getData()==null)
        {
            return MyResult.ofError(4000,"未登陆");
        }
        if (investorManagementDTO.getBankCardNumber()==null){
            return MyResult.ofError(5002,"入参不完整!");
        }
        Long id = userResult.getData().getId();
        User user = new User();
        user.setId(id);
        user.setBankCardNumber(investorManagementDTO.getBankCardNumber());
        cn.com.taiji.data.Result result = userClient.bindCard(user);
        if (result.isSuccess()){
            return MyResult.ofSuccess("绑定成功");
        }
        return MyResult.ofError(5000,"绑定失败");
    }

    /**
     * 用户登陆
     * @param account
     * @param password
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public MyResult<Token> login(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password) throws Exception {
        Result<Token> login = null;
        try {
            login = userClient.login(account, password);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (login.isSuccess()){
            return MyResult.ofSuccess(login.getData());
        }
        return MyResult.ofError(4000,login.getMessage());
    }

    /**
     * 注册用户
     * @param userEntity
     * @return
     */
    @RequestMapping(path = "/users", method = RequestMethod.POST, name = "createUser")
    public MyResult<UserEntity> createUser(@RequestBody UserEntity userEntity){
        Result<UserEntity> createUser = null;
        try{
            createUser = userClient.createUser(userEntity);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (createUser.isSuccess()){
            return MyResult.ofSuccess(createUser.getData());
        }
        return MyResult.ofError(4000,createUser.getMessage());
    }

    /**
     * 发送注册短信验证码
     * @param mobile
     * @return
     */
    @PostMapping("/sendRegisterSmsCode")
    public MyResult sendRegisterSmsCode (@RequestParam(value ="mobile") String mobile){
        Result send = null;
        try{
            send = userClient.sendRegisterSmsCode(mobile);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (send.isSuccess()){
            return MyResult.ofSuccess();
        }
        return MyResult.ofError(4000,send.getMessage());
    }

    /**
     * 发送修改密码验证码短信
     * @param mobile
     * @return
     */
    @PostMapping("/sendChangePasswordSmsCode")
    public MyResult sendChangePasswordSmsCode(@RequestParam(value ="mobile") String mobile){
        Result send = null;
        try{
            send = userClient.sendChangePasswordSmsCode(mobile);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (send.isSuccess()){
            return MyResult.ofSuccess();
        }
        return MyResult.ofError(4000,send.getMessage());
    }

    /**
     * 发送找回密码验证码短信
     * @param mobile
     * @return
     */
    @PostMapping("/sendFindPasswordSmsCode")
    public MyResult sendFindPasswordSmsCode (@RequestParam(value ="mobile") String mobile){
        Result send = null;
        try{
            send = userClient.sendFindPasswordSmsCode(mobile);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (send.isSuccess()){
            return MyResult.ofSuccess();
        }
        return MyResult.ofError(4000,send.getMessage());
    }

    /**
     * 修改密码
     * @param userEntity
     * @return
     */
    @PostMapping("/changePassword")
    public MyResult changePassword(@RequestBody UserEntity userEntity){
        Result res = null;
        try{
            res = userClient.changePassword(userEntity);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (res.isSuccess()){
            return MyResult.ofSuccess(res.getData());
        }
        return MyResult.ofError(4000,res.getMessage());
    }

    /**
     * 找回密码
     * @param userEntity
     * @return
     */
    @RequestMapping(path = "/findPassword", name = "findPassword")
    public MyResult  findPassword (@RequestBody UserEntity userEntity){
        Result res = null;
        try{
            res = userClient.findPassword(userEntity);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (res.isSuccess()){
            return MyResult.ofSuccess(res.getData());
        }
        return MyResult.ofError(4000,res.getMessage());
    }





}
