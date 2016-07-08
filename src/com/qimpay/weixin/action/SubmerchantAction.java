package com.qimpay.weixin.action;

import com.AjaxActionSupport;
import com.framework.utils.Logger;
import com.framework.utils.StringUtils;
import com.qimpay.database.AllMerchant;
import com.qimpay.database.UserInfo;
import com.qimpay.database.weixin.OrderInfo;
import com.qimpay.database.weixin.WXUserInfo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubmerchantAction extends AjaxActionSupport {
    private String[] chkbox;

    public String[] getChkbox() {
        return chkbox;
    }

    public void setChkbox(String[] chkbox) {
        this.chkbox = chkbox;
    }

    public String InitSubmerchantPwd(){
        try {
            String submchid = getParameter("id").toString();
            Map<String, Object> param= new HashMap<>();
            param.put("uname",submchid);
            if (("1,2").indexOf(getRequest().getSession().getAttribute("roleval").toString())>0)
                param.put("xid",Long.parseLong(getRequest().getSession().getAttribute("uid").toString()));
            else if (!("999").equals(getRequest().getSession().getAttribute("roleval").toString()))
                return AjaxActionComplete(false);
            return AjaxActionComplete(WXUserInfo.initSubMerchantPwd(param));
        }catch (Exception e) {
            return AjaxActionComplete(false);
        }
    }

    public String FetchSubmerchant() throws UnsupportedEncodingException {
        Map<Object, Object> param= new HashMap<>();
        if (null!=getParameter("item") && !getParameter("item").equals("")){
            if (StringUtils.isNumeric(getParameter("item").toString()))
                param.put("submchid", getParameter("item"));
            else
                param.put("storename", java.net.URLDecoder.decode(java.net.URLDecoder.decode(
                    getParameter("item").toString(), "UTF-8"),"UTF-8"));
        }
        if (("1,2").indexOf(getRequest().getSession().getAttribute("roleval").toString())>0)
            param.put("uid",Long.parseLong(getRequest().getSession().getAttribute("uid").toString()));
        if ((null!=getParameter("salemanid")) && (!getParameter("salemanid").toString().equals("")))
            param.put("salemanid",Long.parseLong(getParameter("salemanid").toString()));
        List<HashMap> allmerchantlist = null;
        int currpagenum = 1;
        if (null!=getParameter("currpagenum") && !getParameter("currpagenum").equals("0")){
            try{
                if ( Integer.parseInt(getParameter("currpagenum").toString())>0){
                    currpagenum = Integer.parseInt(getParameter("currpagenum").toString());}
            }
            catch (Exception e){

            }
        }
        allmerchantlist = AllMerchant.getweixinsubmerchantByUid(param);

        List<HashMap> returnlist =  allmerchantlist.subList((currpagenum-1)*15,Math.min(currpagenum*15,allmerchantlist.size()));
        Map map=new HashMap<>();
        map.put("totalcount",allmerchantlist.size());
        map.put("pagecount",(allmerchantlist.size()+15-1)/15);
        returnlist.add(0, (HashMap) map);
        return  AjaxActionComplete(returnlist);
    }

    public String LinkMerchant() throws UnsupportedEncodingException {
        Map<Object, Object> param= new HashMap<>();
        if (null!=getParameter("item") && !getParameter("item").equals("")){
            if (StringUtils.isNumeric(getParameter("item").toString()))
                param.put("submchid", getParameter("item"));
            else
                param.put("storename", java.net.URLDecoder.decode(java.net.URLDecoder.decode(
                        getParameter("item").toString(), "UTF-8"),"UTF-8"));
        }
        if (("1,2").indexOf(getRequest().getSession().getAttribute("roleval").toString())>0)
            return AjaxActionComplete("page404");
        List<HashMap> allmerchantlist  = AllMerchant.getAllweixinsubmerchant(param);
//        Map map=new HashMap<>();
//        allmerchantlist.add(0, (HashMap) map);
        return  AjaxActionComplete(allmerchantlist);
    }

    public String LinkItems(){
        try {
            List<String> idlist = new ArrayList<String>();
            for (int i = 0; i < chkbox.length; i++) {
                idlist.add(chkbox[i]);
            }
            String salemanid = getParameter("salemanid").toString();
            Map param = new HashMap();
            AllMerchant.deleteMerchantLink(idlist);
            param.put("itemlist", idlist);
            param.put("sid", salemanid);
            AllMerchant.insertMerchantLink(param);
            Map map = new HashMap<>();
            map.put("chkbox", idlist);
            return AjaxActionComplete(map);
        }
        catch (Exception e){
            return AjaxActionComplete(false);
        }
    }
}
