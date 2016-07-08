package com.qimpay.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfo {

    public static UserInfo getUserInfoById(String UId) {
        String statement = "com.qimpay.database.mapping.userinfo.getUserInfoById";
        return Database.Instance().selectOne(statement, UId);
    }

    public static UserInfo getUserInfoByOpenid(String Openid) {
        String statement = "com.qimpay.database.mapping.userinfo.getUserInfoByOpenid";
        return Database.Instance().selectOne(statement, Openid);
    }

    public static UserInfo getUserInfoByAcount(Map param) {
        String statement = "com.qimpay.database.mapping.userinfo.getUserInfoByAcount";
        return Database.Instance().selectOne(statement,param);
    }

    public static List<HashMap> getAllUserInfo(long roleid) {
        String statement = "com.qimpay.database.mapping.userinfo.getAllUserInfo";
        return Database.Instance().selectList(statement,roleid);
    }

    public static boolean updateUserInfoPwd(Map param) {
        String statement = "com.qimpay.database.mapping.userinfo.updateUserInfoPwd";
        return Database.Instance().update(statement, param) == 1;
    }

    public static List<Long> getsubmchidlist(long uid) {
        String statement = "com.qimpay.database.mapping.submerchant.getsubmchidlist";
        return Database.Instance().selectList(statement,uid);
    }

    public long getId() {
        return id_;
    }

    public void setId(long id_) {
        this.id_ = id_;
    }

    public String getUname() {
        return uname_;
    }

    public void setUname(String uname_) {
        this.uname_ = uname_;
    }

    public String getUpwd() {
        return upwd_;
    }

    public void setUpwd(String upwd_) {
        this.upwd_ = upwd_;
    }

    public String getUnick() {
        return unick_;
    }

    public void setUnick(String unick_) {
        this.unick_ = unick_;
    }

    public int getRole() {
        return role_;
    }

    public void setRole(int role_) {
        this.role_ = role_;
    }

    public int getActive() {
        return active_;
    }

    public void setActive(int active_) {
        this.active_ = active_;
    }

    public String getOpenid() {
        return openid_;
    }

    public void setOpenid(String openid_) {
        this.openid_ = openid_;
    }

    private long id_;
    private String uname_;
    private String upwd_;
    private String unick_;
    private int role_;
    private int active_;
    private String openid_;
}
