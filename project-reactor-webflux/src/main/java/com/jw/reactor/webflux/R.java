package com.jw.reactor.webflux;

import lombok.Data;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {

    /**
     * @see org.springframework.http.HttpStatus
     */
    private int code;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 数据
     */
    private T data;

    /**
     * 消息
     */
    private String message;

    public static <T> R<T> success(T data) {
        return success("成功", data);
    }

    public static <T> R<T> success(String message, T data) {
        R<T> result = new R<>();
        result.setMessage(message);
        result.setCode(HttpStatus.OK.value());
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public static <T> R<T> exception(Throwable e) {
        R<T> result = new R<>();
        result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setSuccess(false);
        result.setMessage(e.getMessage());
        return result;
    }
}
