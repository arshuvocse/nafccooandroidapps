package com.saidur.creatrix.unicorn.salessolution.UI.Customer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Model.Customer.ModelCustomerCallReason;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;

public class CcReasonAdapter extends RecyclerView.Adapter<CcReasonAdapter.ViewHolder> {
    Context context;
    // Initialize variable
    List<ModelCustomerCallReason> dataList;
    ItemClickListener itemClickListener;
    int selectedPosition = -1;

    // Create constructor
    public CcReasonAdapter(Context context,List<ModelCustomerCallReason> dataList,
                           ItemClickListener itemClickListener) {
        this.context = context;
        this.dataList = dataList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_row_ccreason, parent,
                        false);
        // Pass holder view
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelCustomerCallReason data = dataList.get(position);
        // Set text on radio button
       // holder.tv_name.setText(data.getChemistCallReason());
        holder.radioButton.setText(data.getChemistCallReason());

        // Checked selected radio button
        holder.radioButton.setChecked(position == selectedPosition);

        // set listener on radio button
        holder.radioButton.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(
                            CompoundButton compoundButton,
                            boolean b) {
                        // check condition
                        if (b) {
                            // When checked
                            // update selected position
                            selectedPosition = holder.getAdapterPosition();
                            // Call listener

                            itemClickListener.onClick(holder.radioButton.getText()
                                    .toString(),data.getChemistCallReasonId());
                        }
                    }
                });
    }

    @Override
    public long getItemId(int position) {
        // pass position
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        // pass position
        return position;
    }

    @Override
    public int getItemCount() {
        // pass total list size
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Initialize variable
        RadioButton radioButton;
        TextView tv_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Assign variable
            radioButton = itemView.findViewById(R.id.rb_reason);
            //tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

    public interface ItemClickListener {
        void onClick(String s,int id);
    }
}

