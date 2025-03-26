package com.forever.foreveroj.model.dto.questionsubmit;

import com.forever.foreveroj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 提交记录和信息查询
 *
 * @author <a href="https://github.com/RicardoLou">RicardoLou</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 提交状态
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;


    /**
     * 用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}