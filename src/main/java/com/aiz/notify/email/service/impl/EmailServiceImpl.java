package com.aiz.notify.email.service.impl;

import com.aiz.notify.email.entity.Email;
import com.aiz.notify.email.entity.EmailTemplate;
import com.aiz.notify.email.mapper.EmailMapper;
import com.aiz.notify.email.service.EmailService;
import com.aiz.notify.email.service.EmailTemplateService;
import com.aiz.notify.util.PDFReport;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Yao
 * @since 2020-10-04
 */
@Service
public class EmailServiceImpl extends ServiceImpl<EmailMapper, Email> implements EmailService {
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private EmailMapper emailMapper;
    @Autowired
    private EmailTemplateService emailTemplateService;

    @Override
    public boolean sendMail(Email email) {
        return send(email);
    }

    @Override
    public boolean sendMailPdf(Email email, String filePdfPath) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Objects.requireNonNull(javaMailSender.getUsername()));
            helper.setTo(email.getToAddress());
            helper.setSubject(email.getSubject());
            //邮件附件pdf
            if(filePdfPath != null && filePdfPath.length() != 0) {
                File filePdf = new File(filePdfPath);
                if(filePdf != null && filePdf.isFile()){
                    helper.addAttachment(filePdf.getName(),filePdf);
                }
            }
            helper.setText(new String(email.getContent().getBytes(), StandardCharsets.ISO_8859_1), true);
            javaMailSender.send(message);
            email.setSendTime(LocalDateTime.now());
            email.setSendStatus(1);
            email.setServerResponse("OK");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            email.setSendStatus(0);
            email.setServerResponse(e.getMessage());
        } finally {
            emailMapper.insert(email);
        }
        return false;
    }

    @Override
    public String getEmailContent(Long templateId, Map params) {
        QueryWrapper<EmailTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EmailTemplate::getId,templateId);
        EmailTemplate emailTemplate = emailTemplateService.getOne(queryWrapper);
        if (emailTemplate == null) {
            log.error("没有找到邮件模板，模板id："+templateId);
            throw new RuntimeException("没有找到邮件模板，模板id："+templateId);
        }
        String paramsStr = JSON.toJSONString(params);
        String content = emailTemplate.composeContent(JSON.parseObject(paramsStr, Map.class));
        return content;
    }

    @Override
    public String getPdfFilePath(Map params, String code) {
        return new PDFReport().ExportReport(params,code);
    }

    private boolean send(Email email) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Objects.requireNonNull(javaMailSender.getUsername()));
            helper.setTo(email.getToAddress());
            helper.setSubject(email.getSubject());
            helper.setText(new String(email.getContent().getBytes(), StandardCharsets.ISO_8859_1), true);
            javaMailSender.send(message);
            email.setSendTime(LocalDateTime.now());
            email.setSendStatus(1);
            email.setServerResponse("OK");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            email.setSendStatus(0);
            email.setServerResponse(e.getMessage());
        } finally {
            email.setCreateTime(LocalDateTime.now());
            email.setUpdateTime(LocalDateTime.now());
            emailMapper.insert(email);
        }
        return false;
    }

}
