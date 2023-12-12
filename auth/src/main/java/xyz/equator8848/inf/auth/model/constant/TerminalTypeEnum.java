package xyz.equator8848.inf.auth.model.constant;

import lombok.Getter;

/**
 * 终端类型
 */
@Getter
public enum TerminalTypeEnum {
    /**
     * 登录终端类型
     */
    PC_WEB(0, "PC端-WEB"),
    PC_DESKTOP(1, "PC端-桌面端"),
    MOBILE_H5(2, "移动端-H5"),
    MOBILE_WX(3, "移动端-微信"),
    MOBILE_DING_TALK(4, "移动端-钉钉");

    private final Integer code;

    private final String name;

    TerminalTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

}
