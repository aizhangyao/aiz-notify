package com.aiz.notify.email.service.impl;

import com.aiz.notify.common.constant.NotifyConstant;
import com.aiz.notify.email.entity.EmailTemplate;
import com.aiz.notify.email.mapper.EmailTemplateMapper;
import com.aiz.notify.email.service.EmailTemplateService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Yao
 * @since 2020-10-04
 */
@Service
public class EmailTemplateServiceImpl extends ServiceImpl<EmailTemplateMapper, EmailTemplate> implements EmailTemplateService {

    @Override
    public EmailTemplate queryEmailTemplate(Long templateId, String type) {
        QueryWrapper<EmailTemplate> queryWrapper = new QueryWrapper<>();
        if(templateId != null){
            queryWrapper.lambda().eq(EmailTemplate::getId,templateId);
        }
        if(type != null){
            queryWrapper.lambda().eq(EmailTemplate::getType,type);
        }
        queryWrapper.lambda().eq(EmailTemplate::getEnable, NotifyConstant.EnableEnum.YES.getCode());
        EmailTemplate emailTemplate = this.getOne(queryWrapper);
        return emailTemplate;
    }

}
