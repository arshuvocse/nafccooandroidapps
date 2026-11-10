package com.saidur.creatrix.unicorn.salessolution.Interface;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelStationType;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelDeliveryDay;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTransport;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelWorkType;

import java.util.List;

public interface ITwwpABM {
    interface  Presenter{

        void getDeliveryDay();
        void getTourType();
        void getWorkType();
        void getTransport();
        void getTeritorry();
        void getSTeritorry(int id);
    }
    interface View{
        void viewDeliveryDay(List<ModelDeliveryDay> ddays);
        void viewTourType(List<ModelStationType> ttypes);
        void viewWorkType(List<ModelWorkType> wtypes);
        void viewTransport(List<ModelTransport> wtypes);
        void viewTeritorry(List<ModelTeritorry> wtypes);
        void viewSTeritorry(List<ModelSubTeritorry> wtypes);
    }
}
