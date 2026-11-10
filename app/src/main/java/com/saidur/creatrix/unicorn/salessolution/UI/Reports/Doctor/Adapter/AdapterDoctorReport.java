package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Doctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.ActivityDoctorEntry;

import java.util.ArrayList;
import java.util.List;


public class AdapterDoctorReport extends RecyclerView.Adapter<AdapterDoctorReport.VH> {
    Context context;
    List<DoctorListViewModel> dList;
    DoctorClick doctorClick;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds

    public AdapterDoctorReport(Context context, List<DoctorListViewModel> cList) {
        this.context = context;
        this.dList = cList;
        this.doctorClick = doctorClick;
    }

    @NonNull
    @Override
    public AdapterDoctorReport.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_doctor_list, parent, false);
        return new AdapterDoctorReport.VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDoctorReport.VH holder, int position) {
        DoctorListViewModel doctor = dList.get(holder.getAdapterPosition());

        holder.docName.setText(doctor.getDoctorName());
        holder.contact.setText(doctor.getDocContact());
        holder.gender.setText(doctor.getGender());
        holder.market.setText(doctor.getMarketName());
        holder.address.setText(doctor.getDoctorAddress());
        holder.potential.setText(doctor.getDoctorPatientStatus());
        holder.btnEdit.setOnClickListener(v -> {
            Context ctx = v.getContext();
            Intent i = new Intent(ctx, ActivityDoctorEntry.class);
            i.putExtra("Mode", "edit");
            i.putExtra("DoctorId", doctor.getDoctorId());        // <-- essential
            // (optional) more prefill data:
            i.putExtra("DoctorName", doctor.getDoctorName());
            i.putExtra("Mobile", doctor.getDocContact());
            ctx.startActivity(i);
        });
        setFadeAnimation(holder.itemView);
    }
    @Override
    public int getItemCount() {
        return dList != null ? dList.size() : 0;
    }
    public static class VH extends RecyclerView.ViewHolder {
        TextView docName, contact, gender,market,address,potential;
       ImageView btnEdit ;
        CardView docClickdv;

        public VH(@NonNull View v) {
            super(v);
            docName = (TextView) v.findViewById(R.id.docName);
            contact = (TextView) v.findViewById(R.id.contact);
            gender = (TextView) v.findViewById(R.id.gender);
            market = (TextView) v.findViewById(R.id.market);
            address = (TextView) v.findViewById(R.id.address);
            potential = (TextView) v.findViewById(R.id.potential);
            btnEdit = (ImageView) v.findViewById(R.id.btnEdit);

        }
    }
    public void filterList(ArrayList<DoctorListViewModel> aList) {
        dList = aList;
        notifyDataSetChanged();
    }
    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public interface DoctorClick{
        void onClick(int id);
    }
}
