package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Customer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.databinding.RvCustomerDueItemBinding;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerDueListAdapter extends RecyclerView.Adapter<CustomerDueListAdapter.CustomerDueViewHolder> {
    private final LayoutInflater inflater;
    private final List<CustomerDueItem> items = new ArrayList<>();
    private final DecimalFormat amountFormat;

    public CustomerDueListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        amountFormat = new DecimalFormat("#,##0.##", DecimalFormatSymbols.getInstance(Locale.US));
    }

    @NonNull
    @Override
    public CustomerDueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerDueViewHolder(RvCustomerDueItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerDueViewHolder holder, int position) {
        holder.bind(items.get(position), amountFormat);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void submitList(List<CustomerDueItem> newItems) {
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    static class CustomerDueViewHolder extends RecyclerView.ViewHolder {
        private final RvCustomerDueItemBinding binding;

        CustomerDueViewHolder(RvCustomerDueItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(CustomerDueItem item, DecimalFormat amountFormat) {
            binding.tvCustomerName.setText(item.getCustomerName());
            binding.tvDueAmount.setText(binding.getRoot().getContext().getString(
                    R.string.customer_due_money_format,
                    amountFormat.format(item.getDueAmount())
            ));

            if (TextUtils.isEmpty(item.getCustomerCode())) {
                binding.tvCustomerCode.setVisibility(View.GONE);
            } else {
                binding.tvCustomerCode.setVisibility(View.VISIBLE);
                binding.tvCustomerCode.setText(item.getCustomerCode());
            }

            if (TextUtils.isEmpty(item.getCellNo())) {
                binding.tvCustomerCell.setVisibility(View.GONE);
                binding.tvCustomerCell.setOnClickListener(null);
            } else {
                binding.tvCustomerCell.setVisibility(View.VISIBLE);
                binding.tvCustomerCell.setText(binding.getRoot().getContext().getString(
                        R.string.customer_due_cell_format,
                        item.getCellNo()
                ));
                binding.tvCustomerCell.setOnClickListener(v -> openDialer(v.getContext(), item.getCellNo()));
            }

            if (TextUtils.isEmpty(item.getMarketName())) {
                binding.tvCustomerMeta.setVisibility(View.GONE);
            } else {
                binding.tvCustomerMeta.setVisibility(View.VISIBLE);
                binding.tvCustomerMeta.setText(item.getMarketName());
            }
        }

        private void openDialer(Context context, String cellNo) {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + Uri.encode(cellNo)));
            context.startActivity(dialIntent);
        }
    }
}
