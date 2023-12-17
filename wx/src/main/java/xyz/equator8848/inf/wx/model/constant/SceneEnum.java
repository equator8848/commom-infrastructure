package xyz.equator8848.inf.wx.model.constant;

public enum SceneEnum {
    LOGIN("LOGIN", "LOGIN_%s"),

    BIND("BIND","BIND_%s");

    private String code;
    private String formatter;

    SceneEnum(String code, String formatter) {
        this.code = code;
        this.formatter = formatter;
    }

    public String getCode() {
        return code;
    }

    public String getFormatter() {
        return formatter;
    }
}
