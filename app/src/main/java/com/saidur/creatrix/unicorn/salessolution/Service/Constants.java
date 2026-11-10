package com.saidur.creatrix.unicorn.salessolution.Service;

import java.util.HashMap;
import java.util.Map;

public class Constants {
  public static Map<String,String> filtermap=new HashMap<>();
  public static String filterparams="";




  public   static  final  int LOCATION_SERVICE_ID = 175;
    public static  final  String ACTION_START_LOCATION_SERVICE = "startLocationService";
    public static  final  String ACTION_STOP_LOCATION_SERVICE = "stopLocationService";
    public static  final  String LATITUDE = "LATITUDE";
    public static  final  String LONGITUDE = "LONGITUDE";
  public static String LastSyncTime="0";

  public static final String IMAGE_FILE_PREFIX = "Crieatrix_";
  public static final String IMAGE_FORMAT = ".jpg";
  public static final String IMAGE_FORMAT_SERVER = "JPEG";

  public static String SeenNotice = "";
  public static String SeenTraining = "";
  public static String Role = "";
  public static String WHO = "";
  public static String From = "";
  public static int current_selected_month = 0;
  public static int current_selected_year = 0;

  public static int current_selected_year_pos = -1;
  public static int current_selected_month_pos = -1;

  public static int current_vp_selected_year_pos = -1;
  public static int current_vp_selected_month_pos = -1;

  public static int current_dwsp_selected_year_pos = -1;
  public static int current_dwsp_selected_month_pos = -1;
}
