package co.simplon.dev2dev_business.controllers.errors;

import co.simplon.dev2dev_business.dtos.CustomErrorResponse;
import co.simplon.dev2dev_business.exceptions.ArticleShareLinkException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {
    @Override //handle valid dto inputs
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

//        final Map<String, String> errors = new HashMap<>();
//        for (FieldError error : ex.getFieldErrors()) {
//            errors.put(error.getField(), error.getDefaultMessage());
//        }
//        return handleExceptionInternal(ex, errors, headers, status, request);
        final CustomErrorResponse customErrorResponse = new CustomErrorResponse();
        final Map<String, ArrayList<String>> fieldErrors = new HashMap<>();
        final ArrayList<String> codes = new ArrayList<>();
        for (FieldError error : ex.getFieldErrors()) {
            codes.add(error.getCode());
            //System.out.println(error.getCode());
            fieldErrors.put(error.getField(), codes);
        }
        customErrorResponse.setFieldErrors(fieldErrors);
        return handleExceptionInternal(ex, customErrorResponse, headers, status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class) // handle valid have not title article
    public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex) {
	final Map<String, String> errors = new HashMap<>();
	ex.getConstraintViolations().forEach(error -> {
	    final String fieldName = error.getPropertyPath().toString();
	    final String errorMessage = error.getMessage();
	    errors.put(fieldName, errorMessage);
	});
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ArticleShareLinkException.class) //handle can not access url
    public ResponseEntity<Object> handleInvalidUrlException(ArticleShareLinkException ex){
        final Map<String, String> errors = new HashMap<>();
        errors.put("link",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<Object> handleIOException(IOException ex) {
        return new ResponseEntity<>(ex.getMessage(),new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(),new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {

        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
}

