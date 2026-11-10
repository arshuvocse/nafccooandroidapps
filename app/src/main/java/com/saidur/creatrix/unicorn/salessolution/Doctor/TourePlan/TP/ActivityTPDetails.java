package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.TP;



import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Adapter.CheckedCustomerItem;
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Adapter.CustomerItemChkAdapter;
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.TourPlanDtailsaAdapter;
import com.saidur.creatrix.unicorn.salessolution.Interface.DeleteListener;
import com.saidur.creatrix.unicorn.salessolution.Interface.IMarketStracture;
import com.saidur.creatrix.unicorn.salessolution.Interface.ITourplan;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelArea;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelGroup;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelRegion;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.MonthDate;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPurposeViewModel;
import com.saidur.creatrix.unicorn.salessolution.Presentar.MarketStructurePresenter;
import com.saidur.creatrix.unicorn.salessolution.Presentar.TourPlanPresenter;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.SessionManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityTpdetailsBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopTourplanAddMarketwiseBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopTourplanEditMarketwiseBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivityTPDetails extends AppCompatActivity implements IMarketStracture.View, ITourplan.View, DeleteListener, CheckedCustomerItem {
ActivityTpdetailsBinding binding;
    //Popup Layout
    PopTourplanAddMarketwiseBinding pbinding;
    PopTourplanEditMarketwiseBinding pbindingEdit;
    int selectedRegionId, selectedAreaId, selectedTeriId, selectedSTeri, selectedMarket, selectedTPP, empId, SN;
    String selectedMarketName, selectedTPPName;
   MarketStructurePresenter mpresenter;
    TourPlanPresenter tppresenter;
    String roleType;

    SessionManagement session;
    HashMap<String, String> user;

    TourPlanDtailsaAdapter adapter;
    SimpleDateFormat dateFormat;
    String ddy;
    Dialog popupTPP, popupTPPEdit;
    DBCrudHelper dbCrudHelper;
    List<TourPurposeViewModel> tppList;
    MonthDate monthDatel;
    List<TourPlanViewModel> tpl = new ArrayList<>();


    List<ModelCustomer> aCustomerList = new ArrayList<>();
    List<ModelCustomer> chkCustomerList = new ArrayList<>();
    String[] listItemCustomer_Customer;
    boolean[] checkedItems_Customer;
    ArrayList<Integer> mUserItems_Cust = new ArrayList<>();


    CustomerItemChkAdapter itemChkAdapter;
    Dialog popupCustomer;
    RecyclerView rv_customer;
    EditText srchview;
    TextView done_cust, cancel_cust, title;
    int a, b, c;
    String currentdate, edit_dName, Tedit_dName;


    private callbackHitApi_TP callback;
    public void setCallback(callbackHitApi_TP callback) {
        this.callback = callback;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTpdetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbCrudHelper = new DBCrudHelper(ActivityTPDetails.this);
        mpresenter = new MarketStructurePresenter(this, this);
        tppresenter = new TourPlanPresenter(this, this);

        session = new SessionManagement(getApplicationContext());
        user = session.getUserDetails();

        empId = Integer.parseInt(user.get(SessionManagement.KEY_EmpId));
        roleType = user.get(SessionManagement.KEY_EmpRoleType);
    }
    public interface callbackHitApi_TP{
        void onCallBackTP(String month,String year);
    }
    @Override
    public void ckdItemName(List<ModelCustomer> st, int Pos) {

    }

    @Override
    public void unckdItemName(List<ModelCustomer> st, int Pos) {

    }

    @Override
    public void deleteItemFromServer(int pos, int id) {

    }

    @Override
    public void deleteItem(int pos) {

    }

    @Override
    public void editItem(int pos, int id, int rid, int aid, int tid, int stid, int mid, String region, String area, String territory, String subTerritory, String market) {

    }
 @Override
    public void editTourPlanInfo(int pos, int id) {

    }

    @Override
    public void vGroup(List<ModelGroup> groupList) {

    }

    @Override
    public void vRegion(List<ModelRegion> regionList) {

    }

    @Override
    public void vArea(List<ModelArea> areaList) {

    }

    @Override
    public void vTeritory(List<ModelTeritorry> teritoryList) {

    }

    @Override
    public void vSTeritory(List<ModelSubTeritorry> steritoryList) {

    }

    @Override
    public void vMarket(List<ModelMarket> marketList) {

    }

    @Override
    public void OnTourPlanDataGet(List<TourPlanViewModel> aList) {

    }

    @Override
    public void OnTourPlanDailyDataGet(List<MonthDate> aList) {

    }

    @Override
    public void OnArreangList(List<MonthDate> aMondateList, boolean is_Entry, List<TourPlanViewModel> aTpLIst) {

    }

    @Override
    public void OnFailour(String msg) {

    }

    @Override
    public void OnSuccessTPPDay(String msg) {

    }
}