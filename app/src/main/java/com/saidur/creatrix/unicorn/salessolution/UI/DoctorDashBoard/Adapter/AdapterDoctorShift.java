package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelPracticeShift;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;

public class AdapterDoctorShift extends RecyclerView.Adapter<AdapterDoctorShift.BookViewHolder> {
    public Context context;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private final List<ModelPracticeShift> aShftList;



    public AdapterDoctorShift(Context context, List<ModelPracticeShift> aShftList) {
        this.context = context;
        this.aShftList = aShftList;
    }

    @NonNull
    @Override
    public AdapterDoctorShift.BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_shift, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterDoctorShift.BookViewHolder holder, int position) {
        ModelPracticeShift ad = aShftList.get(position);

        holder.tv_shiftTypeName.setText(ad.getPracticeShift());
        holder.del.setOnClickListener(v -> {
            aShftList.remove(holder.getAdapterPosition());
            notifyItemChanged(holder.getAdapterPosition());
        });
        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return aShftList.size();
    }


    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_shiftTypeName;
        public ImageView del;


        public BookViewHolder(View view) {
            super(view);
            tv_shiftTypeName = (TextView) view.findViewById(R.id.tv_shiftTypeName);
            del = (ImageView) view.findViewById(R.id.iv_del);
        }
    }


    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


}