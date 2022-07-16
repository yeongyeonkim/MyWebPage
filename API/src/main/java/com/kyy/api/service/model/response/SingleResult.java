package com.kyy.api.service.model.response;

import lombok.Data;

/*
    [결과가 단일건인 api를 담는 모델]
    Generic Interface에 <T>를 지정하여 어떤 형태의 값도 넣을 수 있도록 구현
 */
@Data
public class SingleResult<T> extends CommonResult {
    private T data;
}
