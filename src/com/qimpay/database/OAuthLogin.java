package com.qimpay.database;

import java.util.Date;
import java.util.Map;

public class OAuthLogin {
    public static void main(String[] args) throws Exception {

    }

    public static OAuthLogin getOAuthLoginByRmdno(String rmdno) {
        String statement = "com.database.merchant.mapping.oAuthLogin.getOAuthLoginByRmdno";
        return Database.Instance().selectOne(statement, rmdno);
    }

    public static boolean insertOAuthLogin(Map paramap) {
            String statement = "com.database.merchant.mapping.oAuthLogin.insertOAuthLogin";
            return Database.Instance().insert(statement, paramap)==1;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public String getRmdno() {
        return rmdno;
    }

    public void setRmdno(String rmdno) {
        this.rmdno = rmdno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String openid;
    private Date inserttime;
    private String rmdno;
}
