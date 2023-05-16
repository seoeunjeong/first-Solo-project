package com.soloproject.community.Exception;

import lombok.Getter;

public enum ExceptionCode {

    MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다."),
    MEMBER_EXISTS(409, "회원이 이미 존재합니다."),
    ARTICLE_NOT_FOUND(404, "게시글을 찾을 수 없습니다.."),

    COMMENT_NOT_FOUND(404, "댓글을 찾을 수 없습니다.."),
    FILE_SIZE_EXCEED(404, "파일사이즈를 초과하였습니다");
    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }

}
