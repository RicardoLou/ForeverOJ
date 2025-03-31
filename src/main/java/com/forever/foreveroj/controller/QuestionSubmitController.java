package com.forever.foreveroj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forever.foreveroj.annotation.AuthCheck;
import com.forever.foreveroj.common.BaseResponse;
import com.forever.foreveroj.common.ErrorCode;
import com.forever.foreveroj.common.ResultUtils;
import com.forever.foreveroj.constant.UserConstant;
import com.forever.foreveroj.exception.BusinessException;
import com.forever.foreveroj.model.dto.question.QuestionQueryRequest;
import com.forever.foreveroj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.forever.foreveroj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.forever.foreveroj.model.entity.Question;
import com.forever.foreveroj.model.entity.QuestionSubmit;
import com.forever.foreveroj.model.entity.User;
import com.forever.foreveroj.model.vo.QuestionSubmitVO;
import com.forever.foreveroj.service.QuestionSubmitService;
import com.forever.foreveroj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 * @author <a href="https://github.com/RicardoLou">RicardoLou</a>
 */
@RestController
//@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

//    /**
//     * 提交题目
//     *
//     * @param questionSubmitAddRequest
//     * @param request
//     * @return result 用户本次提交代码的编号
//     */
//    @PostMapping("/")
//    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
//            HttpServletRequest request) {
//        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        // 登录才能提交
//        final User loginUser = userService.getLoginUser(request);
//        Long questionSubmitID = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
//        return ResultUtils.success(questionSubmitID);
//    }
//
//    /**
//     * 分页获取题目提交列表查询（管理员外，用户只能看到非答案、提交代码等公开信息）
//     * @param questionSubmitQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/list/page")
//    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
//                                                                         HttpServletRequest request) {
//        long current = questionSubmitQueryRequest.getCurrent();
//        long size = questionSubmitQueryRequest.getPageSize();
//        // 从数据库中查询原始的题目提交分页信息
//        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
//                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
//        final User loginUser = userService.getLoginUser(request);
//        // 返回脱敏信息
//        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
//    }
}










