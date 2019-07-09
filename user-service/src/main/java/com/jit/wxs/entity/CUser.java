package com.jit.wxs.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CUser implements Serializable {

    private Long id;
    private String account ;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer state;
    private String idCardNumber;
    private String bankCardNumber;
    private String idCardPngUp;
    private String idCardPngDown;
    private Integer currentPage;
    private Integer pageSize;
    private String stateDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }
}
