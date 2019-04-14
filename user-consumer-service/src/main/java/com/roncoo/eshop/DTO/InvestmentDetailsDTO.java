package com.roncoo.eshop.DTO;

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
    private Integer id;

    private Integer projectId;

    private Integer investmenterId;

    private String projectName;

    private Integer monthEarnings;

    private Integer expectedRiskTolerance;

    private BigDecimal inputMargin;

    private String moneyProportion;

    private Date inputMarginTime;

    private Integer state;

    private Date gmtCreated;

    private Date gmtUpdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getInvestmenterId() {
        return investmenterId;
    }

    public void setInvestmenterId(Integer investmenterId) {
        this.investmenterId = investmenterId;
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
