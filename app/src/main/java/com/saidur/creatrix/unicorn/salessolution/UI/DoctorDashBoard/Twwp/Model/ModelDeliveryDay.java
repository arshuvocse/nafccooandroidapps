package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model;

public class ModelDeliveryDay {
       int DeliveryDayNameId;
       String DeliveryDayName;

       public ModelDeliveryDay() {
       }

       public int getDeliveryDayNameId() {
              return DeliveryDayNameId;
       }

       public void setDeliveryDayNameId(int deliveryDayNameId) {
              DeliveryDayNameId = deliveryDayNameId;
       }

       public String getDeliveryDayName() {
              return DeliveryDayName;
       }

       public void setDeliveryDayName(String deliveryDayName) {
              DeliveryDayName = deliveryDayName;
       }

       @Override
       public String toString() {
              return DeliveryDayName;
       }
}
