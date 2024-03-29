package com.newegg.mkpl.api.blogsystem.enums;

/**
 * 返回的状态码
 *
 * @author vz04
 * @date 8/8/2019 3:20 PM
 **/
public enum StateEnum {
    /**
     * 错误的请求
     */
    FAIL(400),
    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 没有权限
     */
    NO_AUTHORITY(401),;

    private Integer code;

    StateEnum(Integer code) {
        this.code = code;
    }

    public int value() {
        return this.code;
    }

}
