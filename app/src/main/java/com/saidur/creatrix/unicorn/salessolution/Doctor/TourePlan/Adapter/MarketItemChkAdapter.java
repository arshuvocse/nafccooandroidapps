package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelMarket;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Market;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.ArrayList;
import java.util.List;

public class MarketItemChkAdapter extends RecyclerView.Adapter<MarketItemChkAdapter.DICH> {
    Context c;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    List<ModelMarket> custList;
    CheckedMarketItem itmCkListener;
   /* boolean[] checkBoxState;
    boolean isChecked;
    String check;
    ListView users;
    int position;*/
    //Doc adds
/*    String[] listDoctor;
    boolean[] checkedItems;
    List<Integer> mUserItems = new ArrayList<>();*/

    List<ModelMarket> chkedItemList = new ArrayList<>();
    List<DoctorListViewModel> unchkedItemList = new ArrayList<>();
    public MarketItemChkAdapter(Context c, List<ModelMarket> custList, CheckedMarketItem itmCkListener) {
        this.c = c;
        this.custList = custList;
        this.itmCkListener = itmCkListener;
        //  Toast.makeText(c, "data "+docList.toString(), Toast.LENGTH_SHORT).show();

    }

    @NonNull
    @Override
    public DICH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_doclist_checkbox, parent, false);
       // c = parent.getContext();
        return new DICH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DICH holder, int position) {
        ModelMarket dl = custList.get(position);
        if (dl != null && custList.size()>0) {
            ModelMarket dll=new ModelMarket();
            holder.tv_custName.setText(dl.getMarketName());
            holder.chkitm.setOnClickListener(v -> {
                if (holder.chkitm.isChecked()) {
                    dll.setMarketId(dl.getMarketId());
                    dll.setMarketName(dl.getMarketName());
                    chkedItemList.add(dll);
                    itmCkListener.ckdItemMarketName(chkedItemList,position);
                } if(!holder.chkitm.isChecked()) {
                    chkedItemList.remove(dll);
                    itmCkListener.unckdItemMarketName(chkedItemList,position);
                }
            });
        } else {

            Toast.makeText(c, "Customer Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        if(custList.size()>0)
        {
            return custList.size();
        }else return 0;

    }

    public static class DICH extends RecyclerView.ViewHolder {
        TextView tv_custName;
        CheckBox chkitm;


        public DICH(@NonNull View v) {
            super(v);
            chkitm = v.findViewById(R.id.chkitm);
            tv_custName = v.findViewById(R.id.docname);
        }


    }
    public void filterListFun(List<ModelMarket> CustList) {
        this.custList = CustList;
    }
}

