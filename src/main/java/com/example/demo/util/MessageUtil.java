package com.example.demo.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiProcessinstanceGetRequest;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.example.demo.config.Constants;
import com.example.demo.config.URLConstants;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;

/**
 * @author Huxiaoxue
 * @version V1.0
 * @ClassName
 * @desc: 发送消息
 * @create: 2019-05-24
 **/
@Slf4j
public class MessageUtil {

    /**
     * 发送消息
     * @param processInstanceId 审批实例id
     */
    public static void sendMessage(String processInstanceId) {
        try {
            // PROCESSINSTANCE_GET 获取审批实例的接口url
            DingTalkClient client = new DefaultDingTalkClient(URLConstants.Process.PROCESSINSTANCE_GET);
            //审批获取实例请求
            OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
            request.setProcessInstanceId(processInstanceId);
            //执行
            OapiProcessinstanceGetResponse response = client.execute(request, AccessTokenUtil.getToken());

            //获取审批接收人
            String originatorUserid = response.getProcessInstance().getOriginatorUserid();


            //发送消息的Client
            client = new DefaultDingTalkClient(URLConstants.DingMessage.MESSAGE_ASYNCSEND);

            //发送消息请求
            OapiMessageCorpconversationAsyncsendV2Request messageRequest = new OapiMessageCorpconversationAsyncsendV2Request();
            //接收人
            messageRequest.setUseridList(originatorUserid);
            messageRequest.setAgentId(Constants.AGENTID);
            messageRequest.setToAllUser(false);

            //消息内容实体
            OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
            msg.setMsgtype("text");
            msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
            msg.getText().setContent("出差申请通过了，快去订机票吧");
            messageRequest.setMsg(msg);

            //进行发送
            client.execute(messageRequest,AccessTokenUtil.getToken());

        }catch (ApiException e){
            log.error("send message failed: "+e);
            throw new RuntimeException();
        }

    }
}
