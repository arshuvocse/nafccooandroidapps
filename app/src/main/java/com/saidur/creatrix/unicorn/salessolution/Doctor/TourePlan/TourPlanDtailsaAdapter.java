package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.saidur.creatrix.unicorn.salessolution.Interface.DeleteListener;
import com.saidur.creatrix.unicorn.salessolution.Model.MarketStructure.LviewHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.MonthDate;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;

public class TourPlanDtailsaAdapter extends RecyclerView.Adapter<TourPlanDtailsaAdapter.VPDH> {
    //Context c;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private MonthDate monthDate;
    private List<TourPlanViewModel> vpModelList;
    //TPEditListener tpEditListener;
    DeleteListener dListener;

    Activity ac;

    public TourPlanDtailsaAdapter(Activity ac, MonthDate monthDate, DeleteListener dListener) {
        this.ac = ac;
        this.monthDate = monthDate;
        this.dListener = dListener;
        notifyDataSetChanged();
    }
    public TourPlanDtailsaAdapter(Activity ac, List<TourPlanViewModel> vpModelList, DeleteListener dListener) {
        this.ac = ac;
        this.vpModelList = vpModelList;
        this.dListener = dListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VPDH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tour_plan_details_item, parent, false);
//        c = parent.getContext();
        return new VPDH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VPDH holder, int position) {
      // TourPlanViewModel doclist = monthDate.getaTpViewList().get(position);
       TourPlanViewModel tpdata = vpModelList.get(position);
        Dialog d = new Dialog(ac);
        if (tpdata != null) {
            holder.tv_mname.setText(tpdata.getMarketName());
            holder.tv_tpp.setText(tpdata.getTPName());
            holder.sn.setText(String.valueOf(tpdata.getSerialNo()));
            if(tpdata.getIsMorning().equals("1"))
            {
                holder.iswhat.setText("Morning");
            }
            if(tpdata.getIsEvening().equals("1"))
            {
                holder.iswhat.setText("Evening");
            }

            if(tpdata.getIsStartTime().equals("1") && tpdata.getIsEndtime().equals("1"))
            {
                holder.tv_Time.setText("Start Time: "+tpdata.getStarttime().toString()+" "+"End Time: "+tpdata.getEndtime());
            }else if(tpdata.getIsStartTime().equals("1"))
            {
                holder.tv_Time.setText("Start Time: "+tpdata.getStarttime().toString());
            }else if(tpdata.getIsEndtime().equals("1"))
            {
                holder.tv_Time.setText("End Time: "+tpdata.getEndtime());
            }else {
                holder.tv_Time.setVisibility(View.GONE);
            }



          //  holder.sn.setText(String.valueOf(position+1));
            ArrayAdapter<ModelCustomer> custAd=new ArrayAdapter<>(ac,R.layout.lv_dcrbrand,R.id.dcrbrand,tpdata.getaCustomerMasterList());
            holder.lv_custs.setAdapter(custAd);
            LviewHelper.getListViewSize(holder.lv_custs);
            custAd.notifyDataSetChanged();

            holder.idelete.setOnClickListener(v ->{
                d.setContentView(R.layout.popup_delete);
                d.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
                TextView yes, no;
                yes = d.findViewById(R.id.yes);
                no = d.findViewById(R.id.no);
                d.show();
                yes.setOnClickListener(v1 -> {
                    d.dismiss();
                    if(tpdata.getTourPlanId()!=0)
                    {
                        dListener.deleteItemFromServer(position,tpdata.getTourPlanId());
                    }else {
                        dListener.deleteItem(position);
                    }

                    //notifyDataSetChanged();
                });
                no.setOnClickListener(v1 -> {
                    d.dismiss();
                });
              //  return false;

            });
            holder.edit.setVisibility(View.GONE);
            holder.edit.setOnClickListener(v -> {
                //tpEditListener.edit_Card(position);
                //tpdata.getMarketName();
                dListener.editItem(position,tpdata.getTourPlanId(),tpdata.getRegionId(),tpdata.getAreaId(),tpdata.getTerritoryId(),tpdata.getSubTerritoryId(),tpdata.getMarketId(),tpdata.getRegionName(),tpdata.getAreaName(),tpdata.getTerritoryName(),
                        tpdata.getSubTerritoryName(),tpdata.getMarketName());
            });

        }
    }
    @Override
    public int getItemCount() {
      //  return monthDate.getaTpViewList().size();
        return vpModelList.size();
    }

    public static class VPDH extends RecyclerView.ViewHolder {
        TextView sn,tv_mname, tv_tpp, iswhat,edit,delete,tv_Time;
        ImageView idelete;
        CardView cardView;
        ListView lv_custs;

        public VPDH(@NonNull View v) {
            super(v);
            sn = v.findViewById(R.id.sn);
            tv_mname = v.findViewById(R.id.tv_mname);
            tv_tpp = v.findViewById(R.id.tv_tpp);
            iswhat = v.findViewById(R.id.isWhat);
            tv_Time = v.findViewById(R.id.tv_Time);
            lv_custs = v.findViewById(R.id.custListLv);
            edit = v.findViewById(R.id.edit);
            delete = v.findViewById(R.id.delete);
            idelete = v.findViewById(R.id.idelete);
            cardView = v.findViewById(R.id.cardView);
        }
    }
    public interface TPEditListener{
        void edit_Card(int postion);
    }
}
