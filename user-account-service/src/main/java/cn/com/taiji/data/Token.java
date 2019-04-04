package cn.com.taiji.data;


import java.io.Serializable;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/4/4 3:14
 */
public class Token implements Serializable {

    private  String token ;

    private  Integer experin ;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExperin() {
        return experin;
    }

    public void setExperin(Integer experin) {
        this.experin = experin;
    }
}
