package com.qimpay.database;

import com.framework.database.DatabaseFramework;
import org.apache.ibatis.session.SqlSessionFactory;

public class WxDatabase extends DatabaseFramework {
    public static void main(String[] args) throws Exception {

    }

    static {
        String mybatisConfig = "com/qimpay/database/wxconf.xml";
        sqlSessionFactory_ = DatabaseFramework.buildSqlSessionFactory(mybatisConfig);
    }

    public static WxDatabase Instance() {
        return instance_;
    }

    private WxDatabase() {}

    protected SqlSessionFactory sqlSessionFactory() {
        return sqlSessionFactory_;
    }

    private static final WxDatabase instance_ = new WxDatabase();
    private static SqlSessionFactory sqlSessionFactory_;
}
