package xyz.equator8848.inf.wx.model.constant;

public enum SendTemplateErrorCode {
    REQUIRE_SUBSCRIBE(43004, "require subscribe"),
    INVALID_OPEN_ID(40003, "invalid openid rid");


    private int code;

    private String msg;

    SendTemplateErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
