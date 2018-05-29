package com.xkh.hzp.xkh.config;

/**
 * @packageName com.xkh.hzp.xkh.config
 * @FileName UrlConfig
 * @Author tangyang
 * @DATE 2018/5/28
 **/
public class UrlConfig {

    public static final String USER_BASE_URL = "http://192.168.2.131:9091";
    public static final String DYNAMIC_BASE_URL = "http://192.168.2.130:8002";
    public static final String banner = DYNAMIC_BASE_URL + "/banner";
    public static final String getToken = DYNAMIC_BASE_URL + "/dynamic/getToken";
    public static final String login = USER_BASE_URL + "/uc/login/in";
    public static final String register = USER_BASE_URL + "/uc/register/save";
    public static final String registerCheck = USER_BASE_URL + "/uc/register/check";
    public static final String modifyPwd = USER_BASE_URL + "/uc/passwd/update";
    public static final String findPwd = USER_BASE_URL + "/uc/passwd/find";
    public static final String settingPwd = USER_BASE_URL + "/uc/passwd/reset";
    public static final String logout = USER_BASE_URL + "/uc/login/out";
    public static final String loginCheck = USER_BASE_URL + "/uc/login/check";
    public static final String getAuthCode = USER_BASE_URL + "/uc/authcode/sms/get";
    public static final String businessCooperation = DYNAMIC_BASE_URL + "/businessCooperation/addCooperation";
    public static final String talentJoinUs = DYNAMIC_BASE_URL + "/apply/addJoinUs";
    public static final String publishDynamic = DYNAMIC_BASE_URL + "/dynamic/apply";
    public static final String queryHotLable = DYNAMIC_BASE_URL + "/hotLabel";
    public static final String queryTalentList = DYNAMIC_BASE_URL + "/talent";
}
