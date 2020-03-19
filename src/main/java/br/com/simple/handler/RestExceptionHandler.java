package br.com.simple.handler;

import br.com.simple.error.ResourceNotFoundDetails;
import br.com.simple.error.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfExcception){
        ResourceNotFoundDetails rnfDetails = ResourceNotFoundDetails.Builder
                                                                    .newBuilder()
                                                                    .timestamp(new Date().getTime())
                                                                    .status(HttpStatus.NOT_FOUND.value())
                                                                    .title("Resource not found")
                                                                    .details(rnfExcception.getMessage())
                                                                    .developerMessage(rnfExcception.getClass().getName())
                                                                    .build();
        return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
    }
}
