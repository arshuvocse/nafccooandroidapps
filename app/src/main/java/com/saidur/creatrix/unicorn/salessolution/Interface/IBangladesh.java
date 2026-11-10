package com.saidur.creatrix.unicorn.salessolution.Interface;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDistrict;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDivision;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelThana;

import java.util.List;

public interface IBangladesh {
    interface  Presenter{
        void GetDivision();
        void GetDistrict(int grpid);
        void GetThana(int regid);
        void GetTeritoryLocal(int areaid);
        void GetSTeritoryLocal(int terid);
        void GetMarketLocal(int sterid);
    }
    interface View{
        void viewDivision(List<ModelDivision> divisionList);
        void viewDistict(List<ModelDistrict> districtList);
        void viewThana(List<ModelThana> thanaList);
    }
}
