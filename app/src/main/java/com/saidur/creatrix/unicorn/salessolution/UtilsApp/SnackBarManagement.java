package com.saidur.creatrix.unicorn.salessolution.UtilsApp;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SnackBarManagement {

    public static void NoInternetSnackbar(View mContext){
        Snackbar snackbar = Snackbar.make(mContext, "No internet connection!", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.rgb(255, 51, 51));
        TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }


    // Custome Warning message snakcbar
    public static void _warning_CustomMessage(View mContext,String message){
        Snackbar snackbar = Snackbar.make(mContext, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.rgb(244, 235, 66));
        TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.BLACK);
        snackbar.show();
    }


    public static void _error_CustomMessage(View mContext,String message){
        Snackbar snackbar = Snackbar.make(mContext, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.rgb(255, 51, 51));
        TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
    public static void _success_CustomMessage(View mContext,String message){
        Snackbar snackbar = Snackbar.make(mContext, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.rgb(0, 128, 0));
        TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
