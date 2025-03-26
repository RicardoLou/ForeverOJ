package com.forever.foreveroj.judge.codesandbox;

import com.forever.foreveroj.judge.codesandbox.model.ExecuteCodeRequest;
import com.forever.foreveroj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱的定义
 *
 */
public interface CodeSandbox {
    /**
     * 执行代码
     *
     * @param executeCodeResponse
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeResponse);
}
