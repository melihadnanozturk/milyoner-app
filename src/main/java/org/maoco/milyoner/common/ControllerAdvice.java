package org.maoco.milyoner.common;


import jakarta.servlet.http.HttpServletResponse;
import org.maoco.milyoner.common.exception.AnswerException;
import org.maoco.milyoner.common.exception.MilyonerException;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.common.exception.RateLimitException;
import org.maoco.milyoner.common.exception.WrongAnswerException;
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> handleNotFoundException(NotFoundException e) {
        return ApiResponse.failed(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CreateAnswerException.class)
    public ApiResponse<String> handleCreateAnswerException(CreateAnswerException e) {
        return ApiResponse.failed(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AnswerException.class)
    public ApiResponse<String> handleAnswerException(AnswerException e) {
        return ApiResponse.failed(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongAnswerException.class)
    public ApiResponse<String> handleWrongAnswerException(WrongAnswerException e) {
        return ApiResponse.failed(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = getValidationMessage(e.getBindingResult());
        return ApiResponse.failed(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(RateLimitException.class)
    public ApiResponse<String> handleRateLimitException(RateLimitException e, HttpServletResponse response) {
        response.setHeader("Retry-After", String.valueOf(e.getRetryAfterSeconds()));
        return ApiResponse.failed(e.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MilyonerException.class)
    public ApiResponse<String> handleMilyonerException(MilyonerException e) {
        return ApiResponse.failed(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getValidationMessage(BindingResult result) {
        List<ObjectError> objects = result.getAllErrors();
        ObjectError error = objects.get(0);
        return error.getDefaultMessage();
    }
}
