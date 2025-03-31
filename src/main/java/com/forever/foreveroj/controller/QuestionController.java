package com.forever.foreveroj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forever.foreveroj.annotation.AuthCheck;
import com.forever.foreveroj.common.BaseResponse;
import com.forever.foreveroj.common.DeleteRequest;
import com.forever.foreveroj.common.ErrorCode;
import com.forever.foreveroj.common.ResultUtils;
import com.forever.foreveroj.constant.UserConstant;
import com.forever.foreveroj.exception.BusinessException;
import com.forever.foreveroj.exception.ThrowUtils;
import com.forever.foreveroj.model.dto.question.*;
import com.forever.foreveroj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.forever.foreveroj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.forever.foreveroj.model.entity.Question;
import com.forever.foreveroj.model.entity.QuestionSubmit;
import com.forever.foreveroj.model.entity.User;
import com.forever.foreveroj.model.vo.QuestionSubmitVO;
import com.forever.foreveroj.model.vo.QuestionVO;
import com.forever.foreveroj.service.QuestionService;
import com.forever.foreveroj.service.QuestionSubmitService;
import com.forever.foreveroj.service.UserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 题目接口
 *
 * @author <a href="https://github.com/RicardoLou">RicardoLou</a>
 */
@RestController
@RequestMapping("/Question")
@Slf4j
public class QuestionController {

    @Resource
    private QuestionService QuestionService;

    @Resource
    private UserService userService;

    @Resource
    private QuestionSubmitService questionSubmitService;


    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 创建
     *
     * @param QuestionAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest QuestionAddRequest, HttpServletRequest request) {
        if (QuestionAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question Question = new Question();
        BeanUtils.copyProperties(QuestionAddRequest, Question);
        List<String> tags = QuestionAddRequest.getTags();
        if (tags != null) {
            Question.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCase = QuestionAddRequest.getJudgeCase();
        if (judgeCase != null) {
            Question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = QuestionAddRequest.getJudgeConfig();
        if (judgeConfig != null) {
            Question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        QuestionService.validQuestion(Question, true);
        User loginUser = userService.getLoginUser(request);
        Question.setUserId(loginUser.getId());
        Question.setFavourNum(0);
        Question.setThumbNum(0);
        boolean result = QuestionService.save(Question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newQuestionId = Question.getId();
        return ResultUtils.success(newQuestionId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Question oldQuestion = QuestionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldQuestion.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = QuestionService.removeById(id);
        return ResultUtils.success(result);
    }

    /**
     * 更新（仅管理员）
     *
     * @param QuestionUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest QuestionUpdateRequest) {
        if (QuestionUpdateRequest == null || QuestionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question Question = new Question();
        BeanUtils.copyProperties(QuestionUpdateRequest, Question);
        List<String> tags = QuestionUpdateRequest.getTags();
        if (tags != null) {
            Question.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCase = QuestionUpdateRequest.getJudgeCase();
        if (judgeCase != null) {
            Question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = QuestionUpdateRequest.getJudgeConfig();
        if (judgeConfig != null) {
            Question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        // 参数校验
        QuestionService.validQuestion(Question, false);
        long id = QuestionUpdateRequest.getId();
        // 判断是否存在
        Question oldQuestion = QuestionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = QuestionService.updateById(Question);
        return ResultUtils.success(result);
    }


    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<Question> getQuestionById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = QuestionService.getById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        // 不是本人或不是管理员
        if (!loginUser.getId().equals(question.getUserId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        return ResultUtils.success(question);
    }


    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<QuestionVO> getQuestionVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question Question = QuestionService.getById(id);
        if (Question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(QuestionService.getQuestionVO(Question, request));
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param QuestionQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<QuestionVO>> listQuestionVOByPage(@RequestBody QuestionQueryRequest QuestionQueryRequest,
                                                               HttpServletRequest request) {
        long current = QuestionQueryRequest.getCurrent();
        long size = QuestionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Question> QuestionPage = QuestionService.page(new Page<>(current, size),
                QuestionService.getQueryWrapper(QuestionQueryRequest));
        return ResultUtils.success(QuestionService.getQuestionVOPage(QuestionPage, request));
    }

    /**
     * 非脱敏数据查询（only admin）
     *
     * @param questionQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Question>> listQuestionByPageWithAdmin(@RequestBody QuestionQueryRequest questionQueryRequest, HttpServletRequest request) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        Page<Question> questionPage = QuestionService.page(new Page<>(current, size), QuestionService.getQueryWrapper(questionQueryRequest));
        return ResultUtils.success(questionPage);
    }

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param QuestionQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<QuestionVO>> listMyQuestionVOByPage(@RequestBody QuestionQueryRequest QuestionQueryRequest,
                                                                 HttpServletRequest request) {
        if (QuestionQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        QuestionQueryRequest.setUserId(loginUser.getId());
        long current = QuestionQueryRequest.getCurrent();
        long size = QuestionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Question> QuestionPage = QuestionService.page(new Page<>(current, size),
                QuestionService.getQueryWrapper(QuestionQueryRequest));
        return ResultUtils.success(QuestionService.getQuestionVOPage(QuestionPage, request));
    }

    // endregion

    /**
     * 编辑（用户）
     *
     * @param QuestionEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editQuestion(@RequestBody QuestionEditRequest QuestionEditRequest, HttpServletRequest request) {
        if (QuestionEditRequest == null || QuestionEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question Question = new Question();
        BeanUtils.copyProperties(QuestionEditRequest, Question);
        List<String> tags = QuestionEditRequest.getTags();
        if (tags != null) {
            Question.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCase = QuestionEditRequest.getJudgeCase();
        if (judgeCase != null) {
            Question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = QuestionEditRequest.getJudgeConfig();
        if (judgeConfig != null) {
            Question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        // 参数校验
        QuestionService.validQuestion(Question, false);
        User loginUser = userService.getLoginUser(request);
        long id = QuestionEditRequest.getId();
        // 判断是否存在
        Question oldQuestion = QuestionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldQuestion.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = QuestionService.updateById(Question);
        return ResultUtils.success(result);
    }


    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return result 用户本次提交代码的编号
     */
    @PostMapping("/question_submit/do")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
                                               HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能提交
        final User loginUser = userService.getLoginUser(request);
        Long questionSubmitID = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(questionSubmitID);
    }

    /**
     * 分页获取题目提交列表查询（管理员外，用户只能看到非答案、提交代码等公开信息）
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/question_submit/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                         HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        // 从数据库中查询原始的题目提交分页信息
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        final User loginUser = userService.getLoginUser(request);
        // 返回脱敏信息
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }

}
