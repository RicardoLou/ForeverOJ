package com.forever.foreveroj.judge.codesandbox.impl;

import com.forever.foreveroj.judge.codesandbox.CodeSandbox;
import com.forever.foreveroj.judge.codesandbox.model.ExecuteCodeRequest;
import com.forever.foreveroj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 示例代码沙箱
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeResponse) {
        System.out.println("示例代码沙箱");
        return null;
    }
}
