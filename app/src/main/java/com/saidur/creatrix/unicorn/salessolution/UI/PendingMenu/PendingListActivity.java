package com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu;
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
import com.saidur.creatrix.unicorn.salessolution.Interface.DeleteListeners;
import com.saidur.creatrix.unicorn.salessolution.Interface.IPendingCounter;
import com.saidur.creatrix.unicorn.salessolution.Interface.NotifyListener;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderDetails;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMaster;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterPendingCounter;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter.AdapterDraftOrder;
import com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.Adapter.AdapterFragment;
import com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.Model.DcrSM;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityPendingListBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PendingListActivity extends AppCompatActivity implements IPendingCounter.View, NotifyListener, DeleteListeners {
ActivityPendingListBinding binding;
    AdapterDraftOrder orderAdapter;
    //_pending_dcrListAdapter dcrAdapter;
    ModelSession session;
    PresenterPendingCounter presenter;
    HashMap<String, String> user;
    String empId;

    TabLayout tabLayout;
    AdapterFragment adapter;
    ViewPager2 viewPager;

    //OrderListVM orderListVM;
    String tdayshow;
    FragmentManager fm;
    String countDCR, countPrs, countSO, countCO;
    TextView tabtag, tabitemcounts;
    DBCrudHelper dbCrudHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPendingListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbCrudHelper=new DBCrudHelper(PendingListActivity.this);
        presenter = new PresenterPendingCounter(this, this);
        presenter.totalDcr();
        presenter.totalPresc();
        presenter.totalOrder();
        presenter.totalOrderMaster();

        orderAdapter = new AdapterDraftOrder(this, this, this::onNotify, this::onLongClick);
        orderAdapter.notifyDataSetChanged();
        //dcrAdapter = new _pending_dcrListAdapter(this, this);
       // dcrAdapter.notifyDataSetChanged();

        session = dbCrudHelper.getSession();
        empId =String.valueOf(session.getEmpInfoId());
        //TODO: DCR,SampleOrder,CommercialOrder --Lists
        tdayshow = new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(new Date());
        prepareTab();
    }
    public void prepareTab() {
        tabLayout = (TabLayout) findViewById(R.id.pendingtab);
        viewPager = (ViewPager2) findViewById(R.id.pendingViewpager);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        fm = getSupportFragmentManager();
        adapter = new AdapterFragment(fm, getLifecycle(), this/*,tabname,tcc*/);
        viewPager.setAdapter(adapter);
        final List<String> colors = new ArrayList<String>() {
            {
                //add("#DADADA");
                add("#EEF8FA");
                add("#FFFFFF");
                //  add("#FFFFFF");
                add("#FFFFFF");
            }
        };
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    LayoutInflater layoutInflater = LayoutInflater.from(PendingListActivity.this);
                    View tabView = layoutInflater.inflate(R.layout.tab_custom_layout, null, false);
                    tabtag = (TextView) tabView.findViewById(R.id.tabtagz);
                    tabitemcounts = (TextView) tabView.findViewById(R.id.tabitemcounts);
                    tab.setCustomView(tabView);

                    tab.view.setBackgroundColor(Color.parseColor(colors.get(position)));
                    switch (position) {
                        case 0:
                            try {
                                tabtag.setText("DCR");
                                tabitemcounts.setText(countDCR);
                            } catch (Exception ex) {
                            }

                            break;
                        case 1:
                            try {
                                tabtag.setText("Prescription");
                                tabitemcounts.setText(countPrs);
                            } catch (Exception ex) {
                            }

                            break;
                        case 2:
                            try {
                                tabtag.setText("Order");
                                tabitemcounts.setText(countCO);
                            } catch (Exception ex) {
                            }
                            break;
                    }

                }).attach();
        prepareViewpager();
    }

    private void prepareViewpager() {
        //Selected Shade Color
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                final List<String> colors = new ArrayList<String>() {
                    {
                        add("#EEF8FA");
                        add("#F0FBF6");
                        // add("#DADADA");
                        add("#EFD8D7");
                    }
                };

                if (tab.getPosition() == 0) {
                    // tab.view.setBackgroundColor(Color.parseColor("#DADADA"));
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
                        add("#FFFFFF");
                    }
                };
                //tab.view.setAlpha(1);
                tab.view.setBackgroundColor(Color.parseColor(white.get(tab.getPosition())));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

/*    @Override
    public void totalDcr(List<DcrSM> dcrList) {
        try {
            if (dcrList != null) {
                int icountAD = dcrList.size();
                countDCR = String.valueOf(icountAD);
            } else {
                countDCR.equals("0");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void totalPresc(List<PrescriptionSM> preList) {

        try {
            if (preList != null) {
                int icountAP = preList.size();
                countPrs = String.valueOf(icountAP);
            } else {
                countPrs.equals("0");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }*/


    @Override
    public void totalOrder(List<OrderDetails> oList) {
     /*   try {
            if(oList!=null)
            {
                int icountAO = oList.size();
                countCO = String.valueOf(icountAO);
            }
            else {
                //tabitemcounts.setText("0");
                countCO.equals("0");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }*/
    }

    @Override
    public void totalOrderMaster(List<OrderMaster> oList) {
        try {
            if (oList != null) {
                int icountAO = oList.size();
                countCO = String.valueOf(icountAO);
            } else {
                //tabitemcounts.setText("0");
                countCO.equals("0");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //  Toast.makeText(this, "resta", Toast.LENGTH_SHORT).show();
        presenter.totalDcr();
        presenter.totalPresc();
        presenter.totalOrder();
        presenter.totalOrderMaster();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.totalDcr();
        presenter.totalPresc();
        presenter.totalOrder();
        presenter.totalOrderMaster();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.totalDcr();
        presenter.totalPresc();
        presenter.totalOrder();
        presenter.totalOrderMaster();
    }

    @Override
    public void onNotify(boolean a) {
        onRestart();
        onResume();
    }

    @Override
    public boolean onLongClick(int position, int local) {
        onResume();
        return true;
    }
}