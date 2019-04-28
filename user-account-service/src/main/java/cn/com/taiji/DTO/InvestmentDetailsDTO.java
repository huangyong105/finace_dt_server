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
     * 当前投资状态
     */
    private Integer state;
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
}
