package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackDeleteCallBackRequest;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptor;
import com.dingtalk.oapi.lib.aes.Utils;
import com.example.demo.config.Constants;
import com.example.demo.config.URLConstants;
import com.example.demo.util.AccessTokenUtil;
import com.example.demo.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Huxiaoxue
 * @version V1.0
 * @ClassName
 * @desc: 审批回调
 * @create: 2019-05-24
 **/
@RestController
@RequestMapping("/callback")
@Slf4j
public class CallBackController {


    /**
     * 创建套件后，验证回调URL创建有效事件（第一次保存回调URL之前）
     */
    private static final String CHECK_URL = "check_url";

    /**
     * 审批任务回调
     */
    private static final String BPMS_TASK_CHANGE = "bpms_task_change";

    /**
     * 审批实例回调
     */
    private static final String BPMS_INSTANCE_CHANGE = "bpms_instance_change";

    /**
     * 相应钉钉回调时的值
     */
    private static final String CALLBACK_RESPONSE_SUCCESS = "success";

    public Map<String, String> callback(@RequestParam(value = "signature", required = false) String signature,
                                        @RequestParam(value = "timestamp", required = false) String timestamp,
                                        @RequestParam(value = "nonce", required = false) String nonce,
                                        @RequestBody(required = false) JSONObject json) {
        String params = " signature:"+signature + " timestamp:"+timestamp +" nonce:"+nonce+" json:"+json;
        try {
            DingTalkEncryptor dingTalkEncryptor = new DingTalkEncryptor(Constants.TOKEN, Constants.ENCODING_AES_KEY,
                    Constants.CORP_ID);

            //从post请求的body中获取回调信息的加密数据进行解密处理
            String encryptMsg = json.getString("encrypt");
            String plainText = dingTalkEncryptor.getDecryptMsg(signature, timestamp, nonce, encryptMsg);
            JSONObject obj = JSON.parseObject(plainText);

            //根据回调数据类型做不同的业务处理
            String eventType = obj.getString("EventType");
            if (BPMS_TASK_CHANGE.equals(eventType)) {
                log.info("收到审批任务进度更新: " + plainText);
                //TODO 审批的业务逻辑
            } else if (BPMS_INSTANCE_CHANGE.equals(eventType)) {
                log.info("收到审批实例状态更新: " + plainText);
                String processInstanceId = obj.getString("processInstanceId");
                //TODO
                if (obj.containsKey("result") && obj.getString("result").equals("agree")) {
                    MessageUtil.sendMessage(processInstanceId);
                }
            } else {
                // 其他类型事件处理
            }

            // 返回success的加密信息表示回调处理成功
            return dingTalkEncryptor.getEncryptedMap(CALLBACK_RESPONSE_SUCCESS, System.currentTimeMillis(), Utils.getRandomStr(8));
        } catch (Exception e) {
            //失败的情况，应用的开发者应该通过告警感知，并干预修复
            log.error("process callback failed！"+params,e);
            return null;
        }

    }


    public static void main(String[] args) throws Exception{
        // 先删除企业已有的回调
        DingTalkClient client = new DefaultDingTalkClient(URLConstants.Callback.DELETE_CALLBACK);
        OapiCallBackDeleteCallBackRequest request = new OapiCallBackDeleteCallBackRequest();
        request.setHttpMethod("GET");
        client.execute(request, AccessTokenUtil.getToken());

        // 重新为企业注册回调
        client = new DefaultDingTalkClient(URLConstants.Callback.REGISTER_CALLBACK);
        OapiCallBackRegisterCallBackRequest registerRequest = new OapiCallBackRegisterCallBackRequest();
        registerRequest.setUrl(Constants.CALLBACK_URL_HOST + "/callback");
        registerRequest.setAesKey(Constants.ENCODING_AES_KEY);
        registerRequest.setToken(Constants.TOKEN);
        registerRequest.setCallBackTag(Arrays.asList("bpms_instance_change", "bpms_task_change"));
        OapiCallBackRegisterCallBackResponse registerResponse = client.execute(registerRequest,AccessTokenUtil.getToken());
        if (registerResponse.isSuccess()) {
            System.out.println("回调注册成功了！！！===========");
        }
    }
}
