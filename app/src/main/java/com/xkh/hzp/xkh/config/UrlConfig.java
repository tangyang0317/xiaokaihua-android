package com.xkh.hzp.xkh.config;

/**
 * @packageName com.xkh.hzp.xkh.config
 * @FileName UrlConfig
 * @Author tangyang
 * @DATE 2018/5/28
 **/
public class UrlConfig {

    public static final String USER_BASE_URL = "http://user-dev.xiaokaihua.com";
    public static final String DYNAMIC_BASE_URL = "http://dynamic-dev.xiaokaihua.com";
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
    public static final String userInfo = USER_BASE_URL + "/uc/user/modify";
    public static final String queryuserInfo = USER_BASE_URL + "/inner/uc/user/_getOne";
    public static final String foucsTalent = DYNAMIC_BASE_URL + "/focus";
    public static final String cancleFoucsTalent = DYNAMIC_BASE_URL + "/focus/cancel";
    public static final String dynamicList = DYNAMIC_BASE_URL + "/dynamic";
    public static final String queryTalentInfo = DYNAMIC_BASE_URL + "/talent";
    public static final String searchDynamic = DYNAMIC_BASE_URL + "/search/dynamic";
    public static final String searchUser = DYNAMIC_BASE_URL + "/search/user";
    public static final String queryDynamicById = DYNAMIC_BASE_URL + "/dynamic";
    public static final String dynamciPraised = DYNAMIC_BASE_URL + "/dynamic/patch/like";
    public static final String dynamciUnPraised = DYNAMIC_BASE_URL + "/dynamic/patch/like/cancel";
    public static final String dynamicComment = DYNAMIC_BASE_URL + "/comment";
    public static final String commentPraised = DYNAMIC_BASE_URL + "/comment/patch/like";
    public static final String commentUnPraised = DYNAMIC_BASE_URL + "/comment/patch/like/cancel";
    public static final String commentReply = DYNAMIC_BASE_URL + "/comment/reply";
    public static final String commentDelete = DYNAMIC_BASE_URL + "/comment/delete";
}
