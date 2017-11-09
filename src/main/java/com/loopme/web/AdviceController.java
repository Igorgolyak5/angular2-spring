package com.loopme.web;

import com.loopme.exceptions.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Igor Holiak.
 */
@Slf4j
@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleIllegalArgException(final Exception exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleException(final Throwable throwable) {
        log.error(throwable.getMessage());
        return new ResponseEntity<Object>(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
