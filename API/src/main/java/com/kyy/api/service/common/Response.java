package com.kyy.api.service.common;

public enum Response {
    SUCCESS("성공"),
    FAIL("실패");
    private final String value;

    Response(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
