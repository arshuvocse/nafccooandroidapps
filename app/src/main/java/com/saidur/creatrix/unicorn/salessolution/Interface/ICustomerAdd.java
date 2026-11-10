package com.saidur.creatrix.unicorn.salessolution.Interface;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.CustomerSvModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.CustomerType;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.ModelProviderType;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.ProgramType;
import com.saidur.creatrix.unicorn.salessolution.Model.Customer.StationType;

import java.util.List;

public interface ICustomerAdd {
    interface Presenter {
        void GetProgramType();
        void GetProviderType();
        void GetSMCType();
        void GetCustomerType();
        void GetStationType();
        void SaveCustomer(CustomerSvModel aInfo, String who);

    }

    interface View {
        /* void onTerritoryReceived(List<TerritoryViewModel> aList);
         void onMarketReceived(List<MarketViewModel> aList);
         void onDivisionReceived(List<DivisionVM> aList);
         void onDistrictReceived(List<DistrictVM> aList);
         void onThanaReceived(List<ThanaVM> aList);*/
        void onProgramType(List<ProgramType> ptype);
        void onProviderType(List<ModelProviderType> ptype);



        void onCustomerTypeReceived(List<CustomerType> aList);
        void onStationReceived(List<StationType> aList);
        void onSubmitSuccess(String mesg,String who);
        void onSubmitError(String mesg);

    }

}
