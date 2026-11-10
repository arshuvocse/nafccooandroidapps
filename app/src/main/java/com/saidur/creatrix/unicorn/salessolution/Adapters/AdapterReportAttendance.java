package com.saidur.creatrix.unicorn.salessolution.Adapters;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelReportAttendance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.ActivityMap;

import java.util.List;
import java.util.Locale;

public class AdapterReportAttendance extends RecyclerView.Adapter<AdapterReportAttendance.BookViewHolder> {
    private Context context;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private List<ModelReportAttendance> aList;


    public AdapterReportAttendance(List<ModelReportAttendance> nList, Context context) {
        this.aList = nList;
        this.context = context;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView attDate, inTime, inAddress, inStatus,inWorkPlace, outtime, outAddress, outStatus,outWorkPlace, tv_session, tv_station;
        public ImageView attendanceImage;
        LinearLayout ll_in,ll_out;

        public BookViewHolder(View view) {
            super(view);
            attDate = (TextView) view.findViewById(R.id.attDate);
            inTime = (TextView) view.findViewById(R.id.inTime);
            inAddress = (TextView) view.findViewById(R.id.inAddress);
            inStatus = (TextView) view.findViewById(R.id.inStatus);
            outtime = (TextView) view.findViewById(R.id.outtime);
            outAddress = (TextView) view.findViewById(R.id.outAddress);
            outStatus = (TextView) view.findViewById(R.id.outStatus);
            tv_session = (TextView) view.findViewById(R.id.tv_session);
            tv_station = (TextView) view.findViewById(R.id.tv_station);
            inWorkPlace = (TextView) view.findViewById(R.id.inWorkPlace);
            outWorkPlace = (TextView) view.findViewById(R.id.outWorkPlace);
            ll_in = (LinearLayout) view.findViewById(R.id.ll_in);
            ll_out = (LinearLayout) view.findViewById(R.id.ll_out);
            attendanceImage = (ImageView) view.findViewById(R.id.attendanceImage);

        }
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_attendance_report, parent, false);
       // context = parent.getContext();
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        if (aList != null) {
            try {
                ModelReportAttendance rip = aList.get(position);
                holder.attDate.setText(rip.getAttendanceDate());
                holder.tv_session.setText(rip.getWorkSession());
                holder.tv_station.setText(rip.getMITourType());
                holder.inWorkPlace.setText(rip.getMIWorkPlace());
                holder.outWorkPlace.setText(rip.getOutWorkPlace());
                bindImage(holder.attendanceImage, rip.getImagePreName());
                holder.attendanceImage.setOnClickListener(v -> showImageDialog(rip.getImagePreName()));

                //String addressD = getCompleteAddressString(Double.parseDouble(ratt.getPInLat()), Double.parseDouble(ratt.getPInLog()));

                if (rip.getPunchInTime() != null) {
                    holder.inTime.setText(rip.getPunchInTime());
                    holder.inAddress.setText("[" + rip.getInAddress() + "]");
                    holder.inAddress.setPaintFlags(holder.inAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    if (rip.getInStatus() != null && !rip.getInStatus().equals("")) {
                        holder.inStatus.setText('(' + rip.getInStatus() + ')');
                    }

                } else {
                    holder.inTime.setText("");
                }
                if (rip.getPunchOutTime() != null) {
                    holder.outtime.setText(rip.getPunchOutTime());
                    holder.outAddress.setText('[' + rip.getOutAttAddress() + ']');
                    holder.outAddress.setPaintFlags(holder.outAddress.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    if (rip.getOutStatus() != null && !rip.getOutStatus().equals("")) {
                        holder.outStatus.setText('(' + rip.getOutStatus() + ')');
                    }

                } else {
                    holder.outtime.setText("");
                }

                holder.ll_in.setOnClickListener(view -> {
                    Intent gotomap=new Intent(context, ActivityMap.class);
                    //gotomap.putExtra("lat",String.valueOf(in_lat));
                    gotomap.putExtra("lat",rip.getPInLat());
                    gotomap.putExtra("lon",rip.getPInLog());
                    gotomap.putExtra("address",rip.getInAddress());
                    context.startActivity(gotomap);
                    ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                });
                holder.ll_out.setOnClickListener(view -> {
                    Intent gotomap=new Intent(context, ActivityMap.class);
                    //gotomap.putExtra("lat",String.valueOf(in_lat));
                    gotomap.putExtra("lat",rip.getOutLat());
                    gotomap.putExtra("lon",rip.getOutLog());
                    gotomap.putExtra("address",rip.getOutAttAddress());
                    context.startActivity(gotomap);
                    ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
        return aList.size();
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("address", strReturnedAddress.toString());
            } else {
                Log.w("address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("address", "Can not get Address!");
        }
        return strAdd;
    }

    private void bindImage(ImageView imageView, String rawUrl) {
        if (context == null || imageView == null) return;
        String safeUrl = sanitizeUrl(rawUrl);
        Glide.with(context)
                .load(safeUrl)
                .placeholder(R.drawable.bg_attendance_image_frame)
                .error(R.drawable.bg_attendance_image_frame)
                .into(imageView);
    }

    private String sanitizeUrl(String rawUrl) {
        return rawUrl == null ? "" : rawUrl.replace("\\", "/").trim();
    }

    private void showImageDialog(String rawUrl) {
        if (context == null) return;
        String safeUrl = sanitizeUrl(rawUrl);
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_attendance_image);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        ImageView ivFull = dialog.findViewById(R.id.ivAttendanceFull);
        Glide.with(context)
                .load(safeUrl)
                .placeholder(R.drawable.bg_attendance_image_frame)
                .error(R.drawable.bg_attendance_image_frame)
                .into(ivFull);
        ivFull.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

}
