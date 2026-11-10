package com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignMaster2;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignMasters;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CampaignModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.CheckedCampaignListener;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdapterCampaignPop extends RecyclerView.Adapter<AdapterCampaignPop.SingleViewHolder> {

    private Context context;

    private List<CampaignModel> campaignModelList;
    private List<CampaignModel> ChkcampaignModelList = new ArrayList<>();


    CampaignMasters campaignMasters = new CampaignMasters();
    List<CampaignMasters> campaignMastersList = new ArrayList<>();

    CampaignMaster2 campaignMaster2 = new CampaignMaster2();
    List<CampaignMaster2> campaignMaster2List = new ArrayList<>();

    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition = 0;
    CheckedCampaignListener itemchklistener;
    boolean isChecked = false;

    public AdapterCampaignPop(Context context, List<CampaignModel> campaignModelList, CheckedCampaignListener itemchklistener) {
        this.context = context;
        this.campaignModelList = campaignModelList;
        this.itemchklistener = itemchklistener;
    }

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_campaignshow, viewGroup, false);
        return new SingleViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder holder, int position) {
        CampaignModel cm = campaignModelList.get(position);
        List<CampaignModel> cList = new ArrayList<>();
        if (cm != null && campaignModelList.size() > 0) {
            CampaignModel dll = new CampaignModel();
            //holder.tv_docName.setText(dl.getDoctorName());
            if (cm.getCampaignName() != null) {
                holder.chkitm.setText(cm.getCampaignName());
            } else {
            }

            holder.chkitm.setChecked(true);
            if (String.valueOf(cm.getBonusProductId()) == null) {
                dll.setBonusProductId(0);
            } else {
                dll.setCampgainMasterId(cm.getCampgainMasterId());
                dll.setBonusProductId(cm.getBonusProductId());
            }
            ChkcampaignModelList.add(dll);
            itemchklistener.ckdItem(ChkcampaignModelList, position);
            boolean isConf = false;
            Optional<CampaignModel> campaignModel=campaignModelList.stream().filter(x->x.getBonusProductId()==cm.getBonusProductId() && x.getCampgainMasterId() !=cm.getCampgainMasterId()).findAny();
            if (campaignModel.isPresent()) {
                isConf = true;
            }

            if (isConf == true) {
                holder.chkitm.setChecked(false);
                //holder.chkitm.setEnabled(false);
                dll.setCampgainMasterId(cm.getCampgainMasterId());
                ChkcampaignModelList.remove(dll);
                itemchklistener.ckdItem(ChkcampaignModelList, position);
            }

            if (cm.getCampainTypeId() == 3 && isConf == false) {

                holder.chkitm.setEnabled(false);
                holder.chkitm.isChecked();
                dll.setCampgainMasterId(cm.getCampgainMasterId());
                if (String.valueOf(cm.getBonusProductId()) == null) {
                    dll.setBonusProductId(0);
                } else {
                    dll.setBonusProductId(cm.getBonusProductId());
                }
                ChkcampaignModelList.add(dll);
                itemchklistener.ckdItem(ChkcampaignModelList, position);

            } else {
                holder.chkitm.setOnClickListener(v -> {
                    if (holder.chkitm.isChecked()) {
                        if (ChkcampaignModelList.size() == 0) {
                            dll.setCampgainMasterId(cm.getCampgainMasterId());
                            if (String.valueOf(cm.getBonusProductId()) == null) {
                                dll.setBonusProductId(0);
                            } else {
                                dll.setBonusProductId(cm.getBonusProductId());
                            }

                            ChkcampaignModelList.add(dll);
                            itemchklistener.ckdItem(ChkcampaignModelList, position);
                        } else {
                            dll.setCampgainMasterId(cm.getCampgainMasterId());
                            if (String.valueOf(cm.getBonusProductId()) == null) {
                                dll.setBonusProductId(0);
                            } else {
                                dll.setBonusProductId(cm.getBonusProductId());
                            }
                            boolean hasProduct = false;
                            int bonousId = dll.getBonusProductId();
                            for (int i = 0; i < ChkcampaignModelList.size(); i++) {
                                int BonusId = ChkcampaignModelList.get(i).getBonusProductId();
                                if (bonousId == BonusId) {
                                    hasProduct = true;
                                    holder.chkitm.setChecked(false);
                                    ChkcampaignModelList.remove(dll);
                                    itemchklistener.ckdItem(ChkcampaignModelList, position);
                                }
                            }
                            if (hasProduct == false) {
                                ChkcampaignModelList.add(dll);
                                itemchklistener.ckdItem(ChkcampaignModelList, position);
                            }
                        }
                    }
                    if (!holder.chkitm.isChecked()) {
                        ChkcampaignModelList.remove(dll);
                        itemchklistener.ckdItem(ChkcampaignModelList, position);
                    }
                });
            }

        } else {
            Toast.makeText(context, "No Campaign ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return campaignModelList.size();
    }


    public CampaignModel getSelected() {
        if (checkedPosition != -1) {
            return campaignModelList.get(checkedPosition);
        }
        return null;
    }

    static class SingleViewHolder extends RecyclerView.ViewHolder {
        private CheckBox chkitm;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            chkitm = itemView.findViewById(R.id.radioCamp);
        }
        void bind2(List<CampaignModel> campaignModelList) {
        }
    }
}
