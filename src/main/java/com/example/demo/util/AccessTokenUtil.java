package com.example.demo.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.example.demo.config.Constants;
import com.example.demo.config.URLConstants;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Huxiaoxue
 * @version V1.0
 * @ClassName
 * @desc: accesstoken
 * @create: 2019-05-24
 **/
@Slf4j
public class AccessTokenUtil {

    /**
     * 获取 access_token ：需要appkey 和 appSecret 得到 access_token
     * <p>
     * TODO :
     * 1、需要存到缓存里，以免每次都需要获取一次，
     * 2、token的期限是2个小时,需要定时动态获取
     * </p>
     * @return
     */
    public static String getToken() throws RuntimeException, ApiException {

        DefaultDingTalkClient client = new DefaultDingTalkClient(URLConstants.URL_GET_TOKKEN);
        OapiGettokenRequest request = new OapiGettokenRequest();

        request.setAppkey(Constants.APPKEY);
        request.setAppsecret(Constants.APPSECRET);
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        return response.getAccessToken();
    }
}
