package com.qimpay.database.weixin;

import com.qimpay.database.WxDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillInfo {
    public static void main(String[] args) throws Exception {
    }
    public static List<HashMap> getBillInfoListByMap(Map param) {
        String statement = "com.qimpay.database.mapping.weixin.wxbill.getBillInfoListByMap";
        return WxDatabase.Instance().selectList(statement,param);
    }

    public static boolean insertbill(Map param) {
        String statement = "com.qimpay.database.mapping.weixin.wxbill.insertbill";
        return WxDatabase.Instance().insert(statement,param)>0;
    }

}
