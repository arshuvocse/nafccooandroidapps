package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelSubTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Model.AssignMarketStructure.ModelTeritorry;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseTwwpCopyModel;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter.AdapterDcpDoctorlist;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.Model.ModelDCPApproveList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDCPItem;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTransport;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.Model.ModelTwwpABMList;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.Model.ModelWSMorning;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.OnEditWSListener;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.WorkScheduleDialogFragment;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter.RecyclerViewActionListener;

import java.util.List;


public class AdapterWS extends RecyclerView.Adapter<AdapterWS.VH> {
    Context context;
    List<ResponseTwwpCopyModel> dList;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    DBCrudHelper dbCrudHelper;
    private OnEditWSListener onEditListener;

    public AdapterWS(Context context, List<ResponseTwwpCopyModel> cList, OnEditWSListener onEditListener) {
        this.context = context;
        this.dList = cList;
        this.onEditListener = onEditListener;
        dbCrudHelper = new DBCrudHelper(context);
    }

    @NonNull
    @Override
    public AdapterWS.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workschedule_model, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWS.VH holder, int position) {
        ResponseTwwpCopyModel data = dList.get(holder.getAdapterPosition());
        ModelWSMorning morning = data.getMorningList();
        ModelWSMorning evening = data.getEveningList();

        holder.m_tv_sAddress.setSelected(true);
        holder.e_tv_sAddress.setSelected(true);
        holder.m_tv_eAddress.setSelected(true);
        holder.e_tv_eAddress.setSelected(true);


        holder.tv_day.setText(data.getDay());
        holder.tv_date.setText(data.getDate());

        //ArrayAdapter<ModelTransport> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, dbCrudHelper.getTransportList_SQLite());
        ArrayAdapter<ModelTransport> dataAdapter = new ArrayAdapter<>(context, R.layout.spinner_custom_medium, dbCrudHelper.getTransportList_SQLite());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<ModelSubTeritorry> teriAdapter = new ArrayAdapter<>(context,R.layout.spinner_custom_medium, dbCrudHelper.getSubTerritoryByIdList_SQLite(0));
        teriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if(morning!=null)
        {
            holder.m_tv_ttype.setText(morning.getTourType());
            holder.m_tv_wtype.setText(morning.getWorkType());
            holder.m_tv_dday.setText(morning.getDeliveryDayName());
            holder.m_tv_stime.setText(morning.getStartTime());
            holder.m_tv_sAddress.setText(morning.getStartPoint());
            holder.m_tv_eAddress.setText(morning.getEndPoint());

            holder.m_tv_eTime.setText(morning.getEndTime());
            holder.mspinnerTravel.setAdapter(dataAdapter);
            holder.mspinnerTravel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    ModelTransport mt = (ModelTransport) adapterView.getItemAtPosition(i);
                    morning.setTravelById(mt.getTransportId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            holder.m_tv_teri.setAdapter(teriAdapter);
            holder.m_tv_teri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    ModelSubTeritorry mt = (ModelSubTeritorry) adapterView.getItemAtPosition(i);
                    morning.setTerritory(String.valueOf(mt.getSubTerritoryId()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        if(evening!=null)
        {
            holder.e_tv_ttype.setText(evening.getTourType());
            holder.e_tv_wtype.setText(evening.getWorkType());
            holder.e_tv_dday.setText(evening.getDeliveryDayName());
            holder.e_tv_stime.setText(evening.getStartTime());
            holder.e_tv_sAddress.setText(evening.getStartPoint());
            holder.e_tv_eAddress.setText(evening.getEndPoint());
            holder.e_tv_eTime.setText(evening.getEndTime());

            holder.espinnerTravel.setAdapter(dataAdapter);
            holder.espinnerTravel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    ModelTransport mt = (ModelTransport) adapterView.getItemAtPosition(i);
                    evening.setTravelById(mt.getTransportId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            holder.e_tv_teri.setAdapter(teriAdapter);
            holder.e_tv_teri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    ModelSubTeritorry mt = (ModelSubTeritorry) adapterView.getItemAtPosition(i);
                    evening.setTerritory(String.valueOf(mt.getSubTerritoryId()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }

        // holder.m_tv_teriName.setText(morning.getPlace());
        //holder.e_tv_teriName.setText(evening.getPlace());

        // holder.m_tv_place.setText(morning.getTravel());
        // holder.e_tv_place.setText(evening.getTravel());

        //ModelTeritorry







        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return dList != null ? dList.size() : 0;
    }



    public static class VH extends RecyclerView.ViewHolder {
        TextView tv_day, tv_date, m_tv_ttype, e_tv_ttype, m_tv_wtype, e_tv_wtype, m_tv_dday, e_tv_dday,
                m_tv_stime, e_tv_stime, m_tv_sAddress, e_tv_sAddress,
                m_tv_eAddress, e_tv_eAddress, m_tv_eTime, e_tv_eTime;
        Spinner mspinnerTravel, espinnerTravel, m_tv_teri, e_tv_teri;
        RecyclerView doclist;
        EditText m_tv_teriName, e_tv_teriName, m_tv_place, e_tv_place;

        public VH(@NonNull View v) {
            super(v);
            mspinnerTravel = (Spinner) v.findViewById(R.id.mspinnerTravel);
            espinnerTravel = (Spinner) v.findViewById(R.id.espinnerTravel);
            m_tv_teri = (Spinner) v.findViewById(R.id.m_tv_teri);
            e_tv_teri = (Spinner) v.findViewById(R.id.e_tv_teri);

            tv_day = (TextView) v.findViewById(R.id.tv_day);
            tv_date = (TextView) v.findViewById(R.id.tv_date);
            m_tv_ttype = (TextView) v.findViewById(R.id.m_tv_ttype);
            e_tv_ttype = (TextView) v.findViewById(R.id.e_tv_ttype);
            m_tv_wtype = (TextView) v.findViewById(R.id.m_tv_wtype);
            e_tv_wtype = (TextView) v.findViewById(R.id.e_tv_wtype);
            m_tv_dday = (TextView) v.findViewById(R.id.m_tv_dday);
            e_tv_dday = (TextView) v.findViewById(R.id.e_tv_dday);

           /* m_tv_teriName = (EditText) v.findViewById(R.id.m_tv_teriName);
            e_tv_teriName = (EditText) v.findViewById(R.id.e_tv_teriName);
            m_tv_place = (EditText) v.findViewById(R.id.m_tv_place);
            e_tv_place = (EditText) v.findViewById(R.id.e_tv_place);*/
            m_tv_stime = (TextView) v.findViewById(R.id.m_tv_stime);
            e_tv_stime = (TextView) v.findViewById(R.id.e_tv_stime);
            m_tv_sAddress = (TextView) v.findViewById(R.id.m_tv_sAddress);
            e_tv_sAddress = (TextView) v.findViewById(R.id.e_tv_sAddress);
            m_tv_eAddress = (TextView) v.findViewById(R.id.m_tv_eAddress);
            e_tv_eAddress = (TextView) v.findViewById(R.id.e_tv_eAddress);
            m_tv_eTime = (TextView) v.findViewById(R.id.m_tv_eTime);
            e_tv_eTime = (TextView) v.findViewById(R.id.e_tv_eTime);

          /*  m_tv_teriName.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                public void afterTextChanged(Editable editable) {
                    boolean id = m_tv_teriName.hasFocus();
                    if (id == true) {
                        int postion = getAdapterPosition();
                        if (!String.valueOf(editable).isEmpty()) {
                            onEditListener.onMTeriNameEditFocusChange(postion, String.valueOf(editable),dList);
                            int textLength = m_tv_teriName.getText().toString().length();
                            m_tv_teriName.setSelection(textLength, textLength);
                        } else {
                            onEditListener.onMTeriNameEditFocusChange(postion, "",dList);
                        }
                    }

                }

                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }
            });
            e_tv_teriName.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                public void afterTextChanged(Editable editable) {
                    boolean id = e_tv_teriName.hasFocus();
                    if (id == true) {
                        int postion = getAdapterPosition();
                        if (!String.valueOf(editable).isEmpty()) {
                            onEditListener.onETeriNameEditFocusChange(postion, String.valueOf(editable));
                            int textLength = e_tv_teriName.getText().toString().trim().length();
                            e_tv_teriName.setSelection(textLength, textLength);
                        } else {
                            onEditListener.onETeriNameEditFocusChange(postion, "");
                        }
                    }

                }

                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }
            });
            m_tv_place.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                public void afterTextChanged(Editable editable) {
                    boolean id = m_tv_place.hasFocus();
                    if (id == true) {
                        int postion = getAdapterPosition();
                        if (!String.valueOf(editable).isEmpty()) {
                            onEditListener.onMPlaceEditFocusChange(postion, String.valueOf(editable));
                            int textLength = m_tv_place.getText().toString().trim().length();
                            m_tv_place.setSelection(textLength, textLength);
                        } else {
                            onEditListener.onMPlaceEditFocusChange(postion, "");
                        }
                    }

                }

                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }
            });
            e_tv_place.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                public void afterTextChanged(Editable editable) {
                    boolean id = m_tv_place.hasFocus();
                    if (id == true) {
                        int postion = getAdapterPosition();
                        if (!String.valueOf(editable).isEmpty()) {
                            onEditListener.onEPlaceEditFocusChange(postion, String.valueOf(editable));
                            int textLength = m_tv_place.getText().toString().trim().length();
                            m_tv_place.setSelection(textLength, textLength);
                        } else {
                            onEditListener.onEPlaceEditFocusChange(postion, "");
                        }
                    }

                }

                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }
            });*/
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

}
