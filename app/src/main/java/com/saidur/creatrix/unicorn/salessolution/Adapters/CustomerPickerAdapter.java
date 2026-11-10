package com.saidur.creatrix.unicorn.salessolution.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Model.CustomerViewModel;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerPickerAdapter extends RecyclerView.Adapter<CustomerPickerAdapter.VH> implements Filterable {
    public interface OnPick {
        void onPick(CustomerViewModel item);
    }

    private final LayoutInflater inflater;
    private final List<CustomerViewModel> original;
    private List<CustomerViewModel> filtered;
    private final OnPick onPick;

    public CustomerPickerAdapter(Context ctx, List<CustomerViewModel> data, OnPick onPick) {
        this.inflater = LayoutInflater.from(ctx);
        this.original = data;
        this.filtered = new ArrayList<>(data);
        this.onPick = onPick;
    }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.spinner_customer_list, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        CustomerViewModel m = filtered.get(pos);
        TextView tv = h.itemView.findViewById(R.id.tv_spinner_item);
        tv.setText(m.toString()); // ensure toString() returns display text
        h.itemView.setOnClickListener(v -> onPick.onPick(m));
    }

    @Override public int getItemCount() { return filtered.size(); }
    @Override public Filter getFilter() { return filter; }

    private final Filter filter = new Filter() {
        @Override protected FilterResults performFiltering(CharSequence cs) {
            String q = cs == null ? "" : cs.toString().toLowerCase(Locale.ROOT).trim();
            List<CustomerViewModel> out = new ArrayList<>();
            if (q.isEmpty()) out.addAll(original);
            else {
                for (CustomerViewModel it : original) {
                    String name = it.getCustomerName() == null ? "" : it.getCustomerName();
                    String code = it.getCustomerCode() == null ? "" : it.getCustomerCode();
                    String cell = it.getCustomerCell() == null ? "" : it.getCustomerCell();
                    if (name.toLowerCase().contains(q) ||
                            code.toLowerCase().contains(q) ||
                            cell.toLowerCase().contains(q)) out.add(it);
                }
            }
            FilterResults fr = new FilterResults();
            fr.values = out; fr.count = out.size();
            return fr;
        }
        @SuppressWarnings("unchecked")
        @Override protected void publishResults(CharSequence cs, FilterResults fr) {
            filtered = (List<CustomerViewModel>) fr.values;
            notifyDataSetChanged();
        }
    };

    static class VH extends RecyclerView.ViewHolder { public VH(@NonNull View itemView){ super(itemView); } }
}
