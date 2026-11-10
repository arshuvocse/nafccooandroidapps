package com.saidur.creatrix.unicorn.salessolution.UI.Order.OrderTracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMaster;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiOrderProcess;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter.AdapterOrdertrackingPoruduct;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityOrderTrackingDetailsBinding;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderTrackingDetailsActivity extends AppCompatActivity {
    ActivityOrderTrackingDetailsBinding binding;
    ProgressDialog pd;
    public OrderMaster OrderList = new OrderMaster();
    private AdapterOrdertrackingPoruduct mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderTrackingDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int orderIdFromAc = getIntent().getExtras().getInt("myOrderId");
        int notificationId = getIntent().getExtras().getInt("nId");
        String fromPass = getIntent().getExtras().getString("from");


        if(orderIdFromAc > 0){
            GetOrderTracker(orderIdFromAc);
        }

       /* // This is for updating notification is read
        if(notificationId > 0){
            int readVal = getIntent().getExtras().getInt("readVal");
            if(readVal == 0){
                UpdateNotification(notificationId);
            }
        }*/
        binding.toolbarCustom.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.attRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderIdFromAc > 0){
                    GetOrderTracker(orderIdFromAc);

                }
            }
        });
    }

    private void GetOrderTracker(int orderId){

        pd = new ProgressDialog(OrderTrackingDetailsActivity.this);
        pd.setMessage("Order is Loading....");
        pd.show();
        pd.setCanceledOnTouchOutside(false);
        try{
            ApiOrderProcess service = RetrofitClientInstance.getRetrofitInstance().create(ApiOrderProcess.class);
            Call<OrderMaster> call = service.GetOrder_CustomerST(orderId);

            call.enqueue(new Callback<OrderMaster>() {
                @Override
                public void onResponse(@NonNull Call<OrderMaster> call, @NonNull Response<OrderMaster> response) {
                    if(pd!=null||pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    OrderList =  response.body();
                    SetData_InView(OrderList);
                }
                @Override
                public void onFailure(@NonNull Call<OrderMaster> call, @NonNull Throwable t) {
                    if(pd!=null||pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    if(t instanceof SocketTimeoutException){

                    }else{

                    }
                }
            });

        }catch (Exception ex){
            if(pd!=null||pd.isShowing())
            {
                pd.dismiss();
            }

        }

    }
    public void SetData_InView(OrderMaster aList) {
        if(aList !=null){

            TextView hOrderMId = (TextView)findViewById(R.id.hOrderMId);
            TextView order_no_txt = (TextView)findViewById(R.id.order_no_txt);
            TextView customer_name_txt = (TextView)findViewById(R.id.customer_name_txt);
            TextView gross_value_txt = (TextView)findViewById(R.id.gross_value_txt);
            TextView submited_date_txt = (TextView)findViewById(R.id.submited_date_txt);
            TextView mio_name_txt = (TextView)findViewById(R.id.mio_name_txt);
            order_no_txt.setText(aList.getOrderCode());
            customer_name_txt.setText(aList.getCustomerName());
            gross_value_txt.setText(aList.getGrossValue());

            String submDate = aList.getSubmDate();
            submited_date_txt.setText(submDate);
            mio_name_txt.setText(aList.getMIOName());
            hOrderMId.setText(String.valueOf(aList.getOrderId()));


            RelativeLayout btn_section = (RelativeLayout)findViewById(R.id.btn_section);

//            if(aList.getIsFromCustomer().equals("No")){
//
//                btn_section.setVisibility(View.GONE);
//            }else{
//                if(aList.getIsPending()==0){
//                    btn_section.setVisibility(View.VISIBLE);
//                }else{
//                    btn_section.setVisibility(View.GONE);
//                }
//
//            }


            ImageView approve_btn = (ImageView)findViewById(R.id.approve_btn);
            ImageView reject_btn = (ImageView)findViewById(R.id.reject_btn);

            approve_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(OrderTrackingDetailsActivity.this);
                    builder1.setMessage("Are you sure wants to approve the Order ?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ApproveOrder(aList.getOrderId());
                                    dialog.cancel();
                                }
                            });
                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
            });

            reject_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(OrderTrackingDetailsActivity.this);
                    builder1.setMessage("Are you sure wants to reject the Order ?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    RejectOrder(aList.getOrderId());

                                    dialog.cancel();
                                }
                            });
                    builder1.setNegativeButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            });

            SetTracker_View(aList);
            mAdapter = new AdapterOrdertrackingPoruduct(aList.getOrderDetails());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            binding.recyclerView.setLayoutManager(mLayoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(mAdapter);
            binding.recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                    DividerItemDecoration.VERTICAL));
            binding.recyclerView.setItemAnimator(null);
            binding.recyclerView.scrollToPosition(0);
            mAdapter.notifyDataSetChanged();
        }

    }
    public void SetTracker_View(OrderMaster aList){

        HorizontalStepView setpview5 = (HorizontalStepView) findViewById(R.id.step_view);
        List<StepBean> source = new ArrayList<>();
        int pending = aList.getIsPending();
        int confirm = aList.getIsConfirm();
        int inc = aList.getIsInvoiced();
   /*     source.add(new StepBean("Pending",pending));
        source.add(new StepBean("Confirmed",confirm));*/
        source.add(new StepBean("Invoiced",inc));
        source.add(new StepBean("Delivered",aList.getIsDelivered()));
        source.add(new StepBean("Payment",aList.getIsPayment()));

        setpview5.setStepViewTexts(source)
                .setTextSize(10)
                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#00C853"))//??StepsViewIndicator??????
                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#00C853"))//??StepsViewIndicator???????
                .setStepViewComplectedTextColor(Color.parseColor("#000000"))//??StepsView text??????
                .setStepViewUnComplectedTextColor(Color.parseColor("#000000"))//??StepsView text???????
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.ic_completed))//??StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, com.baoyachi.stepview.R.drawable.default_icon))//??StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, com.baoyachi.stepview.R.drawable.attention));

    }

    public  void ApproveOrder(int orderId){
        pd = new ProgressDialog(OrderTrackingDetailsActivity.this);
        pd.setMessage("Order is Approving....");
        pd.show();
        pd.setCanceledOnTouchOutside(false);
        try{
            ApiOrderProcess service = RetrofitClientInstance.getRetrofitInstance().create(ApiOrderProcess.class);
            Call<ResponseResultInfo> call = service.ApproveOrder_fromMio(orderId);

            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(Call<ResponseResultInfo> call, Response<ResponseResultInfo> response) {
                    if(pd!=null||pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    ResponseResultInfo aInfo = new ResponseResultInfo();
                    aInfo =  response.body();
                    if(aInfo.isSuccess==true){
                        new AlertDialog.Builder(OrderTrackingDetailsActivity.this)
                                .setTitle("Order Approved")
                                .setMessage("Approved successfully")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(OrderTrackingDetailsActivity.this,OrderTrackingActivity.class);
                                        startActivity(i);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    }

                                }).setCancelable(false).show();

                    }else{
                        Toast.makeText(getApplicationContext(),"Some Error Occurred. Please try again",Toast.LENGTH_LONG).show();
                    }

                }
                @Override
                public void onFailure(Call<ResponseResultInfo> call, Throwable t) {
                    if(pd!=null||pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    if(t instanceof SocketTimeoutException){

                    }else{

                    }
                }
            });

        }catch (Exception ex){
            if(pd!=null||pd.isShowing())
            {
                pd.dismiss();
            }

        }

    }

    public  void RejectOrder(int orderId){
        pd = new ProgressDialog(OrderTrackingDetailsActivity.this);
        pd.setMessage("Order is Rejecting....");
        pd.show();
        pd.setCanceledOnTouchOutside(false);
        try{
            ApiOrderProcess service = RetrofitClientInstance.getRetrofitInstance().create(ApiOrderProcess.class);
            Call<ResponseResultInfo> call = service.RejectOrder_fromMio(orderId);

            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {
                   if(pd!=null||pd.isShowing())
                   {
                       pd.dismiss();
                   }
                    ResponseResultInfo aInfo = new ResponseResultInfo();
                    aInfo =  response.body();
                    if(aInfo.isSuccess==true){
                        new AlertDialog.Builder(OrderTrackingDetailsActivity.this)
                                .setTitle("Order Rejected")
                                .setMessage("Rejected successfully")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(OrderTrackingDetailsActivity.this,OrderTrackingActivity.class);
                                        startActivity(i);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    }

                                }).setCancelable(false).show();

                    }else{
                        Toast.makeText(getApplicationContext(),"Some Error Occurred. Please try again",Toast.LENGTH_LONG).show();
                    }

                }
                @Override
                public void onFailure(@NonNull Call<ResponseResultInfo> call, @NonNull Throwable t) {
                    if(pd!=null||pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    if(t instanceof SocketTimeoutException){

                    }else{

                    }
                }
            });

        }catch (Exception ex){
            if(pd!=null||pd.isShowing())
            {
                pd.dismiss();
            }

        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
  /*  public void UpdateNotification(int id){
        try{
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ResponseResultInfo> call = service.UpdateNotificationIsRead(id);
            HttpUrl ds = call.request().url();
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {
                    ResponseResultInfo info =response.body();
                    if(info.getSuccess() == true){
                        Log.i("IsreadUpdate", "onResponse: "+info);
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ResponseResultInfo> call, @NonNull Throwable t) {
                    Log.i("IsreadUpdate", "Failur: "+t);
                    if(t instanceof SocketTimeoutException){
                    }else{

                    }
                }
            });

        }catch (Exception ex){
            Log.i("IsreadUpdate", "exception: "+ex);

        }

    }*/
}