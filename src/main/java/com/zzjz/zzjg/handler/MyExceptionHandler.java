package com.zzjz.zzjg.handler;

import com.zzjz.zzjg.bean.BaseResponse;
import com.zzjz.zzjg.bean.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description 统一异常处理
 * @Author 房桂堂
 * @Date 2019/8/20 14:10
 */
@ControllerAdvice
public class MyExceptionHandler {

    /**
     * 统一 Exception 异常处理.
     * @param req 请求体
     * @param exception 异常
     * @return response
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse<String> exceptionHandler(HttpServletRequest req, Exception exception) {
        BaseResponse<String> response = new BaseResponse<>();
        response.setResultCode(ResultCode.RESULT_ERROR);
        response.setMessage(exception.getMessage());
        response.setObj(req.getRequestURL().toString());
        return response;
    }
}
