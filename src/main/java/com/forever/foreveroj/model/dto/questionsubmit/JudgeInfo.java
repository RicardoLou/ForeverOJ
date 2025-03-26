package com.forever.foreveroj.model.dto.questionsubmit;

import lombok.Data;

@Data
public class JudgeInfo {
    /**
     * 程序执行信息
     */
    private String message;
    /**
     * 程序消耗内存（KB）
     */
    private Long memory;
    /**
     * 程序消耗时间（ms）
     */
    private Long time;
}
