package com.saidur.creatrix.unicorn.salessolution.Interface;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelStationType;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelDay;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelDeliveryDay;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelWorkType;

import java.util.List;

public interface ITwwp {
    interface  Presenter{
        void getDay();
        void getDeliveryDay();
        void getTourType();
        void getWorkType();
    }
    interface View{
        void viewDay(List<ModelDay> days);
        void viewDeliveryDay(List<ModelDeliveryDay> ddays);
        void viewTourType(List<ModelStationType> ttypes);
        void viewWorkType(List<ModelWorkType> wtypes);
    }
}
