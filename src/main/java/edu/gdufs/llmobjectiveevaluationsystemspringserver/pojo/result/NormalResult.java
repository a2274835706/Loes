package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result;

import lombok.Data;

/**
 * 一般响应结果类
 *
 * @param <T> 响应数据类
 */

@Data
public class NormalResult<T> {

    public static int CODE_ERROR = 0;
    public static int CODE_SUCCESS = 1;
    public static String OK = "OK";
    public static String SYSTEM_ERROR = "SYSTEM_ERROR";
    public static String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static String AUTHORIZED_ERROR = "UNAUTHORIZED_ERROR";
    public static String IDENTIFICATION_ERROR = "IDENTIFICATION_ERROR";
    public static String EXISTENCE_ERROR = "EXISTENCE_ERROR";

    private int code;
    private String message;
    private T data;

    public NormalResult() {}

    public NormalResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    static public NormalResult<?> success() {
        return new NormalResult<>(CODE_SUCCESS, OK, null);
    }

    static public <E> NormalResult<E> success(E data) {
        return new NormalResult<>(CODE_SUCCESS, OK, data);
    }

    static public NormalResult<?> error(String message) {
        return new NormalResult<>(CODE_ERROR, message, null);
    }

    static public <E> NormalResult<E> error(String message, E data) {
        return new NormalResult<>(CODE_ERROR, message, data);
    }

}
