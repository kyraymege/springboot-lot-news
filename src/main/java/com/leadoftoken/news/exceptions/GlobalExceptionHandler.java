package com.leadoftoken.news.exceptions;

import com.leadoftoken.news.entities.dtos.ErrorDetailsDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                           WebRequest webRequest){
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NewsApiException.class)
    public ResponseEntity<ErrorDetailsDto> handleBlogAPIException(NewsApiException exception,
                                                                  WebRequest webRequest){
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    // global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDto> handleGlobalException(Exception exception,
                                                                 WebRequest webRequest){
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetailsDto> handleAccessDeniedException(AccessDeniedException exception,
                                                                       WebRequest webRequest){
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }
}
