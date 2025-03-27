package com.forever.foreveroj.judge;

import com.forever.foreveroj.model.dto.questionsubmit.JudgeInfo;
import com.forever.foreveroj.model.entity.QuestionSubmit;

public interface JudgeService {
    /**
     * 判题服务
     * @param questionSubmitID
     * @return
     */
    QuestionSubmit doJudge(Long questionSubmitID);
}
