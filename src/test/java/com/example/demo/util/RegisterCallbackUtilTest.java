package com.example.demo.util;

import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by 20190301 on 2019/5/31.
 */
@Slf4j
public class RegisterCallbackUtilTest {

    @Test
    public void test() throws ApiException {
        RegisterCallbackUtil.registerUrlCallBack();
    }

}