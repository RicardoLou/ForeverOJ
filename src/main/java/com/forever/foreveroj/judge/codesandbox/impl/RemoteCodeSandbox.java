package com.forever.foreveroj.judge.codesandbox.impl;

import com.forever.foreveroj.judge.codesandbox.CodeSandbox;
import com.forever.foreveroj.judge.codesandbox.model.ExecuteCodeRequest;
import com.forever.foreveroj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱
 *
 */
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
