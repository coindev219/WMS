package com.warehouse.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DuplicateValueFoundException.class)
  public ResponseEntity<ExceptionResponse> handleException(DuplicateValueFoundException ine,WebRequest request){
      ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ine.getMessage(),request.getDescription(false));
      return new ResponseEntity<ExceptionResponse>(exceptionResponse,HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidValueFoundException.class)
  public ResponseEntity<ExceptionResponse> handleException(InvalidValueFoundException ine,WebRequest request){
      ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ine.getMessage(),request.getDescription(false));
      return new ResponseEntity<ExceptionResponse>(exceptionResponse,HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
    ex.printStackTrace();
    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
  } 
  
  /*@ExceptionHandler(StudentNotFoundException.class)
  public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(StudentNotFoundException ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }*/

}