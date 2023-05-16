package com.soloproject.community.advice;

import com.soloproject.community.Exception.BusinessLogicException;
import com.soloproject.community.Exception.ExceptionCode;
import com.soloproject.community.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<?> handleBusinessLogicException(BusinessLogicException e) {

        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode()
                .getStatus()));
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(
            MaxUploadSizeExceededException e) {

        log.info("handleMaxUploadSizeExceededException", e);

        ErrorResponse response = ErrorResponse.of(ExceptionCode.FILE_SIZE_EXCEED);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
