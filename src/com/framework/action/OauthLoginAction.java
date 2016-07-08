package com.framework.action;

import com.AjaxActionSupport;
import com.framework.ProjectSettings;
import com.qimpay.database.OAuthLogin;
import com.framework.utils.OpenId;
import com.qimpay.database.UserInfo;
import com.qimpay.database.weixin.MerchantInfo;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OauthLoginAction extends AjaxActionSupport {
    public String chkwx() {
        String id = getParameter("id").toString();
        String dt = getParameter("dt").toString();
        OAuthLogin oAuthLogin = OAuthLogin.getOAuthLoginByRmdno(dt);
        if (null != oAuthLogin) {
            if ((new Date()).getTime() - oAuthLogin.getInserttime().getTime() < 15000) {
                UserInfo userInfo = UserInfo.getUserInfoByOpenid(oAuthLogin.getOpenid().toString());
                if (null != userInfo) {
                    getRequest().getSession().setAttribute("uname", userInfo.getUname());
                    getRequest().getSession().setAttribute("unick", userInfo.getUnick());
                    getRequest().getSession().setAttribute("uid", userInfo.getId());
                    getRequest().getSession().setAttribute("roleval", userInfo.getRole());
                    getRequest().getSession().setAttribute("role", userInfo.getRole() == 999 ? "管理员" : userInfo.getRole() == 1 ? "机构" : userInfo.getRole() == 2 ? "销售" : userInfo.getRole() == 3 ? "职员" : "未知");
                    return "gomainpage";
                }
            }
        }
        return AjaxActionComplete(false);
    }

    public String wx() throws Exception {
        MerchantInfo merchantInfo = MerchantInfo.getMerchantInfoById(ProjectSettings.getId());
        String appid = merchantInfo.getAppid();
        String appsecret = merchantInfo.getAppsecret();
        JSONObject jsonObject = JSONObject.fromObject(getRequest().getSession().getAttribute("datajson"));
        appid = merchantInfo.getAppid();
        appsecret = merchantInfo.getAppsecret();
        OpenId openId = new OpenId(appid, appsecret, getParameter("code").toString());
        if (openId.getRequest()) {
            UserInfo userInfo = UserInfo.getUserInfoByOpenid(openId.getOpenId());
            if (null != userInfo) {
                Map paramap = new HashMap();
                paramap.put("openid", "openId.getOpenId()");
                paramap.put("rmdno", jsonObject.getString("dt"));
                OAuthLogin.insertOAuthLogin(paramap);
                return AjaxActionComplete(true);
            }
        }
        return AjaxActionComplete(false);
    }

    public void oauthWX() throws IOException {
        MerchantInfo merchantInfo = MerchantInfo.getMerchantInfoById(ProjectSettings.getId());
        String appid = merchantInfo.getAppid();
        String appsecret = merchantInfo.getAppsecret();
        String dt = getParameter("dt").toString();
        String mid = getParameter("id").toString();
        getRequest().getSession().setAttribute("datajson", "{'dt':'" + dt + "','mid':'" + mid + "'}");
        String redirect_uri = getRequest().getScheme() + "://" + getRequest().getServerName() + getRequest().getContextPath() + "/weixin/rtopenid.jsp";
        String petOpenidUri = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                        "%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect",
                appid, redirect_uri);
        getResponse().sendRedirect(petOpenidUri);
    }

}
