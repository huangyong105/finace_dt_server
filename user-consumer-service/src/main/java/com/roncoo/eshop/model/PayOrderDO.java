package com.roncoo.eshop.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PayOrderDO {
    private Long id;
    private String payOrderId;
    private Long userId;
    private BigDecimal inputMargin;
    private String projectName;
    private String payDescribe;
    private String payTitle;
    private Date gmtCreated;
    private Date gmtUpdated;
    private Integer payState;
    private Long projectId;
    private Long orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getInputMargin() {
        return inputMargin;
    }

    public void setInputMargin(BigDecimal inputMargin) {
        this.inputMargin = inputMargin;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPayDescribe() {
        return payDescribe;
    }

    public void setPayDescribe(String payDescribe) {
        this.payDescribe = payDescribe;
    }

    public String getPayTitle() {
        return payTitle;
    }

    public void setPayTitle(String payTitle) {
        this.payTitle = payTitle;
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

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
