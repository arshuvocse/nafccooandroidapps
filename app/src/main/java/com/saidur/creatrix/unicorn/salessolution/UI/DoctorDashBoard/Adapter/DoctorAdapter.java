package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;
import com.saidur.creatrix.unicorn.salessolution.R;
import java.util.ArrayList;
import java.util.List;


public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.VH> {
    Context context;
    List<DoctorListViewModel> dList;
    DoctorClick doctorClick;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds

    public DoctorAdapter(Context context, List<DoctorListViewModel> cList,DoctorClick doctorClick) {
        this.context = context;
        this.dList = cList;
        this.doctorClick = doctorClick;
    }

    @NonNull
    @Override
    public DoctorAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_doctor2_list, parent, false);
        return new DoctorAdapter.VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.VH holder, int position) {
        DoctorListViewModel doctor = dList.get(holder.getAdapterPosition());
        holder.tv_docName.setText(doctor.getDoctorName());
        holder.tv_contact.setText(doctor.getDocContact());
        holder.tv_market.setText(doctor.getMarketName());
        holder.docClickdv.setOnClickListener(v -> {
            doctorClick.onClick(doctor.getDoctorId());

            /*DoctorListViewModel sDoctor;
            Constants.WHO = "DoctorAdapter";
            sDoctor = dList.get(position);
            Intent intent = new Intent(context, PlaceOrderActivity.class);
            Gson gson = new Gson();
            String myJson = gson.toJson(sDoctor);
            intent.putExtra("myjson", myJson);
            context.startActivity(intent);*/
        });
        setFadeAnimation(holder.itemView);
    }
    @Override
    public int getItemCount() {
        return dList != null ? dList.size() : 0;
    }
    public static class VH extends RecyclerView.ViewHolder {
        TextView tv_docName, tv_contact, tv_market;
        CardView docClickdv;

        public VH(@NonNull View v) {
            super(v);
            tv_docName = (TextView) v.findViewById(R.id.tv_docName);
            tv_contact = (TextView) v.findViewById(R.id.tv_contact);
            tv_market = (TextView) v.findViewById(R.id.tv_market);
            docClickdv = (CardView) v.findViewById(R.id.docClickdv);
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
