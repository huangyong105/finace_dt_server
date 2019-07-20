package cn.com.taiji.DTO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dongyuan
 * @date 2019-03-24 15:30
 **/
public class InvestmentDetailsDTO {
    /**
     * 主键自增id
     */
    private Long id;
    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 投资人id
     */
    private Long investmenterId;
    /**
     * 投资人名称
     */
    private String investmenterName;
    /**
     * 投资人手机号
     */
    private String investmenterTel;
    /**
     * 投资人邮箱
     */
    private String investmenterEmail;
    /**
     * 投资人银行卡号码
     */
    private String bankCardNumber;
    /**
     * 身份证正面
     */
    private String idCardPngUp;
    /**
     * 身份正反面
     */
    private String idCardPngDown;

    /**
     * 身份证号码
     */
    private String idCardNumber;

    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 预期月收益
     */
    private Integer monthEarnings;
    /**
     * 预期承受风险
     */
    private Integer expectedRiskTolerance;
    /**
     * 投入保证金
     */
    private BigDecimal inputMargin;
    /**
     * 资金配比
     */
    private String moneyProportion;
    /**
     * 入金时间
     */
    private Date inputMarginTime;
    /**
     * 支付订单号
     */
    private String payOrderId;
    /**
     * 支付时间
     */
    private String payTime;
    /**
     * 当前投资状态
     */
    private Integer state;

    /**
     * 状态描述
     */
    private String stateDesc;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 更新时间
     */
    private Date gmtUpdated;

    /**
     * 起始页
     */
    private Integer currentPage;
    /**
     * 每页大小
     */
    private Integer pageSize;
    /**
     * 开始时间
     */
    private Long beginTime;
    /**
     * 结束时间
     */
    private Long endTime;
    /**
     * 搜索订单状态(1:支付成功，2：申请退款)
     */
    private Integer searchType;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInvestmenterId(Long investmenterId) {
        this.investmenterId = investmenterId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getInvestmenterId() {
        return investmenterId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getMonthEarnings() {
        return monthEarnings;
    }

    public void setMonthEarnings(Integer monthEarnings) {
        this.monthEarnings = monthEarnings;
    }

    public Integer getExpectedRiskTolerance() {
        return expectedRiskTolerance;
    }

    public void setExpectedRiskTolerance(Integer expectedRiskTolerance) {
        this.expectedRiskTolerance = expectedRiskTolerance;
    }

    public BigDecimal getInputMargin() {
        return inputMargin;
    }

    public void setInputMargin(BigDecimal inputMargin) {
        this.inputMargin = inputMargin;
    }

    public String getMoneyProportion() {
        return moneyProportion;
    }

    public void setMoneyProportion(String moneyProportion) {
        this.moneyProportion = moneyProportion;
    }

    public Date getInputMarginTime() {
        return inputMarginTime;
    }

    public void setInputMarginTime(Date inputMarginTime) {
        this.inputMarginTime = inputMarginTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtUpdated() {
        return gmtUpdated;
    }

    public void setGmtUpdated(Date gmtUpdated) {
        this.gmtUpdated = gmtUpdated;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public String getInvestmenterName() {
        return investmenterName;
    }

    public void setInvestmenterName(String investmenterName) {
        this.investmenterName = investmenterName;
    }

    public String getInvestmenterTel() {
        return investmenterTel;
    }

    public void setInvestmenterTel(String investmenterTel) {
        this.investmenterTel = investmenterTel;
    }

    public String getInvestmenterEmail() {
        return investmenterEmail;
    }

    public void setInvestmenterEmail(String investmenterEmail) {
        this.investmenterEmail = investmenterEmail;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getIdCardPngUp() {
        return idCardPngUp;
    }

    public void setIdCardPngUp(String idCardPngUp) {
        this.idCardPngUp = idCardPngUp;
    }

    public String getIdCardPngDown() {
        return idCardPngDown;
    }

    public void setIdCardPngDown(String idCardPngDown) {
        this.idCardPngDown = idCardPngDown;
    }

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }
}
