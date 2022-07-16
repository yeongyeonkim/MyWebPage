package com.kyy.api.service.model.response;

import lombok.Data;
import java.util.List;

/*
    [결과가 여러건인 api를 담는 모델]
    어떤 형태의 List 값도 넣을 수 있도록 구현
 */
@Data
public class ListResult<T> extends CommonResult {
    private List<T> list;
}
