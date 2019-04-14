package com.roncoo.eshop.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProjectManagementDO {
    private Long id;

    private String projectName;

    private Integer monthEarnings;

    private Integer expectedRiskTolerance;

    private BigDecimal minMargin;

    private String moneyProportion;

    private Date gmtCreated;

    private Date gmtUpdated;

    private Integer isOnline;

    private Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getMinMargin() {
        return minMargin;
    }

    public void setMinMargin(BigDecimal minMargin) {
        this.minMargin = minMargin;
    }

    public String getMoneyProportion() {
        return moneyProportion;
    }

    public void setMoneyProportion(String moneyProportion) {
        this.moneyProportion = moneyProportion;
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

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}