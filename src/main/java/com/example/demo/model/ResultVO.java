package com.example.demo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Huxiaoxue
 * @version V1.0
 * @ClassName
 * @desc:
 * @create: 2019-05-24
 **/
@Data
public class ResultVO<T> implements Serializable{

    private String code;

    private String message;

    private T result;

    private boolean success = false;

    public ResultVO() {
    }

    public static <T> ResultVO<T> success(T result){
        ResultVO<T> resultVO = new ResultVO<T>();
        resultVO.success = true;
        resultVO.code = "0";
        resultVO.message = "success";
        resultVO.result = result;
        return resultVO;
    }

    public static  <T> ResultVO<T> failure(String errorCode,String errorMessage){
        ResultVO resultVO = new ResultVO();
        resultVO.code = errorCode;
        resultVO.message = errorMessage;
        resultVO.success = false;
        return resultVO;
    }


    public static  <T> ResultVO<T> failure(String errorCode){
        ResultVO resultVO = new ResultVO();
        resultVO.code = errorCode;
        resultVO.message = "failure";
        resultVO.success = false;
        return resultVO;
    }

}
