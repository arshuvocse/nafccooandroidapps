package com.saidur.creatrix.unicorn.salessolution.Database;

public class DatabaseInfomation {

    private  static  final  String DATABASE_NAME = "SalesLocalUnicornDB.db";
    private static  final  int DATABASE_VERSION= 2;

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }
}
