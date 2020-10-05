package com.aiz.notify.email.service;

import com.aiz.notify.email.entity.EmailTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Yao
 * @since 2020-10-04
 */
public interface EmailTemplateService extends IService<EmailTemplate> {
    EmailTemplate queryEmailTemplate(Long templateId,String type);
}
