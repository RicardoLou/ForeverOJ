package com.forever.foreveroj.judge.codesandbox.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.forever.foreveroj.common.ErrorCode;
import com.forever.foreveroj.exception.BusinessException;
import com.forever.foreveroj.judge.codesandbox.CodeSandbox;
import com.forever.foreveroj.judge.codesandbox.model.ExecuteCodeRequest;
import com.forever.foreveroj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱
 *
 */
public class RemoteCodeSandbox implements CodeSandbox {
    // 鉴权请求头
    private static final String AUTH_REQUEST_HEADER = "auth";
    // 密钥
    private static final String AUTH_REQUEST_SECRET = "1900";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://localhost:8082/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StrUtil.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR,"remote sandbox error, message " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
