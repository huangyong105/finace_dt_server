package com.jit.wxs.Enum;

import java.util.Arrays;

public enum StateEnum {
    //投资人状态(100未实名未绑卡，110已实名未绑卡，111实名绑卡，101未实名已绑卡)
    ONE(100,"未实名未绑卡"),
    TWO(110,"已实名未绑卡"),
    THREE(111,"实名绑卡"),
    FOUR(101,"未实名已绑卡"),
    UNKOWN(-1,"unkown");

    private int stateCode;
    private String desc;
    StateEnum(int stateCode, String desc) {
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
        return Arrays.stream(StateEnum.values()).filter(e -> e.getDesc().equals(stateCode)).findFirst().orElse(StateEnum.UNKOWN).desc;
    }
}
