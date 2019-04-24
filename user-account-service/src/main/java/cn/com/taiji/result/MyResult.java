package cn.com.taiji.result;

import java.io.Serializable;

/**
 * 应用的统一返回值对象
 *
 * @reviewer
 */
public class MyResult<T> implements Serializable {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回描述
     */
    private String message;

    /**
     * 返回的值对象
     */
    private T value;

    private MyResult() {
    }

    public static <T> MyResult<T> ofSuccess() {
        return ofSuccess(null);
    }

    public static <T> MyResult<T> ofSuccess(T value) {
        MyResult<T> result = new MyResult<>();
        result.setSuccess(true);
        result.setValue(value);
        return result;
    }

    public static <T> MyResult<T> ofErrorT(int code, String message) {
        MyResult<T> result = new MyResult<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static MyResult ofError(int code, String message) {
        MyResult result = new MyResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static MyResult ofError(BaseResultConstant errorCode) {
        MyResult result = new MyResult();
        result.setSuccess(false);
        result.setCode(errorCode.getResultCode());
        result.setMessage(errorCode.getDescription());
        return result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

