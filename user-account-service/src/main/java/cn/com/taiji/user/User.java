package cn.com.taiji.user;

import cn.com.taiji.data.BaseEntity;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author zhuohao
 */
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "account", nullable = true)
    private String account ;
    @Column(name = "username", nullable = true)
    private String username;
    @Column(name = "first_name", nullable = true)
    private String firstName;
    @Column(name = "last_name", nullable = true)
    private String lastName;
    @Column(name = "email", nullable = true)
    private String email;
    @Column(name = "password", nullable = true)
    private String password;
    @Column(name = "state",nullable = true)
    private Integer state;
    @Column(name = "id_card_number",nullable = true)
    private String idCardNumber;
    @Column(name = "bank_card_number",nullable = true)
    private String bankCardNumber;
    @Column(name = "id_card_png_up",nullable = true)
    private String idCardPngUp;
    @Column(name = "id_card_png_down",nullable = true)
    private String idCardPngDown;
    @Transient
    private String  code ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override

    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
