package com.aiz.notify.sms.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SmsTemplateModel
 * @Description
 * @Author Yao
 * @Date Create in 1:01 上午 2020/10/5
 * @Version 1.0
 */
@Data
public class SmsTemplateModel implements Serializable {
    @ApiModelProperty(hidden = true)
    private String id;

    @ApiModelProperty(value = "")
    private String name;
}
