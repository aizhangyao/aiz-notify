package com.aiz.notify.sms.controller;


import com.aiz.notify.common.api.vo.Result;
import com.aiz.notify.sms.entity.Sms;
import com.aiz.notify.sms.service.SmsService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
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
@RequestMapping("/api/notify/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    @ApiOperation("短信测试使用")
    @GetMapping("/test")
    public Result<?> test(){
        return Result.ok("短信测试使用");
    }

    @ApiOperation("测试保存短信")
    @PostMapping("/save")
    public Result<?> save(Sms sms){
        sms.setPhoneNumber("17805053916");
        sms.setContent("保存短信測試内容");
        sms.setSendTime(LocalDateTime.now());
        sms.setSendStatus(1);
        sms.setTemplateId(-1L);
        return Result.ok(smsService.save(sms));
    }

    @ApiOperation("测试获取短信内容")
    @PostMapping("/get-sms-content")
    public Result<?> testGetEmailContent(String templateCode){
        if(StringUtils.isNotBlank(templateCode)){
            templateCode = "SMS_202335032";
        }
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
        String smsContent = smsService.getSmsContent(templateCode, params);
        return Result.ok(smsContent);
    }

    @ApiOperation("测试发送短信")
    @PostMapping("/send")
    public Result<?> send(String templateCode){
        String phoneNumber ="8617805053916";
        if(templateCode == null){
            templateCode = "SMS_202335032"; //已缴提醒
        }
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

        return Result.ok(smsService.sendSms(phoneNumber,templateCode,params));
    }




}
