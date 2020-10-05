package com.aiz.notify.sms.entity;

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
@TableName("sys_sms")
public class Sms implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 接收人手机号码
     */
    private String phoneNumber;

    /**
     * 短信模版id
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
