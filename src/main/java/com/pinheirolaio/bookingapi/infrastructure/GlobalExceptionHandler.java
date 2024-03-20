package com.pinheirolaio.bookingapi.infrastructure;

import com.pinheirolaio.bookingapi.infrastructure.exception.InvalidParameterException;
import com.pinheirolaio.bookingapi.infrastructure.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handle(MethodArgumentNotValidException exception) {
        exception.printStackTrace();
        List<String> errors = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        for(FieldError fieldError : fieldErrors) {

            String mensagem = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            errors.add(mensagem);
        }
        return new ResponseEntity<>(buildError(errors, HttpStatus.BAD_REQUEST)
                , HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handle(RuntimeException exception) {
        exception.printStackTrace();
        List<String> errors = new ArrayList<>();
        errors.add(exception.getMessage());
        return new ResponseEntity<>(buildError(errors, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<?> handleInvalidParameterException(InvalidParameterException exception) {
        List<String> errors = new ArrayList<>();
        errors.add(exception.getMessage());
        return new ResponseEntity<>(buildError(errors, HttpStatus.BAD_REQUEST)
                , null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException exception) {
        List<String> errors = new ArrayList<>();
        errors.add(exception.getMessage());
        return new ResponseEntity<>(buildError(errors, HttpStatus.NOT_FOUND)
                , null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        exception.printStackTrace();
        List<String> errors = new ArrayList<>();
        errors.add(exception.getMessage());
        return new ResponseEntity<>(buildError(errors, HttpStatus.INTERNAL_SERVER_ERROR)
                , null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDetail buildError(List<String> messages, HttpStatus status) {
        return new ErrorDetail.Builder()
                .timestamp(String.valueOf(new Date(System.currentTimeMillis() + TimeZone.getDefault().getRawOffset()).getTime()))
                .status(String.valueOf(status.value()))
                .message(messages)
                .build();
    }
}