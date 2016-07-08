package com.qimpay.weixin;

import com.framework.ProjectSettings;
import com.framework.utils.HttpUtils;
import com.framework.utils.XMLParser;
import com.qimpay.database.weixin.BillInfo;
import com.qimpay.database.weixin.MerchantInfo;
import com.qimpay.weixin.utils.Signature;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DownLoadBill {
    private static String DOWNLOADURL="https://api.mch.weixin.qq.com/pay/downloadbill";
    public static void main(String[] args) {
        try {
                dbill("20160613");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void dbill(String datestr) throws UnsupportedEncodingException, IllegalAccessException, ParseException {
        MerchantInfo merchantInfo =MerchantInfo.getMerchantInfoById(ProjectSettings.getId());
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", merchantInfo.getAppid());
        packageParams.put("mch_id", merchantInfo.getMchId());
        packageParams.put("nonce_str", "duizhangdan");
        packageParams.put("bill_date", datestr);//20151109
        packageParams.put("bill_type", "ALL");
        String sign = Signature.generateSign((Map)packageParams,merchantInfo.getApiKey());
        packageParams.put("sign", sign);

        String xml  ="<xml>"+ (XMLParser.mapToXMLTest2((Map)packageParams)).toString()+"</xml>";
        HttpPost httpPost = new HttpPost(DOWNLOADURL);
        httpPost.setEntity(new StringEntity(xml, "UTF-8"));
        String responseString = new String();
        try {
            try {
                responseString = HttpUtils.PostRequest(httpPost, (HttpResponse httpResponse) -> {
                    return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                });
                Map para = new HashMap();
                StringReader sr = new StringReader(responseString);
                BufferedReader in = new BufferedReader(sr);
                List<String>    slist    =    new    ArrayList<String>();slist.add("tradetime");
                slist.add("ghid");
                slist.add("mchid");
                slist.add("submch");
                slist.add("deviceid");
                slist.add("wxorder");
                slist.add("bzorder");
                slist.add("openid");
                slist.add("tradetype");
                slist.add("tradestatus");
                slist.add("bank");
                slist.add("currency");
                slist.add("totalmoney");
                slist.add("redpacketmoney");
                slist.add("wxrefund");
                slist.add("bzrefund");
                slist.add("refundmoney");
                slist.add("redpacketrefund");
                slist.add("refundtype");
                slist.add("refundstatus");
                slist.add("productname");
                slist.add("bzdatapacket");
                slist.add("fee");
                slist.add("rate");
                while ((in.read()!=-1)){
                    String tmpstr = in.readLine();
                    String[] arraystr = tmpstr.split(",`");
                    if (arraystr.length==24) {
                        para.clear();
                        for (int i = 1; i <= 24; i++) {
                            para.put(slist.get(i-1), arraystr[i - 1]);
                        }
                        try {
                            BillInfo.insertbill(para);
                        }
                        catch (Exception e){

                        }
                    }
                }
                System.out.println("insert bill over!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        finally {
            httpPost.abort();
        }

    }
//    public ActionForward dsdbill() throws Exception {
//        response.setContentType("text/text;charset=UTF-8");
//        request.setCharacterEncoding("UTF-8");
//        String rt = new String("");
//        String datastr = new String(request.getParameter("datas")
//                .getBytes("iso-8859-1"), "utf-8");
//
//
//        SortedMap<String, String> packageParams = new TreeMap<String, String>();
//        packageParams.put("appid", "wxbc6cb3102836e118");
//        packageParams.put("mch_id", "1241847602");
//        packageParams.put("nonce_str", "duizhangdan");
//        packageParams.put("bill_date", datastr);//20151109
//        packageParams.put("bill_type", "ALL");
//        RequestHandler reqHandler = new RequestHandler(null, null);
//        reqHandler.init("wxbc6cb3102836e118", "9778c54c04ff5d14f7f9c491badbc60b", "1241847602L124184760298765432100");
//        String sign = reqHandler.createSign(packageParams);
//        packageParams.put("sign", sign);
//
//        String xml  ="<xml>"+ (com.utils.Xmlutils.mapToXMLTest2((Map)packageParams)).toString()+"</xml>";
//        String createOrderURL = "https://api.mch.weixin.qq.com/pay/downloadbill";
//
//
//        String retrunstring = GetWxOrderno.getresult(createOrderURL, xml) ;
//        System.out.println("...");
//        Dbill dbill = new Dbill();
//        dbill.insertdb(retrunstring);
//        return  null;
//    }


}
