package com.saidur.creatrix.unicorn.salessolution.Interface;


import com.saidur.creatrix.unicorn.salessolution.Model.CustomerViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorCategory;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorContact;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorDegree;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorDesignation;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorSM;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorSpeciality;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelBrand;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelPracticeShift;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelGender;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelPriority;

import java.util.List;

public interface IDoctor {
    interface Presenter{
        void GetDegree(int doctypeId);
        void GetSpeciality();
        void GetDoctorDesignation(int id);
        void GetDoctorGender();
        void GetDoctorPracticeShift();

        void getCustomerListByMarketId(int MarketId);

        void GetContactType(int id);
        void GetBrand();
        void GetPriority();
        void GetDoctorCategory(int id);
        void SaveDoctor(DoctorSM doctorSM, String type);
    }
    interface View{
        void onDegreeGet(List<DoctorDegree> aList);
        void onSpecialityGet(List<DoctorSpeciality> aList);
        void onDoctorDesignationGet(List<DoctorDesignation> aList);
        void onDoctorGenderGet(List<ModelGender> aList);
        void onDoctorPracticeGet(List<ModelPracticeShift> aList);
        void onCustomerListByMarketIdGet(List<CustomerViewModel> aList);
        void onContactTypeGet(List<DoctorContact> aList);
        void onBrandGet(List<ModelBrand> aList);
        void onPriorityGet(List<ModelPriority> aList);
        void onDocCategoryGet(List<DoctorCategory> aList);
        void onSubmitSuccess(String mesg);
        void onSubmitError(String mesg);
    }
}
