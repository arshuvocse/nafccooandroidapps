package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.Model.ModelRole;

import java.util.ArrayList;
import java.util.List;

public class AdapterRole extends RecyclerView.Adapter<AdapterRole.DICH> {
    Context c;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    List<ModelRole> roleList;
    List<ModelRole> ckdList = new ArrayList<>();
    onCheckedListener chklistener;
    public AdapterRole(Context c, List<ModelRole> roleList, onCheckedListener chklistener) {
        this.c = c;
        this.roleList = roleList;
        this.chklistener = chklistener;
    }

    @NonNull
    @Override
    public DICH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lv_row_role, parent, false);
        c = parent.getContext();
        return new DICH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DICH holder, int position) {
        ModelRole dl = roleList.get(position);
        Log.d("TAG", "onBindViewHolder: " + dl);
        holder.roleName.setText(dl.getRole());
        ModelRole dc = new ModelRole();
        holder.chkitm.setOnClickListener(v -> {
            if (holder.chkitm.isChecked()) {

                dc.setId(roleList.get(holder.getAdapterPosition()).getId());
                dc.setRole(roleList.get(holder.getAdapterPosition()).getRole());
                dc.setIschecked(true);
                ckdList.add(dc);
            }
            if (!holder.chkitm.isChecked()) {
                ckdList.remove(dc);
            }
            Gson gson = new Gson();
            String data = gson.toJson(ckdList);
            chklistener.onCheckedData(ckdList);
        });
    }
    @Override
    public int getItemCount() {
        return roleList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    public static class DICH extends RecyclerView.ViewHolder {
        TextView roleName;
        CheckBox chkitm;

        public DICH(@NonNull View v) {
            super(v);
            chkitm = v.findViewById(R.id.checkbox);
            roleName = v.findViewById(R.id.roleName);
        }
    }
    public interface onCheckedListener {
        void onCheckedData(List<ModelRole> ckdList);
    }
}
