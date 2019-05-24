package com.example.demo.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.example.demo.Enum.ResultCode;
import com.example.demo.config.Constants;
import com.example.demo.config.URLConstants;
import com.example.demo.model.ProcessInstanceVo;
import com.example.demo.model.ResultVO;
import com.example.demo.util.AccessTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Huxiaoxue
 * @version V1.0
 * @ClassName
 * @desc: 审批
 * @create: 2019-05-24
 **/
@RestController
@RequestMapping("/process")
@Slf4j
public class ApproveController {

    /**
     * 发起审批实例
     * @param processInstanceVo
     * @return
     */
    @PostMapping("/processInstance/start")
    public ResultVO startProcess(@RequestBody ProcessInstanceVo processInstanceVo){

        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(URLConstants.Process.PROCESSINSTANCE_START);
            OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
            request.setProcessCode(Constants.PROCESS_CODE);

            //审批的内容
            request.setFormComponentValues(processInstanceVo.generateForms());
            request.setOriginatorUserId(processInstanceVo.getOriginatorUserId());
            request.setDeptId(processInstanceVo.getDeptId());
            request.setCcList(processInstanceVo.getOriginatorUserId());
            request.setCcPosition("FINISH");

            OapiProcessinstanceCreateResponse response = client.execute(request, AccessTokenUtil.getToken());
            if(response.getErrcode().longValue() != 0){
                return ResultVO.failure(String.valueOf(response.getErrcode()),String.valueOf(response.getErrmsg()));
            }
            return  ResultVO.success(response.getProcessInstanceId());

        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            return ResultVO.failure(ResultCode.System_error.getErrCode(),ResultCode.System_error.getErrMsg());
        }
    }




}
