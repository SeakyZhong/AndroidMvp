package org.standardmvp.net.exception;

/**
 * http请求异常封装
 * Created by Seaky on 2017/9/22.
 */

public class ApiException extends Exception {

    private int code;//错误码
    private String msg;//错误信息

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ApiException(int code, String msg) {
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
