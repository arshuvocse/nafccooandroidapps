package com.saidur.creatrix.unicorn.salessolution.Adapters;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelRxDetails;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;
import java.util.Locale;

import ozaydin.serkan.com.image_zoom_view.ImageViewZoom;

public class AdapterReportRxDetails extends RecyclerView.Adapter<AdapterReportRxDetails.BookViewHolder> {
    private Context context;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private List<ModelRxDetails> aList;
    onClickImg onclick;
    public AdapterReportRxDetails(List<ModelRxDetails> nList, Context context,onClickImg onclick) {
        this.aList = nList;
        this.context = context;
        this.onclick = onclick;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_dname, tv_odoctor,tv_subdate,tv_rxdate, tv_brand;
        ImageView iv_image;

        public BookViewHolder(View view) {
            super(view);
            tv_dname = (TextView) view.findViewById(R.id.tv_dname);
            tv_odoctor = (TextView) view.findViewById(R.id.tv_odoctor);
            tv_subdate = (TextView) view.findViewById(R.id.tv_subdate);
            tv_rxdate = (TextView) view.findViewById(R.id.tv_rxdate);
            tv_brand = (TextView) view.findViewById(R.id.tv_brand);
            iv_image = (ImageView) view.findViewById(R.id.iv_image);

        }
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_rxdetails, parent, false);
        context = parent.getContext();
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        if (aList != null) {
            try {
                ModelRxDetails rd = aList.get(position);
                holder.tv_brand.setText(rd.getProductName());
                holder.tv_dname.setText(rd.getDoctorName());
                holder.tv_odoctor.setText(rd.getOtherDoctor());
                holder.tv_subdate.setText(rd.getDateofsubmission());
                holder.tv_rxdate.setText(rd.getDateofRx());

                try {
                    if(!rd.getRXImage().equals(""))
                    {
                        Glide.with(context)
                                .load(rd.getRXImage())
                                .fitCenter()
                                //.placeholder(R.drawable.loading_spinner)
                                .into(holder.iv_image);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                holder.iv_image.setOnClickListener(view -> {
                    onclick.onImageClick(rd.getRXImage());
                });

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }


        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        animation.setStartOffset(30 * position);//Provide delay here
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        if(aList!=null)
        {
            return aList.size();
        }
        return 0;
    }
    private void setFadeAnimation(View view) {
        AnimationSet set = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        view.startAnimation(animation);
        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(100);
        view.startAnimation(animation);
    }

public interface onClickImg{
        void onImageClick(String mg);
}
}
