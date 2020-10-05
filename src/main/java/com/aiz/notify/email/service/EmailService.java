package com.aiz.notify.email.service;

import com.aiz.notify.email.entity.Email;
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
public interface EmailService extends IService<Email> {
    /**
     * 发送普通邮件
     * @param email
     * @return
     */
    boolean sendMail(Email email);

    /**
     * 发送带有pdf文件的短信
     * @param email
     * @param filePdfPath
     * @return
     */
    boolean sendMailPdf(Email email,String filePdfPath);

    /**
     * 获取邮件的内容
     * @param templateId    邮件模板Id
     * @param params    邮件内容参数
     * @return  邮件的内容
     */
    String getEmailContent(Long templateId, Map params);

    /**
     * 生成并且获取Pdf的路径
     * @param params 附件内容
     * @param code pdfCode
     * @return
     */
    String getPdfFilePath(Map params,String code);
}
