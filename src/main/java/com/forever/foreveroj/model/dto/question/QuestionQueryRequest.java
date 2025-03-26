package com.forever.foreveroj.model.dto.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.forever.foreveroj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 查询请求
 *
 * @author <a href="https://github.com/RicardoLou">RicardoLou</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 答案
     */
    private String answer;
    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;


    /**
     * 创建用户 id
     */
    private Long userId;


    /**
     * 难易度
     */
    private String difficulty;

    private static final long serialVersionUID = 1L;
}