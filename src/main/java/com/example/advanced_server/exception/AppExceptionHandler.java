package com.example.advanced_server.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import com.example.advanced_server.dto.CustomSuccessResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handle(MethodArgumentNotValidException e) {
        List<Integer> codes = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(element -> ErrorCodes.findByMessage(element.getDefaultMessage()))
                .toList();
        return new ResponseEntity(CustomSuccessResponse.getBadResponse(codes, codes.get(0)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handle(ConstraintViolationException e) {
        List<Integer> codes = e.getConstraintViolations()
                .stream()
                .map(element -> ErrorCodes.findByMessage(element.getMessage()))
                .toList();
        return new ResponseEntity(CustomSuccessResponse.getBadResponse(codes, codes.get(0)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handle(HttpMessageNotReadableException e) {
        List<Integer> codes = new ArrayList<>();
        codes.add(ErrorCodes.findByMessage(ValidationConstants.HTTP_MESSAGE_NOT_READABLE_EXCEPTION));
        return new ResponseEntity(CustomSuccessResponse.getBadResponse(codes, codes.get(0)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity handle(CustomException e) {
        List<Integer> codes = new ArrayList<>();
        codes.add(ErrorCodes.findByMessage(e.getMessage()));
        return new ResponseEntity(CustomSuccessResponse.getBadResponse(codes, codes.get(0)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleErrors(IllegalArgumentException exception) {
        var errors = ValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED;
        List<Integer> list = new ArrayList<>();
        list.add(ErrorCodes.findByMessage(errors));
        return new ResponseEntity(CustomSuccessResponse.getBadResponse(
                list, ErrorCodes.findByMessage(errors)), HttpStatus.BAD_REQUEST);
    }
}
