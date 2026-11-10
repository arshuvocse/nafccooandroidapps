package com.saidur.creatrix.unicorn.salessolution.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;

import com.saidur.creatrix.unicorn.salessolution.Model.CustomerViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public   class CustomerMultiAdapter extends BaseAdapter implements Filterable {
    private final Context ctx;
    private final List<CustomerViewModel> original;   // master list (state lives here)
    private List<CustomerViewModel> filtered;         // current visible list
    private final ItemFilter filter = new ItemFilter();

    public CustomerMultiAdapter(Context ctx, List<CustomerViewModel> items) {
        this.ctx = ctx;
        this.original = items;
        this.filtered = new ArrayList<>(items);
    }

    @Override public int getCount() { return filtered.size(); }
    @Override public Object getItem(int position) { return filtered.get(position); }
    @Override public long getItemId(int position) { return filtered.get(position).getCustomerMasterId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckedTextView tv;
        if (convertView == null) {
            LayoutInflater inf = LayoutInflater.from(ctx);
            convertView = inf.inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
        }
        tv = (CheckedTextView) convertView;
        CustomerViewModel item = filtered.get(position);
        tv.setText(item.getCustomerName());
        // reflect current checked state
        ListView lv = (ListView) parent;
        lv.setItemChecked(position, item.checked);
        return convertView;
    }

    @Override public Filter getFilter() { return filter; }



private class ItemFilter extends Filter {
    @Override protected FilterResults performFiltering(CharSequence constraint) {
        String q = constraint == null ? "" : constraint.toString().toLowerCase(Locale.ROOT).trim();
        List<CustomerViewModel> out;
        if (q.isEmpty()) {
            out = new ArrayList<>(original);
        } else {
            out = new ArrayList<>();
            for (CustomerViewModel it : original) {
                if (it.getCustomerName().toLowerCase(Locale.ROOT).contains(q)) out.add(it);
            }
        }
        FilterResults fr = new FilterResults();
        fr.values = out;
        fr.count = out.size();
        return fr;
    }

    @SuppressWarnings("unchecked")
    @Override protected void publishResults(CharSequence constraint, FilterResults results) {
        filtered = (List<CustomerViewModel>) results.values;
        notifyDataSetChanged();
    }
}

    // helper to toggle by filtered position
    public void toggle(int filteredPos) {
        CustomerViewModel item = filtered.get(filteredPos);
        item.checked = !item.checked;
        notifyDataSetChanged();
    }

    public List<CustomerViewModel> getAllOriginal() { return original; }
}