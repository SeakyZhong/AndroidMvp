package org.standardmvp.net.exception;

/**
 * 服务器异常封装
 * Created by Seaky on 2017/9/22.
 */

public class ServerException extends RuntimeException {

    private int code;
    private String msg;

    public ServerException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
