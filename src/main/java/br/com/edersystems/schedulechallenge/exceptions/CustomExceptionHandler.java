/*
Project .....................: schedule-challenge
Creation Date ...............: 22/08/2020 17:38:01
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public CustomExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpStatus status) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        GenericError error = new GenericError(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), errorMessage);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handlerException(Exception ex) {
        final String errorMessage = messageSource.getMessage(NestedExceptionUtils.getMostSpecificCause(ex).getLocalizedMessage(), null, Locale.ENGLISH);
        return new ResponseEntity<>(new GenericError(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), errorMessage), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        return new ResponseEntity<>(new GenericError(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getMessage()), new org.springframework.http.HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handleConflict(DataIntegrityViolationException ex) {

        String message = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();

        return new ResponseEntity<>(new GenericError(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), message), new org.springframework.http.HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handlePersistenceException(final DataIntegrityViolationException ex) {
        Throwable cause = ex.getRootCause();

        if(cause instanceof SQLIntegrityConstraintViolationException) {
            SQLIntegrityConstraintViolationException consEx = (SQLIntegrityConstraintViolationException) cause;
            return new ResponseEntity<>(new GenericError(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new GenericError(String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({TransactionSystemException.class})
    public ResponseEntity<?> handleConflict(TransactionSystemException ex) {
        Throwable cause = ex.getRootCause();
        String message = NestedExceptionUtils.getMostSpecificCause(ex).getLocalizedMessage();
        if(cause instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) cause).getConstraintViolations();
            message = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
            message = messageSource.getMessage(message, null, Locale.ENGLISH);
        }
        return new ResponseEntity<>(new GenericError(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), message), new org.springframework.http.HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> handleNotFound(NotFoundException ex) {

        String message = messageSource.getMessage(NestedExceptionUtils.getMostSpecificCause(ex).getMessage(), null, Locale.ENGLISH);

        return new ResponseEntity<>(new GenericError(String.valueOf(HttpStatus.NOT_FOUND.value()), message), new org.springframework.http.HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UnProcessableEntityException.class})
    public ResponseEntity<?> handleUnProcessableEntity(UnProcessableEntityException ex) {

        String message = messageSource.getMessage(NestedExceptionUtils.getMostSpecificCause(ex).getMessage(), null, Locale.ENGLISH);

        return new ResponseEntity<>(new GenericError(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), message), new org.springframework.http.HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
