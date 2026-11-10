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
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpList;
import java.util.List;


public class AdapterTwwplist extends RecyclerView.Adapter<AdapterTwwplist.VH> {
    Context context;
    List<ModelTwwpList> tList;
    DcpClick doctorClick;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds

    public AdapterTwwplist(Context context, List<ModelTwwpList> tList) {
        this.context = context;
        this.tList = tList;
    }

    @NonNull
    @Override
    public AdapterTwwplist.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_twwp_list, parent, false);
        return new AdapterTwwplist.VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTwwplist.VH holder, int position) {
        ModelTwwpList data = tList.get(holder.getAdapterPosition());

        holder.day.setText(data.getDayName());
        holder.tv_mspoint.setSelected(true);
        holder.tv_espoint.setSelected(true);
        holder.tv_mepoint.setSelected(true);
        holder.tv_eepoint.setSelected(true);

        holder.tv_mttype.setText(data.getTwwpMorning_V().getTourType());
        holder.tv_ettype.setText(data.getTwwpEvening_V().getTourType());
        holder.tv_mwtype.setText(data.getTwwpMorning_V().getWorkType());
        holder.tv_ewtype.setText(data.getTwwpEvening_V().getWorkType());
        holder.tv_mdday.setText(data.getTwwpMorning_V().getDeliveryDayName());
        holder.tv_edday.setText(data.getTwwpEvening_V().getDeliveryDayName());
        holder.tv_mstime.setText(data.getTwwpMorning_V().getStartTime());
        holder.tv_estime.setText(data.getTwwpEvening_V().getStartTime());

        holder.tv_mspoint.setText(data.getTwwpMorning_V().getStartPoint());
        holder.tv_mepoint.setText(data.getTwwpMorning_V().getEndPoint());

        holder.tv_espoint.setText(data.getTwwpEvening_V().getStartPoint());
        holder.tv_eepoint.setText(data.getTwwpEvening_V().getEndPoint());

        holder.tv_metime.setText(data.getTwwpEvening_V().getEndTime());
        holder.tv_eetime.setText(data.getTwwpEvening_V().getEndTime());


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
        TextView day, tv_mttype,tv_ettype,tv_mwtype,tv_ewtype,tv_mdday,tv_edday,tv_mstime,
                tv_estime,tv_mspoint,tv_mepoint,tv_espoint,tv_eepoint,tv_metime,tv_eetime;
        CardView cardClickdv;
        RecyclerView doclist;

        public VH(@NonNull View v) {
            super(v);
            day = (TextView) v.findViewById(R.id.day);
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
