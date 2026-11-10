package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDCPItem;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDCPList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.ActivityDCR;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AdapterDcplist extends RecyclerView.Adapter<AdapterDcplist.VH> implements AdapterDcpDoctorlist.DcpDocClick {
    Context context;
    List<ModelDCPList> dList;
    DcpClick doctorClick;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds


    public AdapterDcplist(Context context, List<ModelDCPList> cList, DcpClick doctorClick) {
        this.context = context;
        this.dList = cList;
        this.doctorClick = doctorClick;
    }

    @NonNull
    @Override
    public AdapterDcplist.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_dcp_list, parent, false);
        return new AdapterDcplist.VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDcplist.VH holder, int position) {
        ModelDCPList dcp = dList.get(holder.getAdapterPosition());

        holder.date.setText(dcp.getDayValue());
        holder.day.setText(dcp.getDayNameStr());


        AdapterDcpDoctorlist dapter = new AdapterDcpDoctorlist(context, dcp.getDCPListDtl(),dcp.getDCPDate(),this::onDcpDocClick);
        holder.doclist.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        holder.doclist.setAdapter(dapter);
        dapter.notifyDataSetChanged();

        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return dList != null ? dList.size() : 0;
    }

    @Override
    public void onDcpDocClick(String dcpDate,ModelDCPItem dcpi, List<ModelDCPItem> dList) {
        Gson gson = new Gson();
        Constants.From = "DCP_DOCLIST";
        String udata = gson.toJson(dcpi);
        String udataList = gson.toJson(dList);

        Intent dc = new Intent(context, ActivityDCR.class);
        dc.putExtra("dcpDate", dcpDate);
        dc.putExtra("data", udata);
        dc.putExtra("dataList", udataList);
        context.startActivity(dc);
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView date, day, doctorname, worksession, tourtype;
        RecyclerView doclist;

        public VH(@NonNull View v) {
            super(v);
            date = (TextView) v.findViewById(R.id.date);
            day = (TextView) v.findViewById(R.id.day);
            doctorname = (TextView) v.findViewById(R.id.doctorname);
            worksession = (TextView) v.findViewById(R.id.worksession);
            tourtype = (TextView) v.findViewById(R.id.tourtype);
            doclist = (RecyclerView) v.findViewById(R.id.doclist);
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public interface DcpClick {
        Dialog onCreateDialog(Bundle savedInstanceState);

        void onClick(int id);
    }
}
