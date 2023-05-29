package com.bank.card.utils;

import com.bank.card.domain.error.dto.GeneralErrorResponse;
import com.bank.card.domain.error.model.ConflictException;
import com.bank.card.domain.error.model.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@ControllerAdvice
public class RestExceptionHandler {

    private static final ConcurrentHashMap<String, HttpStatus> STATUS_CODES =
            new ConcurrentHashMap<>();

    public RestExceptionHandler() {
        STATUS_CODES.put(ConflictException.class.getSimpleName(), HttpStatus.CONFLICT);
        STATUS_CODES.put(NotFoundException.class.getSimpleName(), HttpStatus.NOT_FOUND);
        STATUS_CODES.put(MethodArgumentNotValidException.class.getSimpleName(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConflictException.class})
    public final ResponseEntity<GeneralErrorResponse> handleConflictExceptions(
            ConflictException ex, HttpServletRequest httpRequest) {
        log.error("handleConflictExceptions", ex);

        HttpStatus status =
                STATUS_CODES.getOrDefault(ex.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR);
        var response =
                GeneralErrorResponse.builder()
                        .message(ex.getMessage())
                        .error(ex.getError())
                        .status(status.value())
                        .path(httpRequest.getRequestURI())
                        .build();

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(value = {Exception.class})
    public final ResponseEntity<Object> handleAllExceptions(
            Exception ex, HttpServletRequest httpRequest) {
        log.error("handleAllExceptions", ex);


        HttpStatus status =
                STATUS_CODES.getOrDefault(ex.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR);
        var response =
                GeneralErrorResponse.builder()
                        .error(ex.getMessage())
                        .message("Internal server error")
                        .status(status.value())
                        .path(httpRequest.getRequestURI())
                        .build();

        return new ResponseEntity<>(response, status);
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public final ResponseEntity<Object> handleArgumentNotValidExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest httpRequest) {
        log.error("handleArgumentNotValidExceptions", ex);
        var errors = new ArrayList<String>();
        ex.getBindingResult().getFieldErrors().forEach(f -> errors.add(f.getField() + ": " + f.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors().forEach(g -> errors.add(g.getObjectName() + ": " + g.getDefaultMessage()));
        var response =
                GeneralErrorResponse.builder()
                        .error(errors.toString())
                        .message("Bad Request")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .path(httpRequest.getRequestURI())
                        .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public final ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception, HttpServletRequest httpRequest) {
        var response =
                GeneralErrorResponse.builder()
                        .error(exception.getCause().getCause().getMessage())
                        .message("Bad Request")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .path(httpRequest.getRequestURI())
                        .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
