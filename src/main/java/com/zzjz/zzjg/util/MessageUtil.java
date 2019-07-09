package com.zzjz.zzjg.util;

import com.zzjz.zzjg.bean.BaseResponse;
import com.zzjz.zzjg.bean.ResultCode;

/**
 * @author 房桂堂
 * @description MessageUtil
 * @date 2019/7/5 14:45
 */
public class MessageUtil {

    /**
     * 请求成功返回.
     * @param object 数据
     * @param msg 返回信息
     * @return
     */
    public static BaseResponse success(String msg, Object object) {
        BaseResponse response = new BaseResponse();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage(msg);
        response.setObj(object);
        return response;
    }

    public static BaseResponse success(String msg) {
        BaseResponse response = new BaseResponse();
        response.setResultCode(ResultCode.RESULT_SUCCESS);
        response.setMessage(msg);
        return response;
    }

    /**
     * 请求失败返回.
     * @param msg 返回信息
     * @return 结果
     */
    public static BaseResponse error(String msg) {
        BaseResponse response = new BaseResponse();
        response.setResultCode(ResultCode.RESULT_ERROR);
        response.setMessage(msg);
        return response;
    }

}
