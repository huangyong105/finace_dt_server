package cn.com.taiji.data;

import java.io.Serializable;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:yong.huang
 * @Date: 2019/4/4 11:09
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -6333578742323539316L;

    public static final String SUCCESS_CODE = "0000";

    /**操作成功,data为空*/
    public static final Result OK = new Result<>(SUCCESS_CODE);
    private String code;
    private String errMsg;
    private String message;
    private T data;
    public boolean isSuccess() {
        return SUCCESS_CODE.equals(getCode());
    }
    /**
     * result.isException()=true表示会员系统发生了异常，此时isSuccess是false，message是异常的message
     * <br>调用方可以处理异常，logger或者throw
     * <br>调用方如果可以忽略这个异常 就直接使用isSuccess判断返回结果
     * @return
     */
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getMessage() {
        return getErrMsg();
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    public Result(){

    }

    public Result(String code, String msg, T data){
        this.code = code;
        this.errMsg = msg;
        this.data = data;
    }



    public Result(String code) {
        this.code = code;
    }

    public Result(String code, T data) {
        this.code = code;
        this.data = data;
    }




    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Result failure(String code, String msg){
        return new Result(code, msg,null);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Result failure(String code, String msg, Object data){
        return new Result(code, msg, data);
    }

    @SuppressWarnings("rawtypes")
    public static Result success(){
        return OK;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Result success(Object data){
        return new Result(SUCCESS_CODE, data);
    }

    @SuppressWarnings("unchecked")
    public static Result success(Object data, String msg){
        return new Result(SUCCESS_CODE, msg, data);
    }

    public static Result failure(String code){
        return new Result(code);
    }

    public static Result result(ResultCode remoteResultCode){
        return new Result(remoteResultCode.getCode(), remoteResultCode.getValue());
    }

    public static Result result(ResultCode remoteResultCode, Object data){
        return new Result(remoteResultCode.getCode(), remoteResultCode.getValue(), data);
    }
    @Override
    public String toString() {
        return "Result [code=" + code + ", errMsg=" + errMsg + ", data=" + data + "]";
    }

}
