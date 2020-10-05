package com.aiz.notify.sms.service.impl;

import com.aiz.notify.common.constant.NotifyConstant;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aiz.notify.sms.mapper.SmsMapper;
import com.aiz.notify.sms.entity.Sms;
import com.aiz.notify.sms.entity.SmsTemplate;
import com.aiz.notify.sms.service.SmsService;
import com.aiz.notify.sms.service.SmsTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Yao
 * @since 2020-10-04
 */
@Service
@Transactional
@Slf4j
public class SmsServiceImpl extends ServiceImpl<SmsMapper, Sms> implements SmsService {
    @Value("${aliyun.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.sign-name}")
    private String signName;

    @Autowired
    private SmsMapper smsMapper;

    @Autowired
    private SmsTemplateService smsTemplateService;

    private static final String DOMAIN = "dysmsapi.aliyuncs.com";
    private static final String PRODUCT = "Dysmsapi";


    @Override
    public String getSmsContent(String templateCode, Map params) {
        QueryWrapper<SmsTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SmsTemplate::getTemplateCode,templateCode);
        SmsTemplate smsTemplate = smsTemplateService.getOne(queryWrapper);
        if (null == smsTemplate) {
            log.error("没有找到短信模板，模板Code："+templateCode);
            throw new RuntimeException("没有找到短信模板，模板Code："+templateCode);
        }
        String paramsStr = JSON.toJSONString(params);
        String content = smsTemplate.composeContent(JSON.parseObject(paramsStr, Map.class));
        return content;
    }

    @Override
    public boolean sendSms(String phoneNumber, String templateCode, Map params) {
        QueryWrapper<SmsTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SmsTemplate::getTemplateCode,templateCode);
        SmsTemplate smsTemplate = smsTemplateService.getOne(queryWrapper);

        Sms sms = new Sms();
        sms.setSendTime(LocalDateTime.now());
        sms.setPhoneNumber(phoneNumber);
        sms.setTemplateId(smsTemplate.getId());
        sms.setContent(smsTemplate.composeContent(params));

        try {
            SendSmsResponse smsResponse = send(sms.getPhoneNumber(), templateCode, JSON.toJSONString(params));
            sms.setServerResponse(smsResponse.getMessage());
            if (NotifyConstant.SendSmsResponseCodeEnum.OK.getCode().equalsIgnoreCase(smsResponse.getCode())) {
                sms.setSendStatus(1);
                return true;
            } else {
                sms.setSendStatus(0);
            }
        } catch (ClientException e) {
            sms.setSendStatus(0);
            e.printStackTrace();
        } finally {
            sms.setCreateTime(LocalDateTime.now());
            sms.setUpdateTime(LocalDateTime.now());
            smsMapper.insert(sms);
        }
        return false;
    }

    @Override
    public String sendSmsR(String phoneNumber, String templateCode, Map params) {
        String message = null;
        QueryWrapper<SmsTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SmsTemplate::getTemplateCode,templateCode);
        SmsTemplate smsTemplate = smsTemplateService.getOne(queryWrapper);

        Sms sms = new Sms();
        sms.setSendTime(LocalDateTime.now());
        sms.setPhoneNumber(phoneNumber);
        sms.setTemplateId(smsTemplate.getId());
        sms.setContent(smsTemplate.composeContent(params));

        try {
            SendSmsResponse smsResponse = send(sms.getPhoneNumber(), templateCode, JSON.toJSONString(params));
            sms.setServerResponse(smsResponse.getMessage());
            message = smsResponse.getMessage();
            if (NotifyConstant.SendSmsResponseCodeEnum.OK.getCode().equalsIgnoreCase(smsResponse.getCode())) {
                sms.setSendStatus(1);
            } else {
                sms.setSendStatus(0);
            }
        } catch (ClientException e) {
            sms.setSendStatus(0);
            e.printStackTrace();
        } finally {
            sms.setCreateTime(LocalDateTime.now());
            sms.setUpdateTime(LocalDateTime.now());
            smsMapper.insert(sms);
        }
        return message;
    }

    private SendSmsResponse send(String phoneNumber, String templateCode, String params) throws ClientException {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 使用post提交
        request.setMethod(MethodType.POST);
        // 必填:待发送手机号
        // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；
        // 发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”

        if (phoneNumber.startsWith("+86")) {
            // 中国
            phoneNumber = phoneNumber.substring(3);
        } else if (phoneNumber.startsWith("+")) {
            // 国际
            phoneNumber = phoneNumber.substring(1);
        }

        request.setPhoneNumbers(phoneNumber);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        log.info("===========================短信签名："+signName+"==============================");
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam(params);
        // 选填:上行短信扩展码,无特殊需要此字段的用户请忽略此字段
        // request.setSmsUpExtendCode("90997");
        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        // request.setOutId("yourOutId");

        // 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (null == sendSmsResponse.getCode() || !NotifyConstant.SendSmsResponseCodeEnum.OK.getCode().equals(sendSmsResponse.getCode())) {
            log.error("send sms fail: phoneNumber={} error={}", phoneNumber, sendSmsResponse.getCode());
        }
        return sendSmsResponse;
    }

}
