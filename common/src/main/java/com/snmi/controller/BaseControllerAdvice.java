package com.snmi.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.snmi.dto.BaseResponse;
import com.snmi.exception.ConflictException;
import com.snmi.exception.ForbiddenException;
import com.snmi.exception.NotFoundException;
import com.snmi.exception.UnProcessableEntityException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.snmi.constants.SecurityGlobalConstant.TOKEN_HEADER;
import static com.snmi.constants.SecurityGlobalConstant.UNAUTHORIZED;

public class BaseControllerAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(BaseControllerAdvice.class);

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonMappingException.class)
    public BaseResponse<?> jsonMappingHandler(final Exception exception, final HttpServletRequest request) {
        LOG.info(exception.getMessage());
        return BaseResponse.build(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonEOFException.class)
    public BaseResponse<?> jsonEOFHandler(final Exception exception, final HttpServletRequest request) {
        LOG.info(exception.getMessage());
        return BaseResponse.build(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonParseException.class)
    public BaseResponse<?> jsonParseHandler(final Exception exception, final HttpServletRequest request) {
        LOG.info(exception.getMessage());
        return BaseResponse.build(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseResponse<?> methodArgumentMismatchHandler(final Exception exception, final HttpServletRequest request) {
        LOG.info(exception.getMessage());
        return BaseResponse.build(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public BaseResponse<?> invalidFormatHandler(final Exception exception, final HttpServletRequest request) {
        LOG.info(exception.getMessage());
        return BaseResponse.build(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse<?> constraintViolationHandler(final Exception exception, final HttpServletRequest request) {
        LOG.info(exception.getMessage());
        return BaseResponse.build(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<?> methodArgumentNotValidHandler(final Exception exception, final HttpServletRequest request) {
        LOG.info(exception.getMessage());
        return BaseResponse.build(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnProcessableEntityException.class)
    public BaseResponse<?> unProcessableEntityHandler(final Exception exception, final HttpServletRequest request) {
        LOG.info(exception.getMessage());
        return BaseResponse.build(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public BaseResponse<?> conflictHandler(final Exception exception, final HttpServletRequest request) {
        LOG.info(exception.getMessage());
        return BaseResponse.build(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public BaseResponse<?> unauthorizedHandler(final Exception exception, final HttpServletRequest request) {
        LOG.info(exception.getMessage());
        return BaseResponse.build(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public BaseResponse<?> expiredTokenHandler(final Exception exception, final HttpServletRequest request) {
        LOG.info(exception.getMessage());
        return BaseResponse.build(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public BaseResponse<?> missingHeaderHandler(final Exception exception, final HttpServletRequest request) {
        LOG.info(exception.getMessage());

        if (exception.getMessage().contains(TOKEN_HEADER)) {
            return BaseResponse.build(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }

        return BaseResponse.build(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MalformedJwtException.class)
    public BaseResponse<?> invalidToken(final Exception exception, final HttpServletRequest request) {
        LOG.info(exception.getMessage());
        return BaseResponse.build(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public BaseResponse<?> forbiddenHandler(final Exception exception, final HttpServletRequest request) {
        LOG.info(exception.getMessage());
        return BaseResponse.build(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public BaseResponse<?> notFoundHandler(final Exception exception, final HttpServletRequest request) {
        LOG.info(exception.getMessage());
        return BaseResponse.build(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
