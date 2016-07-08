package com.framework.action;

import com.AjaxActionSupport;
import com.qimpay.database.MenuTree;
import com.qimpay.database.UserInfo;
import com.qimpay.database.weixin.WXUserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAction extends AjaxActionSupport {
    private static String  MAINPAGE ="mainpage";
    private static String  LOGIN="loginpage";
    private static String  USERINFOJSP="userinfojsp";
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    private String loginErrorMessage;
    private List<Object> menulist = new ArrayList<>();
    public List<Object> getMenulist() {
        return menulist;
    }

    public void setMenulist(List<Object> menulist) {
        this.menulist = menulist;
    }

    public String getLoginErrorMessage() {
        return loginErrorMessage;
    }

    public void setLoginErrorMessage(String loginErrorMessage) {
        this.loginErrorMessage = loginErrorMessage;
    }

    public String Login() {
        try {
            if (getParameter("loginrole").toString().equals("1")) {
                Map paramap = new HashMap();
                paramap.put("uname", getParameter("uname").toString());
                paramap.put("upwd", getParameter("upwd").toString());
                UserInfo userInfo = UserInfo.getUserInfoByAcount(paramap);
                if (null != userInfo) {
                    if (userInfo.getActive()!=1){
                        loginErrorMessage = "账号已经冻结";
                        return LOGIN;
                    }
                    getRequest().getSession().setAttribute("uname", userInfo.getUname());
                    getRequest().getSession().setAttribute("unick", userInfo.getUnick());
                    getRequest().getSession().setAttribute("uid", userInfo.getId());
                    getRequest().getSession().setAttribute("roleval", userInfo.getRole());
                    getRequest().getSession().setAttribute("role", userInfo.getRole() == 999 ? "管理员" : userInfo.getRole() == 1 ? "机构" : userInfo.getRole() == 2 ? "销售" : userInfo.getRole() == 3 ? "职员" : "未知");
                    return "gomainpage";
                } else {
                    loginErrorMessage = "账号密码，验证码有校验不通过";
                    return LOGIN;
                }
            }
            else if (getParameter("loginrole").toString().equals("2")) {
                Map paramap = new HashMap();
                paramap.put("uname", getParameter("uname").toString());
                paramap.put("upwd", getParameter("upwd").toString());
                WXUserInfo  wxuserInfo = WXUserInfo.getWXUserInfoByAcount(paramap);
                if (null != wxuserInfo) {
                    getRequest().getSession().setAttribute("uname",wxuserInfo.getSubmchid());
                    getRequest().getSession().setAttribute("unick",wxuserInfo.getSubmchid());
                    getRequest().getSession().setAttribute("uid",0);
                    getRequest().getSession().setAttribute("roleval", -1);//微信-1 京东-2 支付宝-3
                    getRequest().getSession().setAttribute("role","商户");
                    return "gomainpage";
                } else {
                    loginErrorMessage = "账号密码，验证码有校验不通过";
                    return LOGIN;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            loginErrorMessage = "账号密码，验证码有校验不通过";
            return LOGIN;
        }
        return LOGIN;
    }

    public String Initmainpage() {
        List<MenuTree> menutreelist = MenuTree.getMenuNodeByUid(Long.parseLong(getRequest().getSession().getAttribute("roleval").toString()));
        for (MenuTree m : menutreelist) {
            List<MenuTree> prem = MenuTree.getMenuNodeByPreId(m.getId(),Long.parseLong(getRequest().getSession().getAttribute("roleval").toString()));
            Map mapitem = new HashMap();
            mapitem.put("prenode",m);
            mapitem.put("subnode",prem);
            menulist.add(mapitem);
        }
        return "mainpagejsp";
    }

    public String updateUserInfoPwd() {
        Map<String, Object> param=new HashMap<String, Object>();
        if (getAttribute("roleval").equals(999)){
            param.put("upwd","123");
            param.put("uname",getParameter("salemanname"));
           if ( UserInfo.updateUserInfoPwd(param)){
               return AjaxActionComplete(true);
           }
            else
               return AjaxActionComplete(false);
        }
        return AjaxActionComplete(false);
    }

    public String Logout(){
        getRequest().getSession().removeAttribute("uname");
        getRequest().getSession().invalidate();
        return LOGIN;
    }

    public String Usergroup(){
        return "usergroup";
    }

    public String FetchRole(){
        Map map = new HashMap<>();
        if (null!=getParameter("roleval") && !getParameter("roleval").equals("")){
            List<HashMap> userInfo = UserInfo.getAllUserInfo(Integer.parseInt(getParameter("roleval").toString()));
            map.put("totalcount",userInfo.size());
            userInfo.add(0, (HashMap) map);
            return AjaxActionComplete(userInfo);
        }
        else
            map.put("errorMessage","Error!") ;
        return AjaxActionComplete(map);
    }

    public String userInfo(){
        try {
            String uname = getParameter("uname").toString();
            Map<String, Object> param=new HashMap<String, Object>();
            param.put("uname",uname);
            userInfo = UserInfo.getUserInfoByAcount(param);
            if (null!=userInfo){
                return USERINFOJSP;
            }
            return AjaxActionComplete("page404");
        }
        catch (Exception e){
            return AjaxActionComplete("page404");
        }
    }
}
