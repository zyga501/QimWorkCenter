package com.qimpay.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllMerchant {

    public static List<HashMap> getAllweixinsubmerchant(Map param) {
        String statement = "com.qimpay.database.mapping.allmerchant.getAllweixinsubmerchant";
        return Database.Instance().selectList(statement,param);
    }

    public static List<HashMap> getweixinsubmerchantuserBySid(long sid) {
        String statement = "com.qimpay.database.mapping.allmerchant.getweixinsubmerchantuserBySid";
        return Database.Instance().selectList(statement,sid);
    }

    public static List<HashMap> getweixinsubmerchantByUid(Map param) {
        String statement = "com.qimpay.database.mapping.allmerchant.getweixinsubmerchantByUid";
        return Database.Instance().selectList(statement,param);
    }

    public static boolean insertMerchantLink(Map param) {
        String statement = "com.qimpay.database.mapping.allmerchant.insertMerchantLink";
        return Database.Instance().insert(statement, param) > 0;
    }

    public static boolean deleteMerchantLink(List param) {
        String statement = "com.qimpay.database.mapping.allmerchant.deleteMerchantLink";
        return Database.Instance().insert(statement, param) > 0;
    }
}
