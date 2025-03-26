package com.forever.foreveroj.judge.codesandbox.model;

import com.forever.foreveroj.model.dto.questionsubmit.JudgeInfo;

public class ExecuteCodeResponse {
    /**
     * 接口信息
     */
    private String message;
    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;
}
