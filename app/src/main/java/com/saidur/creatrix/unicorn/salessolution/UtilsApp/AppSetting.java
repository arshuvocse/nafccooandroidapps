package com.saidur.creatrix.unicorn.salessolution.UtilsApp;

public class AppSetting {
    public static final String Base_Url_Test="http://194.233.66.180:415/";
//    public static final String Base_Url_updatedW="http://194.233.66.180:415/";
//
//
//    public static final String Base_Url_Order_new="http://194.233.66.180:416/";
    public static final String Base_Url_Order_test="http://194.233.66.180:416/";



    public static final String Base_Url_updatedW="http://103.244.247.93:198/";
//    public static final String Base_Url_updatedW="http://192.168.110.50:252/";


    public static final String Base_Url_Order_new="http://103.244.247.93:201/";
// public static final String Base_Url_Order_new="http://192.168.110.50:251/";
    public static boolean isEmptyString(String str) {
        return StringUtility.isEmpty(str);
    }
    public static boolean isNotEmptyString(String str) {
        return StringUtility.isNotEmpty(str);
    }
}
