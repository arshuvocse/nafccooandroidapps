package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.Adapter.AdapterSalesReportSlider;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityReportSalesBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportSalesActivity extends AppCompatActivity {
    ActivityReportSalesBinding binding;

    ModelSession session;
    String params, role;
    String tagST,tagT,tagA, tagR, tagN, STeriid,Teriid,Areaid, Regionid, Groupid;
    DBCrudHelper dbCrudHelper;

    int RoleTypeId;
    String RoleType;
    FragmentManager fm;
    AdapterSalesReportSlider fadapter;
    String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportSalesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbCrudHelper = new DBCrudHelper(ReportSalesActivity.this);
        session=dbCrudHelper.getSession();
        role = session.getEmpRole();
        RoleTypeId = session.getRoleTypeId();
        RoleType = session.getRoleType();
        from = getIntent().getStringExtra("From");
        tagST = ".EmpSTeritorryId=";
        tagT = ".EmpTeritorryId=";
        tagA = ".EmpAreaId=";
        tagR = ".EmpRegionId=";
        tagN = ".EmpGroupId=";
        switch (role) {
            case "MBE":
                STeriid = String.valueOf(dbCrudHelper.getCurrentUserSTeriId_SQLite());
                // params = "And tblApprovalLog" + tagA + Areaid;
                params = "AND View_Webapi_EmployeeFieldForceInfo" + tagST + STeriid;
                break;
            case "MIO":
                Teriid = String.valueOf(dbCrudHelper.getCurrentUserTeriId_SQLite());
                // params = "And tblApprovalLog" + tagA + Areaid;
                params = "AND View_Webapi_EmployeeFieldForceInfo" + tagT + Areaid;
                break;
            case "AM":
                Areaid = String.valueOf(dbCrudHelper.getCurrentUserAreaId_SQLite());
                // params = "And tblApprovalLog" + tagA + Areaid;
                params = "AND View_Webapi_EmployeeFieldForceInfo" + tagA + Areaid;
                break;
            case "DZSM":
                Regionid = String.valueOf(dbCrudHelper.getCurrentUserRegionId_SQLite());
                params = "AND View_Webapi_EmployeeFieldForceInfo" + tagR + Regionid;
                break;
            case "NSM":
                Groupid = String.valueOf(dbCrudHelper.getCurrentUserGroupId_SQLite());
                params = "AND View_Webapi_EmployeeFieldForceInfo" + tagN + Groupid;
                break;

            case "Admin":
                params = "";
                break;
        }
        prepareTab();
    }
    public void prepareTab() {
        binding.approvetab.setTabGravity(TabLayout.GRAVITY_FILL);
        fm = getSupportFragmentManager();
        fadapter = new AdapterSalesReportSlider(fm, getLifecycle(), this,from/*,tabname,tcc*/);
        binding.salesViewpager.setAdapter(fadapter);
        final List<String> colors = new ArrayList<String>() {
            {
                add("#D6EFF3");
                add("#FFFFFF");
            }
        };
        new TabLayoutMediator(binding.approvetab, binding.salesViewpager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        LayoutInflater layoutInflater = LayoutInflater.from(ReportSalesActivity.this);
                        View tabView = layoutInflater.inflate(R.layout.tab_custom_layout_sales, null, false);
                        TextView tabtag = (TextView) tabView.findViewById(R.id.tabtagz);
                        tab.setCustomView(tabView);
                        tab.view.setBackgroundColor(Color.parseColor(colors.get(position)));
                        switch (position) {
                            case 0:
                                tabtag.setText("Customer Wise");
                                break;
                            case 1:
                                tabtag.setText("Product Wise");
                                break;

                        }

                    }
                }).attach();
         prepareViewpager();
    }
    private void prepareViewpager() {
        binding.approvetab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.salesViewpager.setCurrentItem(tab.getPosition());
                final List<String> colors = new ArrayList<String>() {
                    {
                        add("#D6EFF3");
                        add("#F0FBF6");
                    }
                };

                if (tab.getPosition() == 0) {
                    tab.view.setBackgroundColor(Color.parseColor(colors.get(0)));
                } else {
                    tab.view.setBackgroundColor(Color.parseColor(colors.get(tab.getPosition())));
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                final List<String> white = new ArrayList<String>() {
                    {
                        add("#FFFFFF");
                        add("#FFFFFF");
                        //add("#FFFFFF");
                        // add("#FFFFFF");
                    }
                };
                //tab.view.setAlpha(1);
                tab.view.setBackgroundColor(Color.parseColor(white.get(tab.getPosition())));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.salesViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.approvetab.selectTab(binding.approvetab.getTabAt(position));
            }
        });
    }
}