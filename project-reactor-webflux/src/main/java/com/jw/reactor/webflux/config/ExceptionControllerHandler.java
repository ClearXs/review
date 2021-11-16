package com.jw.reactor.webflux.config;

import com.jw.reactor.webflux.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerHandler {

    @ExceptionHandler(RuntimeException.class)
    public R<String> handleException(RuntimeException e) {
        return R.exception(e);
    }
}
