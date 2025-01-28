package com.example.searchstack.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor // final 키워드가 붙은 필드 변수를 모두 생성자 주입
public class ApiException extends RuntimeException{
    private final ErrorCode errorCode;
}

