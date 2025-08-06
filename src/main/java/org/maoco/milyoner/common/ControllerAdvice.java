package org.maoco.milyoner.common;


import org.hibernate.service.spi.ServiceException;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.question.service.exception.CreateAnswerException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> handleNotFoundException(NotFoundException e) {

        return ApiResponse.failed(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CreateAnswerException.class)
    public ApiResponse<String> handleCreateAnswerException(CreateAnswerException e) {

        return ApiResponse.failed(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleMethodArgumentNotValidExceptionException(MethodArgumentNotValidException e) {

        String message = getValidationMessage(e.getBindingResult());

        return ApiResponse.failed(message, HttpStatus.BAD_REQUEST);
    }

    private String getValidationMessage(BindingResult result) {
        List<ObjectError> objects = result.getAllErrors();
        ObjectError error = objects.get(0);
        return error.getDefaultMessage();
    }
}
