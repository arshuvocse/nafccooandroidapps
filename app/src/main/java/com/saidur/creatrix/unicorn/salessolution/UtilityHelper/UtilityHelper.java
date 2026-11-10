package com.saidur.creatrix.unicorn.salessolution.UtilityHelper;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UtilityHelper {
    private static final String[] monthNameArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static String[] monthNameArrayFull = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    static DatePickerDialog picker;

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

    public static void _datePickNum_DisableOldDates(TextView controlName, Context context) {
        final Calendar cldr = Calendar.getInstance();
        cldr.add(Calendar.DATE, 0);
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(context,
                (view, year1, monthOfYear, dayOfMonth) -> {

                    String attSelectedMonth = year1 + "-" + (monthOfYear+1) + "-" + dayOfMonth;
                    controlName.setText(attSelectedMonth);
                }, year, month, day);
        picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        picker.show();
    }
    public static void _datePickNum_DisableOldDatesNextMonth(TextView controlName, Context context) {
        final Calendar cldr = Calendar.getInstance();
        cldr.add(Calendar.DATE, 0);  // Current date
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        // Get current date and calculate the maximum allowed date (30 days ahead)
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.DATE, 30);  // Adding 30 days

        // Create DatePickerDialog
        picker = new DatePickerDialog(context,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String attSelectedMonth = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                    controlName.setText(attSelectedMonth);
                }, year, month, day);

        // Set the minimum date to today (no past dates allowed)
        picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        // Set the maximum date to 30 days ahead
        picker.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

        // Show the DatePickerDialog
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

    public static <T> List<T> getList(String jsonArray, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return new Gson().fromJson(jsonArray, typeOfT);
    }

}
