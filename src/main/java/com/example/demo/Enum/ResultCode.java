package com.example.demo.Enum;

import lombok.Data;

/**
 * @author Huxiaoxue
 * @version V1.0
 * @ClassName
 * @desc:
 * @create: 2019-05-24
 **/

public enum  ResultCode {
    System_error("-1","系统繁忙！");

    private String errCode;
    private String errMsg;

    ResultCode(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }


}
