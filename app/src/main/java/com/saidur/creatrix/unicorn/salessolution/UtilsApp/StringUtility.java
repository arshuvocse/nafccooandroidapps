package com.saidur.creatrix.unicorn.salessolution.UtilsApp;

public class StringUtility {

    public StringUtility() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !StringUtility.isEmpty(str);
    }
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isNotBlank(String str) {
        return StringUtility.isBlank(str);
    }
}
