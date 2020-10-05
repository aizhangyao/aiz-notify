package com.aiz.notify.email.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yao
 * @since 2020-10-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_email_template")
public class EmailTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 邮件模版名
     */
    private String name;

    /**
     * 邮件模板主题
     */
    private String subject;

    /**
     * 邮件模板内容
     */
    private String content;

    /**
     * 模版类别
     */
    private String type;

    /**
     * 启用标记 0否1是
     */
    private Integer enable;

    /**
     * 1-正常、0-删除
     */
    @TableLogic(delval = "0",value = "1")
    private Integer isDelete;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public String composeContent(Map<String, String> params) {
        return format(this.getContent(), params);
    }

    protected String composeSubject(Map<String, String> params) {
        return format(this.getSubject(), params);
    }

    private static final Pattern pattern = Pattern.compile("\\{(.*?)}");

    private String format(String sourStr, Map<String, String> params) {
        Matcher matcher;

        String result = sourStr;
        if (params == null) {
            return result;
        }
        matcher = pattern.matcher(result);
        while (matcher.find()) {
            String key = matcher.group();
            String temp = key.substring(1, key.length() - 1).trim();
            Object value = params.get(temp);
            if (value != null)
                result = result.replace(key, value.toString());
        }
        return result;
    }

}
