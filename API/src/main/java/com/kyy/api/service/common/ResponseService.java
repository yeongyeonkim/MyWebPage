package com.kyy.api.service.common;

import com.kyy.api.service.model.response.CommonResult;
import com.kyy.api.service.model.response.ListResult;
import com.kyy.api.service.model.response.SingleResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> singleResult = new SingleResult<>();
        singleResult.setData(data);
        setSuccessResult(singleResult);
        return singleResult;
    }
    
    public <T> ListResult<T> getListResult(List<T> data) {
        ListResult<T> listResult = new ListResult<>();
        listResult.setList(data);
        setSuccessResult(listResult);
        return listResult;
    }

    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setMsg(Response.SUCCESS.getValue());
    }

}
