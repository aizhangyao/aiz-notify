package com.aiz.notify.sms.service;

import com.aiz.notify.sms.entity.Sms;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Yao
 * @since 2020-10-04
 */
public interface SmsService extends IService<Sms> {

    /**
     * 获取短信发送内容
     * @param templateCode 模板Code
     * @param params 短信k-v内容
     * @return
     */
    String getSmsContent(String templateCode, Map params);

    /**
     * 发送短信
     * @param phoneNumber 手机号码
     * @param templateCode 阿里云Code
     * @param params 短信内容
     * @return
     */
    boolean sendSms(String phoneNumber, String templateCode, Map params);

    String sendSmsR(String phoneNumber, String templateCode, Map params);

}
