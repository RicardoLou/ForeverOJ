package com.forever.foreveroj.judge;

import com.forever.foreveroj.judge.strategy.DefaultJudgeStrategy;
import com.forever.foreveroj.judge.strategy.JavaLanguageJudgeStrategy;
import com.forever.foreveroj.judge.strategy.JudgeContext;
import com.forever.foreveroj.judge.strategy.JudgeStrategy;
import com.forever.foreveroj.model.dto.questionsubmit.JudgeInfo;
import com.forever.foreveroj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("Java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
