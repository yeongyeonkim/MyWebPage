package com.kyy.api.service.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
    [api 실행 결과를 담는 모델]
 */
@Data
public class CommonResult {
    @ApiModelProperty(value = "응답 성공 여부 : true")
    private boolean success;
    
    @ApiModelProperty(value = "응답 메시지")
    private String msg;

}
