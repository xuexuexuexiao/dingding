package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Huxiaoxue
 * @version V1.0
 * @ClassName
 * @desc:
 * @create: 2019-05-31
 **/
@RestController
@RequestMapping("/callBack")
public class OrgCallBackController {


    /**
     * 用户回调接口
     */
    @GetMapping("/userCallBack")
    public void userCallBack(){
        
    }

}
