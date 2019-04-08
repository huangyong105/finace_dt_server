package com.roncoo.eshop.model;

import lombok.Data;

import java.util.Date;

@Data
public class InvestorManagementDO {
    private Integer id;

    private String telNumber;

    private String userPassword;

    private String email;

    private Integer state;

    private String idCardNumber;

    private String bankCardNumber;

    private String idCardPngUp;

    private String idCardPngDown;

    private Date gmtCreated;

    private Date gmtUpdated;

    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
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

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}