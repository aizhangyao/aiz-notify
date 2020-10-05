package com.aiz.notify.param.controller;


import com.aiz.notify.param.entity.TemplateParam;
import com.aiz.notify.param.service.TemplateParamService;
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
@RequestMapping("/api/notify/template-param")
public class TemplateParamController {
    @Autowired
    private TemplateParamService templateParamService;

    @ApiOperation("测试获取邮件模板")
    @GetMapping("/query")
    public Object queryTemplateParams(){
        List<TemplateParam> templateParams = templateParamService.list();
        return templateParams;
    }
}
