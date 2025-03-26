package com.forever.foreveroj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forever.foreveroj.model.dto.question.QuestionQueryRequest;
import com.forever.foreveroj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.forever.foreveroj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.forever.foreveroj.model.entity.Question;
import com.forever.foreveroj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forever.foreveroj.model.entity.User;
import com.forever.foreveroj.model.vo.QuestionSubmitVO;
import com.forever.foreveroj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author ricardo
* @description 针对表【question_submit(题目提交表)】的数据库操作Service
* @createDate 2025-03-24 22:48:21
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
