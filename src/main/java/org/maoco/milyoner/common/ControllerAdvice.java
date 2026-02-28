package org.maoco.milyoner.common;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.maoco.milyoner.common.error.CommonError;
import org.maoco.milyoner.common.exception.AnswerException;
import org.maoco.milyoner.common.exception.MilyonerException;
import org.maoco.milyoner.common.exception.NotFoundException;
import org.maoco.milyoner.common.exception.RateLimitException;
import org.maoco.milyoner.common.exception.WrongAnswerException;
import org.maoco.milyoner.question.service.exception.CreateAnswerException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> handleNotFoundException(NotFoundException e) {
        log.info("NotFoundException occurred: {}", e.getMessage());
        return ApiResponse.error(e.getCode(), e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CreateAnswerException.class)
    public ApiResponse<String> handleCreateAnswerException(CreateAnswerException e) {
        log.warn("CreateAnswerException occurred: {}", e.getMessage());
        return ApiResponse.error(e.getCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AnswerException.class)
    public ApiResponse<String> handleAnswerException(AnswerException e) {
        log.warn("AnswerException occurred: {}", e.getMessage());
        return ApiResponse.error(e.getCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongAnswerException.class)
    public ApiResponse<String> handleWrongAnswerException(WrongAnswerException e) {
        log.warn("WrongAnswerException occurred: {}", e.getMessage());
        return ApiResponse.error(e.getCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = getValidationMessage(e.getBindingResult());
        log.warn("Validation error occurred: {}", message);
        return ApiResponse.failed(message, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(RateLimitException.class)
    public ApiResponse<String> handleRateLimitException(RateLimitException e, HttpServletResponse response) {
        log.warn("RateLimitException occurred: {}", e.getMessage());
        response.setHeader("Retry-After", String.valueOf(e.getRetryAfterSeconds()));
        return ApiResponse.failed(e.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MilyonerException.class)
    public ApiResponse<String> handleMilyonerException(MilyonerException e) {
        log.error("MilyonerException occurred: {}", e.getMessage(), e);
        return ApiResponse.error(e.getCode(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalStateException.class)
    public ApiResponse<String> handleIllegalStateException(IllegalStateException e) {
        log.error("IllegalStateException occurred: {}", e.getMessage(), e);
        return ApiResponse.error(
                CommonError.INVALID_SECURITY_CONTEXT.getCode(),
                CommonError.INVALID_SECURITY_CONTEXT.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<String> handleRuntimeException(RuntimeException e) {
        log.error("Unexpected RuntimeException occurred: {}", e.getMessage(), e);
        return ApiResponse.error(
                CommonError.INTERNAL_SERVER_ERROR.getCode(),
                CommonError.INTERNAL_SERVER_ERROR.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ApiResponse<String> handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.warn("UsernameNotFoundException occurred: {}", e.getMessage());
        return ApiResponse.failed(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataAccessException.class, ConstraintViolationException.class})
    public ApiResponse<String> handleDataAccessException(Exception e) {
        log.error("Database error occurred: {}", e.getMessage(), e);
        return ApiResponse.error(
                CommonError.DATABASE_ERROR.getCode(),
                CommonError.DATABASE_ERROR.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleGenericException(Exception e) {
        log.error("Unhandled exception occurred: {}", e.getMessage(), e);
        return ApiResponse.error(
                CommonError.INTERNAL_SERVER_ERROR.getCode(),
                CommonError.INTERNAL_SERVER_ERROR.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private String getValidationMessage(BindingResult result) {
        List<ObjectError> objects = result.getAllErrors();
        ObjectError error = objects.get(0);
        return error.getDefaultMessage();
    }
}
