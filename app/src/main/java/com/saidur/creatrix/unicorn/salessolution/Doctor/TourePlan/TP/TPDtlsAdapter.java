package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.TP;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.saidur.creatrix.unicorn.salessolution.Interface.DeleteListener;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.Market;
import com.saidur.creatrix.unicorn.salessolution.Model.MarketStructure.LviewHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;
import com.saidur.creatrix.unicorn.salessolution.Model.TourPlanViewModel;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TPDtlsAdapter extends RecyclerView.Adapter<TPDtlsAdapter.VPDH> {
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private List<TourPlanViewModel> vpModelList;
    DeleteListener dListener;
    boolean isFinalSubmit;
    Activity ac;
    SimpleDateFormat dateFormat;
    int type;
    public TPDtlsAdapter(Activity ac, List<TourPlanViewModel> vpModelList, DeleteListener dListener, boolean isFinalSubmit,int type) {
        this.ac = ac;
        this.vpModelList = vpModelList;
        this.dListener = dListener;
        this.isFinalSubmit = isFinalSubmit;
        this.type = type;
        //      notifyDataSetChanged();
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
            if(!TextUtils.isEmpty(tpdata.getVisitedWithEmpName()))
            {
                holder.tv_visited_with.setText("Visited With : "+tpdata.getVisitedWithEmpName());
            }


            if(tpdata.getIsMarketVisit().equals("1"))
            {
                holder.tv_mname.setText("Market Visit");
                holder.ll_startplace.setVisibility(View.VISIBLE);
                holder.ll_endplace.setVisibility(View.VISIBLE);
                holder.lbl_otherMarketV.setVisibility(View.VISIBLE);

            }
            if(tpdata.getIsOtherVisit().equals("1"))
            {
                holder.tv_mname.setText("Other Visit");
                holder.ll_startplace.setVisibility(View.GONE);
                holder.ll_endplace.setVisibility(View.GONE);
                holder.lbl_otherMarketV.setVisibility(View.GONE);

            }


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
            if (tpdata.getObjective() != null && !tpdata.getObjective().equals("")) {
                holder.tvObjective.setText(tpdata.getObjective());
                holder.lll2Objective.setVisibility(View.VISIBLE);
            } else {
                holder.lll2Objective.setVisibility(View.GONE); // Optional: hide the view if the objective is empty or null
            }


            if(tpdata.getIsMarketVisit().equals("1")) {
                holder.tv_Time.setText("Market: " + tpdata.getMarketName() + ", " + "Time: " + tpdata.getStarttime().toString());
                holder.tv_Place.setText("Market: " + tpdata.getMarketNameEnd() + ", " + "Time: " + tpdata.getEndtime().toString());

            }

            //  holder.sn.setText(String.valueOf(position+1));
            if(tpdata.getaCustomerMasterList().size()>0)
            {
                //ArrayAdapter<Customer> custAd = new ArrayAdapter<>(ac, R.layout.lv_dcrbrand, R.id.dcrbrand, vpModelList.get(holder.getAdapterPosition()).getaCustomerMasterList());
                ArrayAdapter<ModelCustomer> custAd = new ArrayAdapter<>(ac, R.layout.lv_dcrbrand, R.id.dcrbrand, tpdata.getaCustomerMasterList());
                holder.lv_custs.setAdapter(custAd);
                LviewHelper.getListViewSize(holder.lv_custs);
            }

            if(tpdata.getaVisitedMarketList().size()>0)
            {
                //ArrayAdapter<Customer> custAd = new ArrayAdapter<>(ac, R.layout.lv_dcrbrand, R.id.dcrbrand, vpModelList.get(holder.getAdapterPosition()).getaCustomerMasterList());
                ArrayAdapter<ModelMarket> marketAd = new ArrayAdapter<>(ac, R.layout.lv_dcrbrand, R.id.dcrbrand, tpdata.getaVisitedMarketList());
                holder.lv_market.setAdapter(marketAd);
                LviewHelper.getListViewSize(holder.lv_market);
            }

            // custAd.notifyDataSetChanged();
            /*  if (tpdata.isFinalSubmit() == false) {
                holder.idelete.setVisibility(View.VISIBLE);
            } else {
                if (tpdata.getApprovalStatus().equals("0")) {
                    isFinalSubmit = false;
                }
                if (tpdata.getApprovalStatus().equals("1")) {
                    isFinalSubmit = true;
                }
                if (tpdata.getApprovalStatus().equals("2")) {
                    isFinalSubmit = true;
                }
                if (tpdata.getApprovalStatus().equals("3")) {
                    isFinalSubmit = false;
                }
            }*/

            if(type==1)
            {
                if(!isFinalSubmit){
                    try {
                        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
                        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        Date Todate = null;
                        Date CurrentDate = null;
                        Todate = dateFormat.parse(today);
                        CurrentDate = dateFormat.parse(tpdata.getTourPlanDate());

                        if (CurrentDate != null && Todate != null) {
                            if (CurrentDate.before(Todate)) {
                                holder.idelete.setVisibility(View.GONE);
                                holder.iEdit.setVisibility(View.GONE);
                            } else {
                                holder.idelete.setVisibility(View.VISIBLE);
                                holder.iEdit.setVisibility(View.VISIBLE);
                                holder.iEdit.setVisibility(View.GONE);
                            }
                        } else {
                            holder.idelete.setVisibility(View.VISIBLE);
                            holder.iEdit.setVisibility(View.VISIBLE);
                            holder.iEdit.setVisibility(View.GONE);
                        }
                    } catch (ParseException e) {}
                }else {
                    holder.idelete.setVisibility(View.GONE);
                    holder.iEdit.setVisibility(View.GONE);
                }
            }else {
                holder.idelete.setVisibility(View.VISIBLE);
                holder.iEdit.setVisibility(View.VISIBLE);
                holder.iEdit.setVisibility(View.GONE);
            }


            if(tpdata.getTourPlanId()==0){
                holder.iEdit.setVisibility(View.GONE);
            }

            holder.idelete.setOnClickListener(v -> {
                d.setContentView(R.layout.popup_delete);
                d.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
                TextView yes, no;
                yes = d.findViewById(R.id.yes);
                no = d.findViewById(R.id.no);
                d.show();
                yes.setOnClickListener(v1 -> {
                    d.dismiss();
                    if (tpdata.getTourPlanId() != 0) {
                        dListener.deleteItemFromServer(position, tpdata.getTourPlanId());
                    } else {
                        dListener.deleteItem(position);
                    }

                    //notifyDataSetChanged();
                });
                no.setOnClickListener(v1 -> {
                    d.dismiss();
                });
                //  return false;

            });

            holder.iEdit.setOnClickListener(v -> {
                dListener.editTourPlanInfo(position, tpdata.getTourPlanId());


            });

            holder.edit.setVisibility(View.GONE);
            holder.edit.setOnClickListener(v -> {
                dListener.editItem(position, tpdata.getTourPlanId(), tpdata.getRegionId(), tpdata.getAreaId(), tpdata.getTerritoryId(), tpdata.getSubTerritoryId(), tpdata.getMarketId(), tpdata.getRegionName(), tpdata.getAreaName(), tpdata.getTerritoryName(),
                        tpdata.getSubTerritoryName(), tpdata.getMarketName());
            });
        }
    }

    @Override
    public int getItemCount() {
        return vpModelList.size();
    }

    public static class VPDH extends RecyclerView.ViewHolder {
        TextView sn, tv_mname, tv_tpp, iswhat,tv_Time, tv_Place, edit, delete,tv_visited_with,lbl_otherMarketV,tvObjective;
        LinearLayout ll_startplace, ll_endplace,lll2Objective;
        ImageView idelete, iEdit;
        CardView cardView;
        ListView lv_custs;
        ListView lv_market;

        public VPDH(@NonNull View v) {
            super(v);
            sn = v.findViewById(R.id.sn);
            tv_mname = v.findViewById(R.id.tv_mname);
            tv_visited_with = v.findViewById(R.id.tv_visited_with);
            tv_tpp = v.findViewById(R.id.tv_tpp);
            lv_custs = v.findViewById(R.id.custListLvMain);
            lv_market = v.findViewById(R.id.marketListLvMain);
            edit = v.findViewById(R.id.edit);
            delete = v.findViewById(R.id.delete);
            idelete = v.findViewById(R.id.idelete);
            iEdit = v.findViewById(R.id.iEdit);
            cardView = v.findViewById(R.id.cardView);
            iswhat = v.findViewById(R.id.isWhat);
            tv_Time = v.findViewById(R.id.tv_Time);
            tv_Place = v.findViewById(R.id.tv_Place);
            ll_startplace = v.findViewById(R.id.ll_startplace);
            ll_endplace = v.findViewById(R.id.ll_endplace);
            lbl_otherMarketV = v.findViewById(R.id.lbl_otherMarketV);
            lll2Objective = v.findViewById(R.id.lll2Objective);
            tvObjective = v.findViewById(R.id.tvObjective);
        }
    }

    public interface TPEditListener {
        void edit_Card(int postion);
    }
}
