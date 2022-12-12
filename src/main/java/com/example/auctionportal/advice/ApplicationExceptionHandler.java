package com.example.auctionportal.advice;

import com.example.auctionportal.exceptions.FileManagerException;
import com.example.auctionportal.exceptions.InvalidFileFormatException;
import com.example.auctionportal.exceptions.NoFileFoundException;
import com.example.auctionportal.exceptions.UserRegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArguments(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(org.springframework.validation.BindException.class)
    public Map<String, String> handleInvalidFormData(org.springframework.validation.BindException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(UserRegistrationException.class)
    public Map<String, String> handleInvalidRegistration(UserRegistrationException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("email", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(InvalidFileFormatException.class)
    public Map<String, String> handleInvalidFileFormats(InvalidFileFormatException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(FileManagerException.class)
    public Map<String, String> handleFileSavingErrors(FileManagerException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(NoFileFoundException.class)
    public Map<String, String> handleNoFileErrors(NoFileFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }
}
