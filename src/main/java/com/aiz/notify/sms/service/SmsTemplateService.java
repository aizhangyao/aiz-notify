package com.aiz.notify.sms.service;

import com.aiz.notify.sms.entity.SmsTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Yao
 * @since 2020-10-04
 */
public interface SmsTemplateService extends IService<SmsTemplate> {
    SmsTemplate querySmsTemplate(String templateCode, String type);
    SmsTemplate querySmsTemplate(Long templateId,String type);
    SmsTemplate querySmsTemplate(String type);
}
