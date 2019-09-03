package com.roncoo.eshop.web.controller;



import cn.com.taiji.DTO.InvestmentDetailsDTO;
import cn.com.taiji.DTO.InvestorManagementDTO;
import cn.com.taiji.DTO.SysUser;
import cn.com.taiji.data.Result;
import cn.com.taiji.data.Token;
import cn.com.taiji.data.User;
import cn.com.taiji.data.UserEntity;

import cn.com.taiji.page.PageInfoDTO;
import cn.com.taiji.page.PageResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.roncoo.eshop.client.BUserClient;
import com.roncoo.eshop.client.UserClient;
import com.roncoo.eshop.config.AliPayConfig;
import com.roncoo.eshop.converter.BeanConverter;
import com.roncoo.eshop.manager.InvestorManager;
import cn.com.taiji.result.MyResult;
import com.roncoo.eshop.manager.MailManager;
import com.roncoo.eshop.manager.PayOrderManager;
import com.roncoo.eshop.mapper.InvestmentDetailsMapper;
import com.roncoo.eshop.mapper.PayOrderMapper;
import com.roncoo.eshop.mapper.ProjectManagementMapper;
import com.roncoo.eshop.model.*;
import com.roncoo.eshop.util.OrderCodeUtil;
import com.roncoo.eshop.util.PayCommonUtil;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
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
    @Autowired
    private AliPayConfig aliPayConfig;
    @Autowired
    ProjectManagementMapper projectManagementMapper;
    @Autowired
    InvestmentDetailsMapper investmentDetailsMapper;
    @Autowired
    PayOrderMapper payOrderMapper;
    @Autowired
    MailManager mailManager ;
    @Autowired
    private BUserClient bUserClient;
    @Value("${image_path_url}")
    String imagePathUrl;
    @Value("${ali.notifyUrl}")
    String notifyUrl;
    @Value("${spring.mail.username}")
    private String fromEmail;


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
        investorManagementDTO.setName(user.getUsername());
        return MyResult.ofSuccess(investorManagementDTO);
    }

    /**
     * 获取用户投资详情
     * @return
     */
    @RequestMapping("/myInvestment")
    public MyResult<PageResult<InvestmentDetailsDTO>> getMyInvestment(@RequestHeader("token")String token,@RequestBody InvestmentDetailsDTO investmentDetailsDTO){
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
        Integer currentPage = investmentDetailsDTO.getCurrentPage();
        Integer pageSize = investmentDetailsDTO.getPageSize();
        if (currentPage == null) {
            currentPage = 1;
        }
        if (pageSize == null) {
            pageSize = 100000;
        }
        Long id = userResult.getData().getId();
        PageResult<InvestmentDetailsDTO> investmentDetailsDOSByuserId = investorManager.getInvestmentDetailsDOSByuserId(id, currentPage, pageSize);
        return MyResult.ofSuccess(investmentDetailsDOSByuserId);
    }

    /**
     * 用户投资项目申请退款
     * @param token
     * @return
     */
    @RequestMapping("/applyRefund")
    public MyResult applyRefund(@RequestHeader("token")String token,@RequestParam Long id){
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
        InvestmentDetailsDO investmentDetailsDOSById = investorManager.getInvestmentDetailsDOSById(id);
        if (investmentDetailsDOSById==null){
            return MyResult.ofError(5000,"未找到该投资记录");
        }
        Long aLong = investorManager.updateState(id, 3);
        if (null == aLong){
            return MyResult.ofError(5000,"申请失败");
        }
        return MyResult.ofSuccess("申请退款成功");
    }

    /**
     * 已退款操作
     * todo 提供内部调用
     * @param
     * @return
     */
    @RequestMapping("/refunded")
    public MyResult refunded(@RequestBody InvestmentDetailsDTO investmentDetailsDTO){
        InvestmentDetailsDO investmentDetailsDOSById = investorManager.getInvestmentDetailsDOSById(investmentDetailsDTO.getId());
        if (investmentDetailsDOSById==null){
            return MyResult.ofError(5000,"未找到该投资记录");
        }
        if (investmentDetailsDOSById.getState()==2){
            return MyResult.ofError(5000,"该订单已退款");
        }
        Long aLong = investorManager.updateState(investmentDetailsDTO.getId(), 2);
        if (null == aLong){
            return MyResult.ofError(5000,"操作失败");
        }
        return MyResult.ofSuccess("退款操作成功");
    }



    /**
     * 获取用户投资详情
     * todo 提供内部调用
     * @return
     */
    @RequestMapping("/getMyInvestment")
    public MyResult<PageResult<InvestmentDetailsDTO>> getMyInvestment(@RequestBody InvestmentDetailsDTO investmentDetailsDTO){
        Integer currentPage = investmentDetailsDTO.getCurrentPage();
        Integer pageSize = investmentDetailsDTO.getPageSize();
        if (currentPage == null) {
            currentPage = 1;
        }
        if (pageSize == null) {
            pageSize = 100000;
        }
        PageResult<InvestmentDetailsDTO> dtos = investorManager.getInvestmentDetailsDOSByuserId(investmentDetailsDTO.getInvestmenterId(),currentPage,pageSize);

        return MyResult.ofSuccess(dtos);
    }


    /**
     * 通过筛选条件获取投资详情信息
     * todo 提供内部调用
     * @param investmentDetailsDTO
     * @return
     */
    @RequestMapping("/getInvestmentProduct")
    public MyResult<List<InvestmentDetailsDTO>> getInvestmentProduct(@RequestBody InvestmentDetailsDTO investmentDetailsDTO){
        if (investmentDetailsDTO.getSearchType()==null){
            return MyResult.ofError(4000,"未选择筛选条件");
        }
        List<InvestmentDetailsDTO> investmentDetailsList = null;
        if (investmentDetailsDTO.getSearchType()==1){
            investmentDetailsList = investorManager.getInvestmentDetailsDTOByPaySaccess(investmentDetailsDTO);
        }
        if (investmentDetailsDTO.getSearchType()==2){
            investmentDetailsList=investorManager.getInvestmentDetailsDTOByrefound(investmentDetailsDTO);
        }
        return MyResult.ofSuccess(investmentDetailsList);
    }

    /**
     * 支付宝预下单
     * @param token
     * @param investmentDetailsDTO
     * @return
     */
    @RequestMapping("/payProjectForAli")
    public MyResult payProjectForAli(@RequestHeader("token")String token,@RequestBody InvestmentDetailsDTO investmentDetailsDTO){
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
        Long userId = userResult.getData().getId();
        String userName = userResult.getData().getAccount();
        //生成唯一支付订单id
        String orderCode = OrderCodeUtil.getOrderCode(userId);
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        model.setBody(investmentDetailsDTO.getProjectName());
        model.setSubject("项目投资");
        model.setOutTradeNo(orderCode);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(investmentDetailsDTO.getInputMargin().toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            LOG.info("预下单开始，orderCode:{}",orderCode);
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            infoStr = response.getBody();
            LOG.info(response.getBody());
            PayOrderDO payOrderDO = payOrderManager.saveAliPayOrder(orderCode, investmentDetailsDTO, userId,userName,1,response.getTradeNo());
            if (payOrderDO == null){
                return MyResult.ofError(4000,"预下单存储失败");
            }
            return MyResult.ofSuccess(infoStr);
    } catch (AlipayApiException e) {
            LOG.error("支付宝预下单失败:{}",orderCode);
            e.printStackTrace();
        }
        return MyResult.ofError(4000,"支付宝预下单失败");

    }

    /**
     * 微信预下单
     * @param token
     * @param investmentDetailsDTO
     * @return
     */
    @RequestMapping("/payProjectForWx")
    public MyResult<Map<String,Object>> payProjectForWx(@RequestHeader("token")String token,@RequestBody InvestmentDetailsDTO investmentDetailsDTO,HttpServletRequest request){
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
        Long userId = userResult.getData().getId();
        String userName = userResult.getData().getAccount();
        //生成唯一支付订单id
        String orderCode = OrderCodeUtil.getOrderCode(userId);
        String sym = request.getRequestURL().toString().split("/investor/")[0];
        String notifyUrl = sym + "/investor/wxpayCallback";
        JSONObject jsAtt = new JSONObject();
        jsAtt.put("uid", userId);
        String attach = jsAtt.toJSONString();
        LOG.info("微信预下单开始:{}",orderCode);
        SortedMap<String, Object> map = PayCommonUtil.WxPublicPay(orderCode, investmentDetailsDTO.getInputMargin(), investmentDetailsDTO.getProjectName(), attach, notifyUrl, request);
        PayOrderDO payOrderDO = payOrderManager.saveWxPayOrder(orderCode, investmentDetailsDTO, userId,userName,2);
        if (payOrderDO == null){
            return MyResult.ofError(4000,"预下单存储失败");
        }
        return MyResult.ofSuccess(map);
    }

    /**
     * 支付宝回调，确认订单支付
     * @param
     * @return
     */
    @RequestMapping("/alipayCallback")
    public String addMyInvestment(HttpServletRequest request){
        Map<String, String> params = investorManager.convertRequestParamsToMap(request);
        String paramsJson = JSON.toJSONString(params);
        LOG.info("支付宝回调,{}",paramsJson);
        try {
            //boolean signVerified = AlipaySignature.rsaCheckV1(params, aliPayConfig.getAlikey(), "UTF-8", "RSA2");
            if (true){
                LOG.info("支付宝回调签名认证成功");
                investorManager.aliCheck(params);
                AlipayNotifyParam param = investorManager.buildAlipayNotifyParam(params);
                LOG.info("组装完成:{}",param);
                String trade_status = param.getTrade_status();
                // 支付成功
                if (trade_status.equals("TRADE_SUCCESS") || trade_status.equals("TRADE_FINISHED")) {
                    LOG.info("验证完成");
                    // 处理支付成功逻辑
                    String outTradeNo = param.getOut_trade_no();
                    PayOrderDO payOrderDO = payOrderMapper.selectByOrderId(outTradeNo);
                    LOG.info("获取支付订单:{}",payOrderDO);
                    if (payOrderDO.getPayState()==0){
                            try {
                                    ProjectManagementDO projectManagementDO = projectManagementMapper.selectByPrimaryKey(payOrderDO.getProjectId());
                                    InvestmentDetailsDO investmentDetailsDO = new InvestmentDetailsDO();
                                    investmentDetailsDO.setProjectId(projectManagementDO.getId());
                                    investmentDetailsDO.setInvestmenterId(payOrderDO.getUserId());
                                    investmentDetailsDO.setProjectName(projectManagementDO.getProjectName());
                                    investmentDetailsDO.setMonthEarnings(projectManagementDO.getMonthEarnings());
                                    investmentDetailsDO.setExpectedRiskTolerance(projectManagementDO.getExpectedRiskTolerance());
                                    investmentDetailsDO.setInputMargin(payOrderDO.getInputMargin());
                                    investmentDetailsDO.setMoneyProportion(projectManagementDO.getMoneyProportion());
                                    investmentDetailsDO.setInputMarginTime(payOrderDO.getGmtCreated());
                                    investmentDetailsMapper.insert(investmentDetailsDO);

                                String formatDate = null;
                                DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH表示24小时制；
                                formatDate = dFormat.format(investmentDetailsDO.getInputMarginTime());

                                Long orderId = investmentDetailsMapper.selectId(formatDate,investmentDetailsDO.getInvestmenterId());
                                    payOrderDO.setOrderId(orderId);
                                    payOrderDO.setPayState(1);
                                    payOrderMapper.updateOrderIdAndState(payOrderDO);


                                cn.com.taiji.result.Result<List<SysUser>> result = bUserClient.findAllUsersByPerm();
                                List<SysUser> value = result.getValue();
                                for (SysUser sysUser:value) {
                                    String[] to = new String[]{sysUser.getEmail()};
                                    String subject = "用户支付成功通知";
                                    EmailModelDTO email = new EmailModelDTO(fromEmail, to, null, subject, "用户："+payOrderDO.getUserName()+"使用支付宝成功支付了"+payOrderDO.getInputMargin()+"元购买了项目("+projectManagementDO.getProjectName()+"),请及时处理!", null);
                                    mailManager.sendSimpleMail(email);
                                }
                                    return "success";
                                } catch (Exception e) {
                                    LOG.error("支付宝回调业务处理报错,params:" + paramsJson, e);
                                }
                            }
                        }else if(trade_status.equals("TRADE_CLOSED")){
                            PayOrderDO payOrderDO = new PayOrderDO();
                            payOrderDO.setPayState(2);
                            payOrderDO.setPayOrderId(param.getOut_trade_no());
                            payOrderMapper.updateState(payOrderDO);
                        }else {
                            LOG.error("没有处理支付宝回调业务，支付宝交易状态：{},params:{}",trade_status,paramsJson);
                        }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
       return null;
    }

    /**
     * 微信支付回调
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/wxpayCallback")
    public String wxpayCallback(HttpServletRequest request, HttpServletResponse response) throws IOException{
        BufferedReader reader = null;
        reader = request.getReader();
        String line = "";
        String xmlString = null;
        StringBuffer inputString = new StringBuffer();
        while((line = reader.readLine())!=null){
            inputString.append(line);
        }
        xmlString = inputString.toString();
        request.getReader().close();
        LOG.info("收到微信支付回调信息:"+xmlString);
        Map<String,String> map = new HashMap<String,String>();
        try {
            map = PayCommonUtil.doXMLParse(xmlString);
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        String result_code =map.get("result_code");
        //验证sign是否正确
        if (!PayCommonUtil.isTenpaySign(map)) {
            LOG.info("支付失败！");
            return returnXML("FAIL");
        } else {
           if (investorManager.wxCheck(map)){
               PayOrderDO payOrderDO = payOrderMapper.selectByOrderId(map.get("out_trade_no"));
               if (payOrderDO.getPayState()==0){
                   ProjectManagementDO projectManagementDO = projectManagementMapper.selectByPrimaryKey(payOrderDO.getProjectId());
                   InvestmentDetailsDO investmentDetailsDO = new InvestmentDetailsDO();
                   investmentDetailsDO.setProjectId(projectManagementDO.getId());
                   investmentDetailsDO.setInvestmenterId(payOrderDO.getUserId());
                   investmentDetailsDO.setProjectName(projectManagementDO.getProjectName());
                   investmentDetailsDO.setMonthEarnings(projectManagementDO.getMonthEarnings());
                   investmentDetailsDO.setExpectedRiskTolerance(projectManagementDO.getExpectedRiskTolerance());
                   investmentDetailsDO.setInputMargin(payOrderDO.getInputMargin());
                   investmentDetailsDO.setMoneyProportion(projectManagementDO.getMoneyProportion());
                   investmentDetailsDO.setInputMarginTime(payOrderDO.getGmtCreated());
                   investmentDetailsMapper.insert(investmentDetailsDO);
                   String formatDate = null;
                   DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH表示24小时制；
                   formatDate = dFormat.format(investmentDetailsDO.getInputMarginTime());

                   Long orderId = investmentDetailsMapper.selectId(formatDate,investmentDetailsDO.getInvestmenterId());
                   payOrderDO.setOrderId(orderId);
                   payOrderDO.setPayState(1);
                   payOrderMapper.updateOrderIdAndState(payOrderDO);
                   LOG.info("支付成功！支付订单id:{}",payOrderDO.getPayOrderId());
                   cn.com.taiji.result.Result<List<SysUser>> result = bUserClient.findAllUsersByPerm();
                   List<SysUser> value = result.getValue();
                   for (SysUser sysUser:value) {
                       String[] to = new String[]{sysUser.getEmail()};
                       String subject = "用户支付成功通知";
                       EmailModelDTO email = new EmailModelDTO(fromEmail, to, null, subject, "用户："+payOrderDO.getUserName()+"使用微信成功支付了"+payOrderDO.getInputMargin()+"元购买了项目("+projectManagementDO.getProjectName()+"),请及时处理!", null);
                       mailManager.sendSimpleMail(email);
                   }
                   return returnXML(result_code);
               }else{
                    LOG.info("推送消息重复!支付订单id:{}",payOrderDO.getPayOrderId());
                    return returnXML(result_code);
               }
           }
            LOG.info("接收消息无效!");
            return null;
        }
    }




    @RequestMapping("/test")
    public MyResult test(){
        cn.com.taiji.result.Result<List<SysUser>> result = bUserClient.findAllUsersByPerm();

        List<SysUser> value = result.getValue();
        for (SysUser sysUser:value) {
            LOG.info(sysUser.getEmail());
            String[] to = new String[]{sysUser.getEmail()};
            String subject = "用户支付成功通知";
            EmailModelDTO email = new EmailModelDTO(fromEmail, to, null, subject, sysUser.getName()+"支付了"+1+"元购买了"+"测试服务", null);
            mailManager.sendSimpleMail(email);
        }
        return null;
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
        user.setUsername(investorManagementDTO.getName());
        user.setBankCardNumber(investorManagementDTO.getBankCardNumber());
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


    private String returnXML(String return_code) {

        return "<xml><return_code><![CDATA["

                + return_code

                + "]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }





}
