package com.example.searchstack.config.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private String errorMessage;
    private int status;

    private ErrorResponse(final ErrorCode errorCode) {
        this.errorMessage = errorCode.getErrorMessage();
        this.status = errorCode.getHttpStatus().value();
    }

    // 정적 팩토리 메서드 패턴
    // 객체 생성을 캡슐화할 수 있고, 나중에 인스턴스를 생성하는 방식을 변경할 때도 기존 코드를 변경하지 않고 할 수 있다는 장점이 있다.
    // 찾아본 결과, 네이밍 규칙에 어긋나 "from"으로 짓는 게 더 정확하다.
    public static ErrorResponse of(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }
}
