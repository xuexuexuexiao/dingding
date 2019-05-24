package com.example.demo.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.example.demo.config.URLConstants;
import com.example.demo.model.ResultVO;
import com.example.demo.util.AccessTokenUtil;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Huxiaoxue
 * @version
 * @ClassName
 * @desc:
 * @create: 2019-05-24
 **/
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    public ResultVO login(@RequestParam(value = "authCode") String requestAuthCOde)  {

        //获取accessCode
        String token = null;
        try {
            token = AccessTokenUtil.getToken();
        } catch (ApiException e) {
            e.printStackTrace();
        }

        //获取用户信息
        DingTalkClient client = new DefaultDingTalkClient(URLConstants.User.GET_USER_INFO);
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(requestAuthCOde);
        request.setHttpMethod("GET");

        OapiUserGetuserinfoResponse response;
        try {
            response = client.execute(request, token);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
        //查询得到当前用户id
        String userid = response.getUserid();
        OapiUserGetResponse userProfile = getUserProfile(token, userid);
        String name = userProfile.getName();
        Long deptId = userProfile.getDepartment().get(0);
        //审批部门里面的id 1和-1要互换一下
        if(deptId.longValue() == 1L){
            deptId = -1L;
        }
        //返回结果
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("userId",userid);
        resultMap.put("userName",name);
        resultMap.put("deptId",deptId);
        ResultVO resultVO = ResultVO.success(resultMap);
        return resultVO;
    }

    /**
     * 获取用户详情
     * @param accessToken
     * @param userId
     * @return
     */
    private OapiUserGetResponse getUserProfile(String accessToken, String userId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(URLConstants.User.USER_GET);
            OapiUserGetRequest request = new OapiUserGetRequest();
            request.setUserid(userId);
            request.setHttpMethod("GET");
            OapiUserGetResponse response = client.execute(request, accessToken);

            return response;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}
