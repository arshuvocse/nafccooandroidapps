package com.saidur.creatrix.unicorn.salessolution.UtilityHelper;

import android.content.Context;
import android.widget.Toast;

public class ToastManagment {

    public static void GetLongToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static void SlowNetworkToast(Context context){
        Toast.makeText(context,"Slow network detected. Please try again",Toast.LENGTH_LONG).show();
    }
}
