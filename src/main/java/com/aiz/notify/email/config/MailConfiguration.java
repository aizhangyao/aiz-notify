package com.aiz.notify.email.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @ClassName MailConfiguration
 * @Description
 * @Author Yao
 * @Date Create in 11:34 下午 2020/10/4
 * @Version 1.0
 */

@Configuration
public class MailConfiguration {

    @Value("${mail.smtp.host}")
    private String mailSmtpHost;

    @Value("${mail.smtp.port}")
    private Integer mailSmtpPort;

    @Value("${mail.send.username}")
    private String mailSendUserName;

    @Value("${mail.send.password}")
    private String mailSendPassword;

    @Bean
    public JavaMailSenderImpl JavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailSmtpHost);
        mailSender.setPort(mailSmtpPort);
        mailSender.setUsername(mailSendUserName);
        mailSender.setPassword(mailSendPassword);
        return  mailSender;
    }

}