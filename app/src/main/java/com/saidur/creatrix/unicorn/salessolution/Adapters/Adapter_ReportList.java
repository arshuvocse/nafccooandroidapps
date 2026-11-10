package com.saidur.creatrix.unicorn.salessolution.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelReportsMenu;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;

public class Adapter_ReportList extends BaseAdapter {
    private Context context;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private List<ModelReportsMenu> aList;
    LayoutInflater inflater;
    String roleType;
    public Adapter_ReportList(Context context, List<ModelReportsMenu> aList, String roleType) {
        this.context = context;
        this.aList = aList;
        this.roleType = roleType;
    }


    @Override
    public int getCount() {
        if (aList != null) {
            return aList.size();
            //return aProductList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.rv_reports, parent, false);
        }
        ModelReportsMenu menu = aList.get(pos);
        ImageView icon = view.findViewById(R.id.icon);
        TextView menuName = view.findViewById(R.id.menuName);
        CardView cv = view.findViewById(R.id.cv_item);
        switch (roleType) {
            case "MBE":
                //isVisible=false;
                if(menu.getId()==10)
                {
                    cv.setVisibility(View.GONE);
                }
                break;
            case "MIO":
            case "AM":
                //isVisible=true;
                if(menu.getId()==10)
                {
                    cv.setVisibility(View.VISIBLE);
                }
                break;
        }


        Glide.with(context).load(menu.getIcon())
                .into(icon);
        menuName.setText(menu.getMenuName());
        return view;
    }

}
