package com.patryk.shop.controller;

import com.patryk.shop.domain.dto.FieldErrorDto;
import com.patryk.shop.exception.QuantityExceededException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class AdviceController {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleEntityNotFound(EntityNotFoundException e) {
        log.error(e.getMessage(), e);
    }

    @ExceptionHandler(QuantityExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleQuantityExceededException(QuantityExceededException e) { log.error(e.getMessage(), e);}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<FieldErrorDto> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return e.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> {
                    if(objectError instanceof FieldError) {
                        FieldError fieldError = (FieldError) objectError;
                        return new FieldErrorDto(fieldError.getField(), fieldError.getDefaultMessage());
                    }
                    return new FieldErrorDto(objectError.getDefaultMessage());


                })
                .collect(Collectors.toList());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleEntityNotFound(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        log.error(e.getMessage(), e);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleEmptyResultDataAccessException(BadCredentialsException e) {
        log.error(e.getMessage(), e);
    }
}
