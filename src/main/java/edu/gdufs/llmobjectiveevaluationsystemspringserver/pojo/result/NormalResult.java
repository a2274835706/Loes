package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 一般响应结果类
 *
 * @param <T> 响应数据类
 */

@Data
public class NormalResult<T> {

    public static int CODE_ERROR = 0;
    public static int CODE_SUCCESS = 1;
    public static String STATUS_OK = "OK";
    public static String STATUS_SYSTEM_ERROR = "SYSTEM_ERROR";
    public static String STATUS_VALIDATION_ERROR = "VALIDATION_ERROR";
    public static String STATUS_UNAUTHORIZED = "UNAUTHORIZED";
    public static String STATUS_IDENTIFICATION_ERROR = "IDENTIFICATION_ERROR";
    public static String STATUS_USER_EXIST_ERROR = "USER_EXIST_ERROR";

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
        return new NormalResult<>(CODE_SUCCESS, STATUS_OK, null);
    }

    static public <E> NormalResult<E> success(E data) {
        return new NormalResult<>(CODE_SUCCESS, STATUS_OK, data);
    }

    static public NormalResult<?> error(String message) {
        return new NormalResult<>(CODE_ERROR, message, null);
    }

    static public <E> NormalResult<E> error(String message, E data) {
        return new NormalResult<>(CODE_ERROR, message, data);
    }

}
