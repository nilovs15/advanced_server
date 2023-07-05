package com.example.advanced_server.exception;


import com.example.advanced_server.model.CustomSuccessResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleErrors(MethodArgumentNotValidException exception) {
        var errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> ErrorCodes.findByMessage(err.getDefaultMessage()))
                .sorted()
                .distinct()
                .toList();
        return new ResponseEntity(CustomSuccessResponse.getBadResponse(errors, errors.get(0)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleErrors(ConstraintViolationException exception) {
        var errors = exception.getConstraintViolations()
                .stream()
                .map(err -> ErrorCodes.findByMessage(err.getMessage()))
                .toList();
        return new ResponseEntity(CustomSuccessResponse.getBadResponse(errors, errors.get(0)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleErrors(HttpMessageNotReadableException exception) {
        var errors = ValidationConstants.HTTP_MESSAGE_NOT_READABLE_EXCEPTION;
        List<Integer> list = new ArrayList<>();
        list.add(ErrorCodes.findByMessage(errors));
        return new ResponseEntity(CustomSuccessResponse.getBadResponse(list, ErrorCodes.findByMessage(errors)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleErrors(NoSuchElementException exception) {
        var errors = ValidationConstants.TASK_NOT_FOUND;
        List<Integer> list = new ArrayList<>();
        list.add(ErrorCodes.findByMessage(errors));
        return new ResponseEntity(CustomSuccessResponse.getBadResponse(list, ErrorCodes.findByMessage(errors)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleErrors(IllegalArgumentException exception) {
        var errors = ValidationConstants.USER_WITH_THIS_EMAIL_ALREADY_EXIST;
        List<Integer> list = new ArrayList<>();
        list.add(ErrorCodes.findByMessage(errors));
        return new ResponseEntity(CustomSuccessResponse.getBadResponse(list, ErrorCodes.findByMessage(errors)), HttpStatus.BAD_REQUEST);
    }
}
