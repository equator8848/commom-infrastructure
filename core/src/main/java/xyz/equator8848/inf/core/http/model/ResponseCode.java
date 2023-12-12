package xyz.equator8848.inf.core.http.model;

public enum ResponseCode {
    /**
     * 状态码
     */
    SUCCESS(2000, "OK"),

    BAD_REQUEST(4000, "请求无效，客户端错误"),

    UNAUTHORIZED(4001, "请先登录"),

    FORBIDDEN(4003, "权限不足"),

    PRECONDITION_FAILED(4012, "参数校验不通过"),

    SERVER_ERROR(5000, "服务器错误");

    private int status;

    private String msg;

    ResponseCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
