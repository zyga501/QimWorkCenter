package com.qimpay.weixin.action;

import com.AjaxActionSupport;
import com.framework.ProjectSettings;
import com.qimpay.database.MenuTree;
import com.qimpay.database.UserInfo;
import com.qimpay.database.weixin.BillInfo;
import com.qimpay.database.weixin.OrderInfo;
import com.qimpay.weixin.DownLoadBill;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class DetailAction extends AjaxActionSupport {
    public String Detail(){
        return "detail";
    }

    public String BillDetail(){
        return "billdetail";
    }

    public String SubmerchantManageMent(){
        return "submerchantmanagement";
    }

    public String Fetchdetail() throws UnsupportedEncodingException {
        Map<Object, Object> param= new HashMap<>();
        param.put("merchantid", ProjectSettings.getId());
        if (null!=getParameter("startdate") && !getParameter("startdate").equals("")){
            param.put("startdate", getParameter("startdate"));
        }
        if (null!=getParameter("enddate") && !getParameter("enddate").equals("")){
            param.put("enddate", getParameter("enddate").toString().concat(" 23:59:59"));
        }
        if (null!=getParameter("submchid") && !getParameter("submchid").equals("")){
            param.put("submchid", getParameter("submchid"));
        }
        if (null!=getParameter("outtradeno") && !getParameter("outtradeno").equals("")){
            param.put("outtradeno", getParameter("outtradeno"));
        }
        if (null!=getParameter("storename") && !getParameter("storename").equals("")){
            param.put("storename", java.net.URLDecoder.decode(java.net.URLDecoder.decode(
                    getParameter("storename").toString(), "UTF-8"),"UTF-8"));
        }
        if (null!=getParameter("amount") && !getParameter("amount").equals("") && (!getParameter("amount").toString().toLowerCase().contains("or"))){
            param.put("totalFee", getParameter("amount"));
        }
        if (null!=getParameter("username") && !getParameter("username").equals("")){
            param.put("username", java.net.URLDecoder.decode(java.net.URLDecoder.decode(
                    getParameter("username").toString(), "UTF-8"),"UTF-8"));
        }
        if (null!=getParameter("uname") && !getParameter("uname").equals("")){
            param.put("uname", getParameter("uname"));
        }
        int currpagenum = 1;
        if (null!=getParameter("currpagenum") && !getParameter("currpagenum").equals("0")){
            try{
                if ( Integer.parseInt(getParameter("currpagenum").toString())>0){
                    currpagenum = Integer.parseInt(getParameter("currpagenum").toString());}
            }
            catch (Exception e){

            }
        }
        List<Long> submchidlist = null;
        String submchidstring = "'123'";
        if (("1,2").indexOf(getRequest().getSession().getAttribute("roleval").toString())>0)
            submchidlist = UserInfo.getsubmchidlist(Long.parseLong(getRequest().getSession().getAttribute("uid").toString()));
        else if (getRequest().getSession().getAttribute("roleval").toString().equals("-1"))
            submchidstring = getRequest().getSession().getAttribute("uname").toString();
        if (null!=submchidlist){
            for (Long m : submchidlist) {
                submchidstring = submchidstring.concat(",'"+String.valueOf(m)+"'");
            }
        }
        if (! submchidstring.equals("'123'")) {
            param.put("submchlist", submchidstring);
        }
        List<HashMap> orderInfo;
        float sum =0;
        float ratefee = 0;
        //try
        {
            orderInfo = OrderInfo.getOrderInfoList(param);
            Map mapsum = OrderInfo.getOrderInfoSum(param);
            sum = null==mapsum?0:Float.parseFloat(mapsum.get("totalsum").toString());
            ratefee =null==mapsum?0:Float.parseFloat(mapsum.get("ratefee").toString());
        }//catch (Exception e)
        {
           // e.printStackTrace();
            Map map=new HashMap<>();
            map.put("errorMessage","查无信息");
           // return AjaxActionComplete(map);
        }
        List<HashMap> returnlist =  orderInfo.subList((currpagenum-1)*15,Math.min(currpagenum*15,orderInfo.size()));
        Map map=new HashMap<>();
        map.put("totalcount",orderInfo.size());
        map.put("totalsum",sum);
        map.put("ratefee",ratefee);
        map.put("pagecount",(orderInfo.size()+15-1)/15);
        returnlist.add(0, (HashMap) map);
        return  AjaxActionComplete(returnlist);
    }

    public void Exportdetail() throws IOException, WriteException {
        Map<Object, Object> param= new HashMap<>();
        param.put("merchantid", ProjectSettings.getId());
        if (null!=getParameter("startdate") && !getParameter("startdate").equals("")){
            param.put("startdate", getParameter("startdate"));
        }
        if (null!=getParameter("enddate") && !getParameter("enddate").equals("")){
            param.put("enddate", getParameter("enddate").toString().concat(" 23:59:59"));
        }
        if (null!=getParameter("submchid") && !getParameter("submchid").equals("")){
            param.put("submchid", getParameter("submchid"));
        }
        if (null!=getParameter("outtradeno") && !getParameter("outtradeno").equals("")){
            param.put("outtradeno", getParameter("outtradeno"));
        }
        if (null!=getParameter("storename") && !getParameter("storename").equals("")){
            param.put("storename", java.net.URLDecoder.decode(java.net.URLDecoder.decode(
                    getParameter("storename").toString(), "UTF-8"),"UTF-8"));
        }
        if (null!=getParameter("amount") && !getParameter("amount").equals("") && (!getParameter("amount").toString().toLowerCase().contains("or"))){
            param.put("totalFee", getParameter("amount"));
        }
        if (null!=getParameter("username") && !getParameter("username").equals("")){
            param.put("username", java.net.URLDecoder.decode(java.net.URLDecoder.decode(
                    getParameter("username").toString(), "UTF-8"),"UTF-8"));
        }
        if (null!=getParameter("uname") && !getParameter("uname").equals("")){
            param.put("uname", getParameter("uname"));
        }
        int currpagenum = 1;
        if (null!=getParameter("currpagenum") && !getParameter("currpagenum").equals("0")){
            try{
                if ( Integer.parseInt(getParameter("currpagenum").toString())>0){
                    currpagenum = Integer.parseInt(getParameter("currpagenum").toString());}
            }
            catch (Exception e){

            }
        }
        List<Long> submchidlist = null;
        String submchidstring = "'123'";
        if (("1,2").indexOf(getRequest().getSession().getAttribute("roleval").toString())>0)
            submchidlist = UserInfo.getsubmchidlist(Long.parseLong(getRequest().getSession().getAttribute("uid").toString()));
        else if (getRequest().getSession().getAttribute("roleval").toString().equals("-1"))
            submchidstring = getRequest().getSession().getAttribute("uname").toString();
        if (null!=submchidlist){
            for (Long m : submchidlist) {
                submchidstring = submchidstring.concat(",'"+String.valueOf(m)+"'");
            }
        }
        if (! submchidstring.equals("'123'")) {
            param.put("submchlist", submchidstring);
        }
        List<HashMap> orderInfo;
        float sum =0;
        float ratefee = 0;
        //try
        {
            orderInfo = OrderInfo.getOrderInfoList(param);
            Map mapsum = OrderInfo.getOrderInfoSum(param);
            sum = null==mapsum?0:Float.parseFloat(mapsum.get("totalsum").toString());
            ratefee =null==mapsum?0:Float.parseFloat(mapsum.get("ratefee").toString());
        }//catch (Exception e)
        {
            // e.printStackTrace();
            Map map=new HashMap<>();
            map.put("errorMessage","查无信息");
            // return AjaxActionComplete(map);
        }
        getResponse().setHeader("Content-Disposition", new String(
                ("attachment;filename=" + "Querydata"+String.valueOf((new Date()).getTime())+".xls").getBytes("GB2312"),
                "UTF-8"));
        WritableWorkbook wwb;
        OutputStream os = getResponse().getOutputStream();
        wwb = Workbook.createWorkbook(os);
        WritableSheet sheet = wwb.createSheet("查询结果", 0);
        Label label;
        int x = 0;
        for (HashMap m : orderInfo) {
            int y = 0;
            label = new Label(0, x, String.valueOf(x + 1));
            sheet.addCell(label);
            Iterator iter = m.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                label = new Label(y += 1, x, null != val ? val.toString() : "");
                sheet.addCell(label);
            }
            x += 1;
        }
        wwb.write();
        wwb.close();
        os.flush();
        os.close();
    }

    public String Statistics(){
        return "wxstatistics";
    }

    public String Fetchstatistics() throws UnsupportedEncodingException {
//        List<HashMap> orderInfo = null;
//        if (("1,2").indexOf(getRequest().getSession().getAttribute("roleval").toString())>0) {
//            orderInfo = OrderInfo.getOrderStatistics(Long.parseLong(getRequest().getSession().getAttribute("uid").toString()));
//        }
//        return  AjaxActionComplete(orderInfo);
        try{
        Map<Object, Object> param= new HashMap<>();
        param.put("merchantid", ProjectSettings.getId());
        if (null!=getParameter("startdate") && !getParameter("startdate").equals("")){
            param.put("startdate", getParameter("startdate"));
        }
        if (null!=getParameter("enddate") && !getParameter("enddate").equals("")){
            param.put("enddate", getParameter("enddate").toString().concat(" 23:59:59"));
        }
        if (null!=getParameter("submchid") && !getParameter("submchid").equals("")){
            param.put("submchid", getParameter("submchid"));
        }
        if (null!=getParameter("salename") && !getParameter("salename").equals("")){
            param.put("salename", getParameter("salename"));
        }
        if (null!=getParameter("storename") && !getParameter("storename").equals("")){
            param.put("storename", java.net.URLDecoder.decode(java.net.URLDecoder.decode(
                    getParameter("storename").toString(), "UTF-8"),"UTF-8"));
        }
        int currpagenum = 1;
        if (null!=getParameter("currpagenum") && !getParameter("currpagenum").equals("0")){
                if ( Integer.parseInt(getParameter("currpagenum").toString())>0){
                    currpagenum = Integer.parseInt(getParameter("currpagenum").toString());}
        }
        List<Long> submchidlist = null;
        if (("1,2").indexOf(getRequest().getSession().getAttribute("roleval").toString())>0)
            submchidlist = UserInfo.getsubmchidlist(Long.parseLong(getRequest().getSession().getAttribute("uid").toString()));
        String submchidstring = "'123'";
        if (null!=submchidlist){
            for (Long m : submchidlist) {
                submchidstring = submchidstring.concat(",'"+String.valueOf(m)+"'");
            }
        }
        if (! submchidstring.equals("'123'")) {
            param.put("submchlist", submchidstring);
        }
        List<HashMap> orderInfo = OrderInfo.getOrderStatistics(param);
        List<HashMap> returnlist =  orderInfo.subList((currpagenum-1)*15,Math.min(currpagenum*15,orderInfo.size()));
        Map map=new HashMap<>();
        map.put("totalcount",orderInfo.size());
        map.put("pagecount",(orderInfo.size()+15-1)/15);
        returnlist.add(0, (HashMap) map);
        return  AjaxActionComplete(returnlist);
        }catch (Exception e) {
            Map map=new HashMap<>();
            map.put("errorMessage","查无信息");
            return AjaxActionComplete(map);
        }
    }

    public String Fetchbilldetail() throws UnsupportedEncodingException {
        Map<Object, Object> param= new HashMap<>();
        param.put("merchantid", ProjectSettings.getId());
        if (null!=getParameter("startdate") && !getParameter("startdate").equals("")){
            param.put("startdate", getParameter("startdate"));
        }
        if (null!=getParameter("enddate") && !getParameter("enddate").equals("")){
            param.put("enddate", getParameter("enddate").toString().concat(" 23:59:59"));
        }
        if (null!=getParameter("submchid") && !getParameter("submchid").equals("")){
            param.put("submchid", getParameter("submchid"));
        }
        if (null!=getParameter("outtradeno") && !getParameter("outtradeno").equals("")){
            param.put("bzorder", getParameter("outtradeno"));
        }
        if (null!=getParameter("amount") && !getParameter("amount").equals("") && (!getParameter("amount").toString().toLowerCase().contains("or"))){
            param.put("totalFee", getParameter("amount"));
        }

        if (null!=getParameter("checkbill") && !getParameter("checkbill").equals("")){
            param.put("checkbill","1");
        }
        int currpagenum = 1;
        if (null!=getParameter("currpagenum") && !getParameter("currpagenum").equals("0")){
            try{
                if ( Integer.parseInt(getParameter("currpagenum").toString())>0){
                    currpagenum = Integer.parseInt(getParameter("currpagenum").toString());}
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        List<Long> submchidlist = null;
        String submchidstring = "'123'";
        if (("1,2").indexOf(getRequest().getSession().getAttribute("roleval").toString())>0)
            submchidlist = UserInfo.getsubmchidlist(Long.parseLong(getRequest().getSession().getAttribute("uid").toString()));
        else if (getRequest().getSession().getAttribute("roleval").toString().equals("-1"))
            submchidstring = getRequest().getSession().getAttribute("uname").toString();
        if (null!=submchidlist){
            for (Long m : submchidlist) {
                submchidstring = submchidstring.concat(",'"+String.valueOf(m)+"'");
            }
        }
        if (! submchidstring.equals("'123'")) {
            param.put("submchlist", submchidstring);
        }
        List<HashMap> billInfo;
        float sum =0;
        float ratefee = 0;
        try {
            billInfo = BillInfo.getBillInfoListByMap(param);
        }catch (Exception e) {
            Map map=new HashMap<>();
            map.put("errorMessage","查无信息");
            return AjaxActionComplete(map);
        }
        List<HashMap> returnlist =  billInfo.subList((currpagenum-1)*15,Math.min(currpagenum*15,billInfo.size()));
        Map map=new HashMap<>();
        map.put("totalcount",billInfo.size());
        map.put("pagecount",(billInfo.size()+15-1)/15);
        returnlist.add(0, (HashMap) map);
        return  AjaxActionComplete(returnlist);
    }

    public String UpdBill(){
        try {
            if (getAttribute("roleval").equals("999")) {
                String sdate = getParameter("startdate").toString();
                sdate = sdate.replaceAll("-", "");
                DownLoadBill.dbill(sdate);
            }
        }
        catch (Exception e){
            return AjaxActionComplete(false);
        }
        return  AjaxActionComplete(true);
    }
}
