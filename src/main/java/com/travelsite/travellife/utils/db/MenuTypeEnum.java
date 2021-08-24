package com.travelsite.travellife.utils.db;


/**
 * 菜单类型枚举

 */
public enum MenuTypeEnum {

    /**
     * 目录类型
     */
    DIRECTORY((byte)1, "目录"),
    /**
     * 菜单类型
     */
    MENU((byte)2, "菜单"),
    /**
     * 按钮类型
     */
    BUTTON((byte)3, "按钮"),
    ;

    private Byte code;

    private String message;

    MenuTypeEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

