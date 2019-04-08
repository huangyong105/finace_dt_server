package cn.com.taiji.user;

import cn.com.taiji.data.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class ChangePasswordDTO extends BaseEntity {
    @Column(name = "account", nullable = true)
    private String account ;
    @Column(name = "password", nullable = true)
    private String password;
    @Transient
    private String  code ;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
