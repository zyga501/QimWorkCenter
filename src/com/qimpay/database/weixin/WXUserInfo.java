package com.qimpay.database.weixin;


import com.qimpay.database.Database;
import com.qimpay.database.WxDatabase;

import java.util.Map;

public class WXUserInfo {
    public static WXUserInfo getWXUserInfoByAcount(Map param) {
        String statement = "com.qimpay.database.mapping.weixin.wxuserinfo.getwxuserinfobyacount";
        return WxDatabase.Instance().selectOne(statement,param);
    }

    public static boolean initSubMerchantPwd(Map param){
        String statement = "com.qimpay.database.mapping.weixin.wxuserinfo.initsubmerchantpwd";
        return WxDatabase.Instance().update(statement, param) > 0;
    }

    public String getSubmchid() {
        return submchid;
    }

    public void setSubmchid(String submchid) {
        this.submchid = submchid;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    private String submchid;
    private String xid;
    private String upwd;
}
