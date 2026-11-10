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

import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorContact;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;

public class AdapterDoctorContact extends RecyclerView.Adapter<AdapterDoctorContact.BookViewHolder> {

    private final Context context;
    private static final int FADE_DURATION = 500; // milliseconds
    private final List<DoctorContact> aContactList;
    private final List<DoctorContact> aContactApi;

    public AdapterDoctorContact(Context context, List<DoctorContact> aContactList, List<DoctorContact> aContactApi) {
        this.context = context;
        this.aContactList = aContactList;
        this.aContactApi = aContactApi;
    }

    @NonNull
    @Override
    public AdapterDoctorContact.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_contact, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDoctorContact.BookViewHolder holder, int position) {
        // Safety check
        if (position < 0 || position >= aContactList.size()) return;

        DoctorContact ad = aContactList.get(position);

        holder.tv_contactTypeName.setText(ad.getContactType());
        holder.contactName.setText(ad.getContact());

        // Delete button click
        holder.del.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION && pos < aContactList.size() && pos < aContactApi.size()) {
                aContactList.remove(pos);
                aContactApi.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, aContactList.size()); // keep index consistency
            }
        });

        // Apply animation
        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return aContactList != null ? aContactList.size() : 0;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tv_contactTypeName, contactName;
        ImageView del;

        public BookViewHolder(@NonNull View view) {
            super(view);
            tv_contactTypeName = view.findViewById(R.id.tv_contactTypeName);
            contactName = view.findViewById(R.id.tv_contactName);
            del = view.findViewById(R.id.iv_del);
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
}
