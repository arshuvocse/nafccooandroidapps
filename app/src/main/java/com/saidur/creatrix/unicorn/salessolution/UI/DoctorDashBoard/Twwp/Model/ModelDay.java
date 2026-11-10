package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model;

public class ModelDay {
       int DayNameValueId;
       String DayNameValue;

       public ModelDay() {
       }

       public int getDayNameValueId() {
              return DayNameValueId;
       }

       public void setDayNameValueId(int dayNameValueId) {
              DayNameValueId = dayNameValueId;
       }

       public String getDayNameValue() {
              return DayNameValue;
       }

       public void setDayNameValue(String dayNameValue) {
              DayNameValue = dayNameValue;
       }

       @Override
       public String toString() {
              return DayNameValue;
       }
}
