package com.aiz.notify.email.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
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
@TableName("sys_email")
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 收件人邮箱
     */
    private String toAddress;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 邮件模版id
     */
    private Long templateId;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 发送状态(1代表成功;0代表失败)
     */
    private Integer sendStatus;

    /**
     * 服务返回
     */
    private String serverResponse;

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


}
