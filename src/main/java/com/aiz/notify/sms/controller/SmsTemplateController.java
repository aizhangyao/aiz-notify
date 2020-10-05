package com.aiz.notify.sms.controller;


import com.aiz.notify.sms.entity.SmsTemplate;
import com.aiz.notify.sms.service.SmsTemplateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Yao
 * @since 2020-10-04
 */
@RestController
@RequestMapping("/api/notify/sms-template")
public class SmsTemplateController {
    @Autowired
    private SmsTemplateService smsTemplateService;

    @ApiOperation("测试获取短信模板")
    @GetMapping("/query")
    public Object queryTemplateParams(){
        List<SmsTemplate> templateParams = smsTemplateService.list();
        return templateParams;
    }

}
