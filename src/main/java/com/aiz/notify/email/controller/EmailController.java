package com.aiz.notify.email.controller;


import com.aiz.notify.common.api.vo.Result;
import com.aiz.notify.email.entity.Email;
import com.aiz.notify.email.service.EmailService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Yao
 * @since 2020-10-04
 */
@RestController
@RequestMapping("/api/notify/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @ApiOperation("测试获取邮件")
    @GetMapping("/query")
    public Result<?> queryTemplateParams(){
        List<Email> templateParams = emailService.list();
        return Result.ok(templateParams);
    }

    @ApiOperation("测试保存邮件")
    @PostMapping("/save")
    public Result<?> save(@RequestBody Email email){
        email.setToAddress("zhangyao@linyang.com.cn");
        email.setSubject("测试保存使用");
        email.setContent("保存邮件测试内容");
        email.setTemplateId(1111L);
        email.setSendTime(LocalDateTime.now());
        email.setSendStatus(1);
        email.setServerResponse("OK");
        email.setIsDelete(1);
        email.setCreateTime(LocalDateTime.now());
        email.setUpdateTime(LocalDateTime.now());
        return Result.ok(emailService.save(email));
    }

    @ApiOperation("测试获取邮件内容")
    @GetMapping("/get-email-content")
    public Result<?> testGetEmailContent(){
        Map<String, Object> params = new HashMap<>();
        params.put("billDate", "2020年06月");
        params.put("consNo", "10000001");
        params.put("dataDate", "20200501");
        params.put("lastMonthDate", "20200501");
        params.put("payableExpenses", "136.00");
        params.put("factTotal", "137.00");
        params.put("change", "1.00");
        params.put("deadlinePaymentDate","2020-06-30");
        params.put("days","-76");
        Long templateId = 1L;
        //String emailContent = emailService.getEmailContent(templateId, params);
        //return Result.ok(emailContent);
        return Result.ok(params);
    }

    @ApiOperation("测试发送邮件")
    @PostMapping("/send")
    public Result<?> send(@RequestBody Email email){
        email.setToAddress("819647113@qq.com");
        email.setSubject("测试发送使用");
        email.setContent("发送邮件测试内容");
        email.setTemplateId(1L);
        email.setSendTime(LocalDateTime.now());
        email.setSendStatus(1);
        email.setServerResponse("success");
        //emailService.sendMailPdf(email,filePath);
        boolean b = emailService.sendMail(email);
        return Result.ok(b);
    }

}
