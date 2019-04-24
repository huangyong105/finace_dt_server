package cn.com.taiji.DTO;

import java.util.Date;

/**
 * @author dongyuan
 * @date 2019-03-24 15:22
 **/
public class InvestorManagementDTO {
    /**
     * 主键自增id
     */
    private Long id;
    /**
     * 电话
     */
    private String telNumber;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 状态
     */
    private Integer state;
    /**
     * 身份证号码
     */
    private String idCardNumber;
    /**
     * 银行卡号
     */
    private String bankCardNumber;
    /**
     * 身份证正面照片
     */
    private String idCardPngUp;
    /**
     * 身份证背面照片
     */
    private String idCardPngDown;

    private Date gmtCreated;

    private Date gmtUpdated;

    private Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
