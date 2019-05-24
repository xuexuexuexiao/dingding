package com.example.demo.config;

/**
 * @author Huxiaoxue
 * @version V1.0
 * @ClassName
 * @desc: 常量Url
 * @create: 2019-05-24
 **/
public class URLConstants {
    /**
     * 钉钉网关gettoken地址
     */
    public static final String URL_GET_TOKKEN = "https://oapi.dingtalk.com/gettoken";

    /**
     * user 相关
     */
    public interface User{

        /**
         * 获取用户所在的部门接口url
         */
        static final String GET_USERID_LIST = "https://oapi.dingtalk.com/user/getDeptMember";

        /**
         * 获取企业内用户姓名的接口url
         */
        static  final String  USER_GET = "https://oapi.dingtalk.com/user/get";

        /**
         * 获取用户在企业内userId的接口URL
         */
        static final String GET_USER_INFO = "https://oapi.dingtalk.com/user/getuserinfo";


    }

    /**
     * 审批相关
     */
    public interface Process{

        /**
         * 发起审批实例的接口url
         */
        static final String PROCESSINSTANCE_START = "https://oapi.dingtalk.com/topapi/processinstance/create";

        /**
         * 获取审批实例的接口url
         */
        static final String PROCESSINSTANCE_GET = "https://oapi.dingtalk.com/topapi/processinstance/get";

    }

    /**
     * 部门相关
     */
    public interface Department{
        /**
         * 获取部门列表url
         */
        static final String GET_DEPARTMENT_LIST  = "https://oapi.dingtalk.com/department/list";
    }

    /**
     * 消息相关
     */
    public interface DingMessage{
        /**
         * 发送企业通知消息的接口url
         */
        static final String MESSAGE_ASYNCSEND = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";
    }

    /**
     * 回调相关
     */
    public interface Callback{

        /**
         * 删除企业回调接口url
         */
         static final String DELETE_CALLBACK = "https://oapi.dingtalk.com/call_back/delete_call_back";

        /**
         * 注册企业回调接口url
         */
        static final String REGISTER_CALLBACK = "https://oapi.dingtalk.com/call_back/register_call_back";

    }
}
