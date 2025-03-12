package edu.gdufs.llmobjectiveevaluationsystemspringserver.exception;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result.NormalResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public NormalResult<?> handleException(Exception e) {
        return NormalResult.error(e.getMessage());
    }

}
