package com.saidur.creatrix.unicorn.salessolution.Interface;

import com.saidur.creatrix.unicorn.salessolution.Model.ModelDistrict;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelDivision;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelThana;
import java.util.List;

public interface IDDT {
    interface View {
    void DivisionList(List<ModelDivision> alist);
    void DistrictList(List<ModelDistrict> alist);
    void ThanaList(List<ModelThana> alist);
    }
    interface Presenter {
        void getDivision();
        void getDistrict(int divid);
        void getThana(int dis);
    }
}
