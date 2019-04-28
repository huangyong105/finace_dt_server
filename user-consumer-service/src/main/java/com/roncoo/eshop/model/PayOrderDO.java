package com.roncoo.eshop.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayOrderDO {
    private Long id;
    private String orderId;
    private Long userId;
    private BigDecimal inputMargin;
    private String projectName;
    private String payDescribe;
    private String payTitle;
    private Data gmtCreated;
    private Data gmtUpdated;
    private Integer payState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public Data getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Data gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Data getGmtUpdated() {
        return gmtUpdated;
    }

    public void setGmtUpdated(Data gmtUpdated) {
        this.gmtUpdated = gmtUpdated;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }
}
