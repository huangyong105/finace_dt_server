package cn.com.taiji.data;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/4/4 11:13
 */
public enum ResultCode {

    FAILED("-1", 999, "调用接口失败", null),
    SUCCESS("0000", 0000, "调用接口成功", null);


    private String code;
    private int key;
    private String value;
    private Object data;

    ResultCode(String code, int key, String value, Object data){
        setCode(code);
        setKey(key);
        setValue(value);
        setData(data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSuccess(){
        if ("0000".equals(this.code)) {
            return true;
        }
        return false;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
