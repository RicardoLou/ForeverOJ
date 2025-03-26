package com.forever.foreveroj.judge.codesandbox;

import com.forever.foreveroj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.forever.foreveroj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.forever.foreveroj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱的工厂
 * 根据字符串参数创建代码沙箱实例
 */
public class CodeSandboxFactory {
    /**
     * 创建代码沙箱实例
     * @param type 沙箱类型
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case  "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
