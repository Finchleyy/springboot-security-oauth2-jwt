package com.ypw.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yupengwu
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandle {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.OK);
    }

}