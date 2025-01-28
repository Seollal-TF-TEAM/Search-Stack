package com.example.searchstack.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 상태코드
    CONFLICT_REQUEST_ERROR(HttpStatus.CONFLICT, "동일한 요청입니다."),
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다."),

    // 500 상태코드
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다.");

    private HttpStatus httpStatus;
    private String errorMessage;
}
