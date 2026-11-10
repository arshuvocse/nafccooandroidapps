package com.saidur.creatrix.unicorn.salessolution.UtilsApp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.DateTimeUtil;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UtilityHelper {
    private static final String[] monthNameArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static String[] monthNameArrayFull = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    static DatePickerDialog picker;
    static TimePickerDialog pickerTime;


    public static void _datePickerDialogeForDates(TextView controlName, Context context) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(context,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String attSelectedMonth = DateTimeUtil.getDisplayDate(year1, monthOfYear, dayOfMonth);
                    controlName.setText(attSelectedMonth);

                }, year, month, day);
        picker.show();
    }


    public static void _datePickerDialogeForDates_DisableOldDates(TextView controlName, Context context) {
        final Calendar cldr = Calendar.getInstance();
        cldr.add(Calendar.DATE, 0);
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(context,
                (view, year1, monthOfYear, dayOfMonth) -> {

                    String attSelectedMonth = DateTimeUtil.getDisplayDate(year1, monthOfYear, dayOfMonth);
                    controlName.setText(attSelectedMonth);
                }, year, month, day);
        picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        picker.show();
    }

    @SuppressLint("SetTextI18n")
    public static void _datePickerDialogeForOmmit7day_DisableNextDates(TextView controlName, Context context) {
        final Calendar cldr = Calendar.getInstance();
        cldr.add(Calendar.DATE, 0);
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(context,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String attSelectedMonth = DateTimeUtil.getDisplayDate(year1, monthOfYear, dayOfMonth);
                    String time=new SimpleDateFormat("hh:mm a",Locale.US).format(new Date());
                    controlName.setText(attSelectedMonth+" "+time);
                }, year, month, day);
        picker.getDatePicker().setMinDate(System.currentTimeMillis() - 604800000);
        picker.getDatePicker().setMaxDate(System.currentTimeMillis());
        picker.show();
    }

    public static void _datePickerDialogeForDates_DisableNextDates(TextView controlName, Context context) {
        final Calendar cldr = Calendar.getInstance();
        cldr.add(Calendar.DATE, 0);
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(context,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String attSelectedMonth = DateTimeUtil.getDisplayDate(year1, monthOfYear, dayOfMonth);
                    controlName.setText(attSelectedMonth);
                }, year, month, day);
        // picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        picker.getDatePicker().setMaxDate(System.currentTimeMillis());
        picker.show();
    }

    public static String _GetCurrentDate() {
        return DateTimeUtil.getDisplayDateNow();
    }

    @SuppressLint("SetTextI18n")
    public static void _timePicker(TextView controlName, Context context) {
      /*  final Calendar cldr = Calendar.getInstance();
        cldr.add(Calendar.DATE, 0);
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(context,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String attSelectedMonth = dayOfMonth + "-" + monthNameArray[monthOfYear] + "-" + year1;
                    String time=new SimpleDateFormat("hh:mm a",Locale.getDefault()).format(new Date());
                    controlName.setText(attSelectedMonth+" "+time);
                }, year, month, day);
        picker.getDatePicker().setMinDate(System.currentTimeMillis() - 604800000);
        picker.getDatePicker().setMaxDate(System.currentTimeMillis());
        picker.show();*/

        final int[] thour = {0};
        final int[] tmin = { 0 };

        pickerTime = new TimePickerDialog(context,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                (timePicker, hour, min) -> {
                   // thour = hour;
                   // tmin = min;
                    String time = hour + ":" + min;
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm", Locale.US);
                        Date date = sdf.parse(time);
                        SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm aa", Locale.US);
                        final String timestring = sdf12.format(date);
                        controlName.setText(timestring);


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }, 12, 0, false);
        pickerTime.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerTime.show();
        //pickerTime.updateTime(thour, tmin);
    }


    public static <T> List<T> getList(String jsonArray, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return new Gson().fromJson(jsonArray, typeOfT);
    }

}
