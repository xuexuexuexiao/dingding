package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackDeleteCallBackRequest;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.example.demo.config.Constants;
import com.example.demo.config.URLConstants;
import com.taobao.api.ApiException;

import java.util.Arrays;

/**
 * @author Huxiaoxue
 * @version V1.0
 * @ClassName
 * @desc: 注册回调接口
 * @create: 2019-05-31
 **/
public class RegisterCallbackUtil {


    /**
     * 注册用户员工回调接口
     */
    public static void registerUrlCallBack() throws ApiException {

        /*DingTalkClient client = new DefaultDingTalkClient(URLConstants.Callback.DELETE_CALLBACK);
        OapiCallBackDeleteCallBackRequest request = new OapiCallBackDeleteCallBackRequest();
        request.setHttpMethod("GET");
        client.execute(request, AccessTokenUtil.getToken());
*/
        //注册回调接口的API
        DingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/register_call_back      ");
        //注册回调请求Request
        OapiCallBackRegisterCallBackRequest registerRequest = new OapiCallBackRegisterCallBackRequest();
        //接收回调事件的url: 这个url采用钉钉的内网穿透策略
        registerRequest.setUrl(URLConstants.TMP_EVENT_RECEIVE.URL_USER_RECEIVE+"/user");
        registerRequest.setHttpMethod("POST");
        //43个字符，可随机生成
        registerRequest.setAesKey("1234567890123456789012345678901234567890123");
        //可随机填写 或者随机生成
        registerRequest.setToken("123456");
        registerRequest.setCallBackTag(Arrays.asList("user_add_org", "user_modify_org", "user_leave_org"));
        OapiCallBackRegisterCallBackResponse response = client.execute(registerRequest, AccessTokenUtil.getToken());
        JSON.toJSONString(response);
    }
}
