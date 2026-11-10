package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDCPItem;

import java.util.ArrayList;
import java.util.List;

public class AdapterListDoctor extends ArrayAdapter<ModelDCPItem> {
    public AdapterListDoctor(@NonNull Context context, List<ModelDCPItem> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.lv_doctor, parent, false);
        }
        ModelDCPItem data = getItem(position);

        TextView doctorname = currentItemView.findViewById(R.id.doctorname);
        doctorname.setText(data.getDoctorName());

        TextView worksession = currentItemView.findViewById(R.id.worksession);
        worksession.setText(data.getWorksession());

        TextView tourtype = currentItemView.findViewById(R.id.tourtype);
        tourtype.setText(data.getTourType());

        // then return the recyclable view
        return currentItemView;
    }
}
