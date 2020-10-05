package com.aiz.notify.sms.service.impl;

import com.aiz.notify.common.constant.NotifyConstant;
import com.aiz.notify.sms.entity.SmsTemplate;
import com.aiz.notify.sms.mapper.SmsTemplateMapper;
import com.aiz.notify.sms.service.SmsTemplateService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
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
public class SmsTemplateServiceImpl extends ServiceImpl<SmsTemplateMapper, SmsTemplate> implements SmsTemplateService {

    @Override
    public SmsTemplate querySmsTemplate(String templateCode, String type){
        return querySmsTemplate(null,templateCode,type);
    }

    @Override
    public SmsTemplate querySmsTemplate(Long templateId, String type) {
        return querySmsTemplate(templateId,null,type);
    }

    @Override
    public SmsTemplate querySmsTemplate(String type) {
        return querySmsTemplate(null,null,type);
    }

    private SmsTemplate querySmsTemplate(Long templateId, String templateCode, String type) {
        QueryWrapper<SmsTemplate> queryWrapper = new QueryWrapper<>();
        if(templateId != null){
            queryWrapper.lambda().eq(SmsTemplate::getId,templateId);
        }
        if(StringUtils.isNotBlank(templateCode)){
            queryWrapper.lambda().eq(SmsTemplate::getTemplateCode,templateCode);
        }
        if(StringUtils.isNotBlank(type)){
            queryWrapper.lambda().eq(SmsTemplate::getType,type);
        }
        queryWrapper.lambda().eq(SmsTemplate::getEnable, NotifyConstant.EnableEnum.YES.getCode());
        SmsTemplate smsTemplate = this.getOne(queryWrapper);
        return smsTemplate;
    }
}
