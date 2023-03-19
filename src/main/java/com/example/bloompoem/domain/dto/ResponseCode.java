package com.example.bloompoem.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Getter
public enum ResponseCode {
    /* 200 OK : 요청 완료 응답 */
    SUCCESSFUL(OK, "정상적으로 요청되었습니다"),
    SEND_OTP_MAIL(OK, "정상적으로 요청되었습니다"),

    /* 201 CREATE : 요청 완료 응답 */
    CREATE(CREATED,"정상적으로 작성되었습니다"),

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    CANNOT_FOLLOW_MYSELF(BAD_REQUEST, "자기 자신은 팔로우 할 수 없습니다"),
    MISSING_PARAMETER_VALUE(BAD_REQUEST, "파라미터 값이 없습니다"),
    INVALID_REQUEST(BAD_REQUEST, "올바르지 않은 요청입니다"),
    INVALID_OTP(BAD_REQUEST, "OTP가 일치하지 않습니다"),
    ALREADY_LOG_IN(BAD_REQUEST, "이미 로그인 중입니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
    ACCOUNT_MISMATCH(UNAUTHORIZED, "로그인 하는 사용자와 요청 사용자가 일치 하지 않습니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),
    NOT_FOLLOW(NOT_FOUND, "팔로우 중이지 않습니다"),
    NOT_FOUND_ORDER(NOT_FOUND, "해당하는 오더를 찾을 수 없습니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    DUPLICATE_EMAIL(CONFLICT, "해당하는 이메일은 이미 존재합니다"),

    ;

    private final HttpStatus httpStatus;
    private final String detail;
}