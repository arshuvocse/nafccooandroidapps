package com.saidur.creatrix.unicorn.salessolution.UtilsApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.saidur.creatrix.unicorn.salessolution.UI.Authentication.LoginActivity;

import java.util.HashMap;

public class SessionManagement {
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "SalesAppSession";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_UserId = "UserId";
    public static final String KEY_UserName = "UserName";
    public static final String KEY_EmpId = "EmpId";
    public static final String KEY_EmpRole = "EmpRole";

    //int hobe
    public static final String KEY_EmpRoleTypeId = "RoleTypeId";
    public static final String KEY_EmpRoleType = "RoleType";
    public static final String KEY_EmpDesigName = "DesigName";

    public static final String KEY_EmpMasterCode = "EmpMasterCode";
    public static final String Extra = "Extra";
    public static final String CustomerCell = "CustomerCell";

    public static final String KEY_LoginName = "LoginName";
    public static final String KEY_Password = "Password";

    public static final String KEY_Forward = "IsForward";
    public static final String KEY_Accepted = "IsApprove";
    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(int UserId, String UserName,String EmpMasterCode,String LoginName,String Password,int empId,String extr,String cell,String Role,String RoleTypeId,String RoleType,String DesigName /*,String IsForward,String IsApprove*/){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_UserId, String.valueOf(UserId));
        editor.putString(KEY_UserName, UserName);
        editor.putString(KEY_EmpMasterCode, EmpMasterCode);
        editor.putString(KEY_LoginName, LoginName);
        editor.putString(KEY_Password, Password);
        editor.putString(KEY_EmpId, String.valueOf(empId));
        editor.putString(Extra, String.valueOf(extr));
        editor.putString(CustomerCell, String.valueOf(cell));
        editor.putString(KEY_EmpRole, Role);
        editor.putString(KEY_EmpRoleTypeId, RoleTypeId);
        editor.putString(KEY_EmpRoleType, RoleType);
        editor.putString(KEY_EmpDesigName, DesigName);

       /* editor.putString(KEY_Forward, IsForward);
        editor.putString(KEY_Accepted, IsApprove);*/
        editor.commit();

    }
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_UserId, pref.getString(KEY_UserId, null));
        user.put(KEY_UserName, pref.getString(KEY_UserName, null));
        user.put(KEY_EmpMasterCode, pref.getString(KEY_EmpMasterCode, null));
        user.put(KEY_LoginName, pref.getString(KEY_LoginName, null));
        user.put(KEY_Password, pref.getString(KEY_Password, null));
        user.put(KEY_EmpId, pref.getString(KEY_EmpId, null));
        user.put(Extra, pref.getString(Extra, null));
        user.put(CustomerCell, pref.getString(CustomerCell, null));
        user.put(KEY_EmpRole, pref.getString(KEY_EmpRole, null));
        user.put(KEY_EmpRoleTypeId, pref.getString(KEY_EmpRoleTypeId, null));
        user.put(KEY_EmpRoleType, pref.getString(KEY_EmpRoleType, null));
        user.put(KEY_EmpDesigName, pref.getString(KEY_EmpDesigName, null));

       /* user.put(KEY_Forward, pref.getString(KEY_Forward, null));
        user.put(KEY_Accepted, pref.getString(KEY_Accepted, null));*/
        // return user
        return user;
    }


    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK |i.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);

    }


    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK |i.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }/*else {
            Intent i = new Intent(_context, MainDashboardActivity.class);
            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK |i.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }*/

    }
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
