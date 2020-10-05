package com.aiz.notify.email.controller;


import com.aiz.notify.email.entity.EmailTemplate;
import com.aiz.notify.email.service.EmailTemplateService;
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
@RequestMapping("/api/notify/email-template")
public class EmailTemplateController {
    @Autowired
    private EmailTemplateService emailTemplateService;

    @ApiOperation("测试获取邮件")
    @GetMapping("/query")
    public Object queryTemplateParams(){
        List<EmailTemplate> templateParams = emailTemplateService.list();
        return templateParams;
    }
}
