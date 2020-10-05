package com.aiz.notify.common.constant;

/**
 * @ClassName NotifyConstant
 * @Description 与通知有关的常量
 * @Author Yao
 * @Date Create in 11:16 下午 2020/10/4
 * @Version 1.0
 */
public interface NotifyConstant {
    /**
     * 模板参数类型
     */
    enum ParamPurposeEnum{
        SMS_AND_EMAIL("01","短信、邮件模板"),TICKET("02","小票模板");
        private String code;
        private String msg;
        ParamPurposeEnum(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    /**
     * 模版类别
     */
    enum TemplateTypeEnum{
        CHARGE("01","收费"),
        CALL("02","催缴"),
        PAID("03","已缴"),
        PREPAID_RECHARGE("04","预付费充值");

        private String code;
        private String msg;
        TemplateTypeEnum(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    /**
     * 模板是否启用
     */
    enum EnableEnum{
        NO(0,"否"),YES(1,"是");
        private Integer code;
        private String msg;
        EnableEnum(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    /**
     * 短信接口调用错误码
     */
    enum SendSmsResponseCodeEnum{
        OK("OK","请求成功。"),
        RAM_PERMISSION_DENY("isp.RAM_PERMISSION_DENY","RAM权限DENY"),
        OUT_OF_SERVICE("isv.OUT_OF_SERVICE","业务停机"),
        PRODUCT_UN_SUBSCRIPT("isv.PRODUCT_UN_SUBSCRIPT","未开通云通信产品的阿里云客户"),
        PRODUCT_UNSUBSCRIBE("isv.PRODUCT_UNSUBSCRIBE","产品未开通"),
        ACCOUNT_NOT_EXISTS("isv.ACCOUNT_NOT_EXISTS","账户不存在"),
        ACCOUNT_ABNORMAL("isv.ACCOUNT_ABNORMAL","账户异常"),
        SMS_TEMPLATE_ILLEGAL("isv.SMS_TEMPLATE_ILLEGAL","短信模版不合法"),
        SMS_SIGNATURE_ILLEGAL("isv.SMS_SIGNATURE_ILLEGAL","短信签名不合法"),
        INVALID_PARAMETERS("isv.INVALID_PARAMETERS","参数异常"),
        SYSTEM_ERROR("isp.SYSTEM_ERROR","isp.SYSTEM_ERROR"),
        MOBILE_NUMBER_ILLEGAL("isv.MOBILE_NUMBER_ILLEGAL","非法手机号"),
        MOBILE_COUNT_OVER_LIMIT("isv.MOBILE_COUNT_OVER_LIMIT","手机号码数量超过限制"),
        TEMPLATE_MISSING_PARAMETERS("isv.TEMPLATE_MISSING_PARAMETERS","模版缺少变量"),
        BUSINESS_LIMIT_CONTROL("isv.BUSINESS_LIMIT_CONTROL","业务限流"),
        INVALID_JSON_PARAM("isv.INVALID_JSON_PARAM","JSON参数不合法，只接受字符串值"),
        BLACK_KEY_CONTROL_LIMIT("isv.BLACK_KEY_CONTROL_LIMIT","黑名单管控"),
        PARAM_LENGTH_LIMIT("isv.PARAM_LENGTH_LIMIT",""),
        PARAM_NOT_SUPPORT_URL("isv.PARAM_NOT_SUPPORT_URL","不支持URL"),
        AMOUNT_NOT_ENOUGH("isv.AMOUNT_NOT_ENOUGH","账户余额不足"),
        TEMPLATE_PARAMS_ILLEGAL("isv.TEMPLATE_PARAMS_ILLEGAL","模版变量里包含非法关键字"),
        SignatureDoesNotMatch("SignatureDoesNotMatch","Specified signature is not matched with our calculation."),
        InvalidTimeStamp("InvalidTimeStamp.Expired","Specified time stamp or date value is expired."),
        SignatureNonceUsed("SignatureNonceUsed","Specified signature nonce was used already."),
        InvalidVersion("InvalidVersion","Specified parameter Version is not valid."),
        InvalidAction_NotFound("InvalidAction.NotFound","Specified api is not found, please check your url and method");


        private String code;
        private String message;

        SendSmsResponseCodeEnum(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

    }
}
