package com.roncoo.eshop.result;

/**
 * 公用的错误编码类
 * 模块码建议以最长四位大写字母为业务模块编码
 * 错误码规范(四位数字),业务方扩展的业务错误码建议以1开头的五位数
 * 10000以内的错误吗为通用编码，如操作成功、参数错误、数据库操作错误、rpc调用失败等
 * 10000以上为业务编码，此处不错限制，由业务方自定义
 * Created by zhangtao on 16/12/22.
 */
public class BaseResultConstant {

    /**
     * 2000~2999为系统报错，具体业务编码可以由业务方自行定义
     */
    public static final BaseResultConstant SYSTEM_ERROR = new BaseResultConstant(2000, "System Error");
    /**
     * 3000~3999为参数报错，具体业务编码可以由业务方自行定义
     */
    public static final BaseResultConstant PARAM_ERROR = new BaseResultConstant(3000, "Param Error");
    /**
     * 4000~4999为RPC报错，具体业务编码可以由业务方自行定义
     */
    public static final BaseResultConstant RPC_ERROR = new BaseResultConstant(4000, "RPC Error");
    /**
     * 2000~2999为系统报错，具体业务编码可以由业务方自行定义
     */
    public static final BaseResultConstant MYSQL_ERROR = new BaseResultConstant(6000, "数据库异常");
    /**
     * 1000~1999的code码表示成功
     */
    public static final BaseResultConstant SUCCESS = new BaseResultConstant(1000, "success");

    //错误码信息
    private int resultCode;

    //错误描述信息
    private String description;

    public BaseResultConstant(int resultCode, String description) {
        this.resultCode = resultCode;
        this.description = description;
    }

    //返回错误编码
    public int getResultCode() {
        return this.resultCode;
    }

    //由异常码中获取错误编码的对应错误描述
    public String getDescription() {
        return this.description;
    }

}