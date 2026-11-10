package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpABMList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelWSABM;

import java.util.List;


public class AdapterWSlist extends RecyclerView.Adapter<AdapterWSlist.VH> {
    Context context;
    List<ModelTwwpABMList> tList;
    DcpClick doctorClick;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds

    public AdapterWSlist(Context context, List<ModelTwwpABMList> tList) {
        this.context = context;
        this.tList = tList;
    }

    @NonNull
    @Override
    public AdapterWSlist.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_wsabm_list, parent, false);
        return new AdapterWSlist.VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWSlist.VH holder, int position) {
        ModelTwwpABMList data = tList.get(holder.getAdapterPosition());
        ModelWSABM morning= data.getWSMorning_V();
        ModelWSABM evening= data.getWSEvening_V();

        holder.tv_date.setText(data.getDayVal()+","+data.getMonthVal());

        holder.tv_mttype.setText(data.getWSMorning_V().getTourType());
        holder.tv_ettype.setText(data.getWSEvening_V().getTourType());

        holder.tv_mwtype.setText(data.getWSMorning_V().getWorkType());
        holder.tv_ewtype.setText(data.getWSEvening_V().getWorkType());

        holder.tv_mdday.setText(data.getWSMorning_V().getDeliveryDayName());
        holder.tv_edday.setText(data.getWSEvening_V().getDeliveryDayName());

        holder.tv_mteritorry.setText(data.getWSMorning_V().getTerritoryName());
        holder.tv_eteritorry.setText(data.getWSEvening_V().getTerritoryName());

        holder.tv_mplace.setText(data.getWSMorning_V().getPlace());
        holder.tv_eplace.setText(data.getWSEvening_V().getPlace());

        holder.tv_mtravel.setText(data.getWSMorning_V().getTravel());
        holder.tv_etravel.setText(data.getWSEvening_V().getTravel());

        holder.tv_mstime.setText(data.getWSMorning_V().getStartTime());
        holder.tv_estime.setText(data.getWSEvening_V().getStartTime());

        holder.tv_mspoint.setText(data.getWSMorning_V().getStartAddress());
        holder.tv_espoint.setText(data.getWSEvening_V().getStartAddress());

        holder.tv_mepoint.setText(data.getWSMorning_V().getEndAddress());
        holder.tv_eepoint.setText(data.getWSEvening_V().getEndAddress());

        holder.tv_metime.setText(data.getWSMorning_V().getEndTime());
        holder.tv_eetime.setText(data.getWSEvening_V().getEndTime());


        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        if (tList != null || tList.size() > 0) {
            return tList.size();
        } else {
            return 0;
        }
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView tv_date, tv_mttype,tv_ettype,tv_mwtype,tv_ewtype,tv_mdday,tv_edday,tv_mstime,
                tv_estime,tv_mspoint,tv_mepoint,tv_espoint,tv_eepoint,tv_metime,tv_eetime,tv_mteritorry,tv_eteritorry,
                tv_mplace,tv_eplace,tv_mtravel,tv_etravel;
        CardView cardClickdv;
        RecyclerView doclist;

        public VH(@NonNull View v) {
            super(v);
            tv_date = (TextView) v.findViewById(R.id.tv_date);
            tv_mttype = (TextView) v.findViewById(R.id.tv_mttype);

            tv_ettype = (TextView) v.findViewById(R.id.tv_ettype);
            tv_mwtype = (TextView) v.findViewById(R.id.tv_mwtype);
            tv_ewtype = (TextView) v.findViewById(R.id.tv_ewtype);
            tv_mdday = (TextView) v.findViewById(R.id.tv_mdday);
            tv_edday = (TextView) v.findViewById(R.id.tv_edday);
            tv_mstime = (TextView) v.findViewById(R.id.tv_mstime);
            tv_estime = (TextView) v.findViewById(R.id.tv_estime);
            tv_mspoint = (TextView) v.findViewById(R.id.tv_mspoint);
            tv_mepoint = (TextView) v.findViewById(R.id.tv_mepoint);
            tv_espoint = (TextView) v.findViewById(R.id.tv_espoint);
            tv_eepoint = (TextView) v.findViewById(R.id.tv_eepoint);
            tv_metime = (TextView) v.findViewById(R.id.tv_metime);
            tv_eetime = (TextView) v.findViewById(R.id.tv_eetime);
            tv_mteritorry = (TextView) v.findViewById(R.id.tv_mteritorry);
            tv_eteritorry = (TextView) v.findViewById(R.id.tv_eteritorry);
            tv_mplace = (TextView) v.findViewById(R.id.tv_mplace);
            tv_eplace = (TextView) v.findViewById(R.id.tv_eplace);
            tv_mtravel = (TextView) v.findViewById(R.id.tv_mtravel);
            tv_etravel = (TextView) v.findViewById(R.id.tv_etravel);
            cardClickdv = (CardView) v.findViewById(R.id.cardClickdv);
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public interface DcpClick {
        void onClick(int id);
    }
}
