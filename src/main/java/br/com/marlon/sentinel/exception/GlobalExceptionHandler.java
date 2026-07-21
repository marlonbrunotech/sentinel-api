package br.com.marlon.sentinel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidationException(
        MethodArgumentNotValidException exception){

        Map<String, String> fieldErrors = new HashMap<>();

        for (FieldError error: exception.getBindingResult().getFieldErrors()){
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        return new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "Validation error",
                fieldErrors);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleInvalidRequestBody(HttpMessageNotReadableException exception){

        return new ApiErrorResponse( HttpStatus.BAD_REQUEST.value(),"Invalid request body");


    }

    @ExceptionHandler(AssetNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFoundException (AssetNotFoundException exception){

     return new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleGenericException (Exception exception){

       return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
               "An unexpected error occurred.");

    }


}
