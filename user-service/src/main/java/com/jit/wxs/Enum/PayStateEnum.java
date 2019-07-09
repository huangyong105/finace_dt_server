package com.jit.wxs.Enum;

import java.util.Arrays;

public enum PayStateEnum {
    //投资人状态(100未实名未绑卡，110已实名未绑卡，111实名绑卡，101未实名已绑卡)
    ONE(1,"正常"),
    TWO(2,"已退金"),
    THREE(3,"申请退款"),
    UNKOWN(-1,"unkown");

    private int stateCode;
    private String desc;
    PayStateEnum(int stateCode, String desc) {
        this.stateCode = stateCode;
        this.desc = desc;
    }

    public int getValue() {
        return stateCode;
    }

    public String getDesc() {
        return desc;
    }

    public static String getValueByKey(int stateCode) {
        return Arrays.stream(PayStateEnum.values()).filter(e -> e.getDesc().equals(stateCode)).findFirst().orElse(PayStateEnum.UNKOWN).desc;
    }
}
