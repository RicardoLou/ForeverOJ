package com.forever.foreveroj.sandbox;

import com.forever.foreveroj.judge.codesandbox.CodeSandbox;
import com.forever.foreveroj.judge.codesandbox.CodeSandboxFactory;
import com.forever.foreveroj.judge.codesandbox.CodeSandboxProxy;
import com.forever.foreveroj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.forever.foreveroj.judge.codesandbox.model.ExecuteCodeRequest;
import com.forever.foreveroj.judge.codesandbox.model.ExecuteCodeResponse;
import com.forever.foreveroj.model.enums.QuestionSubmitLanguageEnum;
import org.elasticsearch.client.license.LicensesStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class codeSandboxTest {
    @Value("${codeSandbox.type:example}")
    String type;
    @Test
    void executeCode() {
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String code = "public class Main {\n" +
                "    public static void main(String[] var0) {\n" +
                "        int var1 = Integer.parseInt(var0[0]);\n" +
                "        int var2 = Integer.parseInt(var0[1]);\n" +
                "        var1 += var2;\n" +
                "        System.out.println(\"和为：\" + var1);\n" +
                "    }\n" +
                "}\n";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "7 7");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
        System.out.println(executeCodeResponse.getOutList());
    }
}
