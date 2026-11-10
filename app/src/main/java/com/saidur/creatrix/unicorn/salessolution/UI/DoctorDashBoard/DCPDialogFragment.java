package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBDoctorHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelStationType;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiDoctor;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Adapter.DoctorAdapter;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDCPDate;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDCPItemSubmit;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilityHelper.DateTimeUtil;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentDcpBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopCalenderBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.PopDcpsessionBinding;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DCPDialogFragment extends DialogFragment implements DoctorAdapter.DoctorClick {
    ComponentDcpBinding binding;
    DBDoctorHelper dbDoctorHelper;
    DBCrudHelper dbCrudHelper;
    String empId, docId;

    private CallbackApi callbackApi;
    DoctorAdapter adapter;
    List<DoctorListViewModel> doctorList;
    PopCalenderBinding pcalender;
    PopDcpsessionBinding psession;

    ModelSession session;
    ProgressDialog pd;
    AlertDialog alertDialog;
    String selectemonth = "",selectedyear = "";
    int docid;
    //CalendarPickerView calendar;


    Dialog dSession;

    public static DCPDialogFragment newInstance() {
        return new DCPDialogFragment();
    }

    public void setCallbackApi(CallbackApi callbackApi) {
        this.callbackApi = callbackApi;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogCategoryTheme);
        //setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
        Bundle mBundle = getArguments();
        assert mBundle != null;
        selectemonth = mBundle.getString("Month");
        selectedyear = mBundle.getString("Year");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ComponentDcpBinding.inflate(inflater, container, false);
        if (savedInstanceState == null) {

        }
        binding.toolbarCustom.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireDialog().onBackPressed();
            }
        });
        dbDoctorHelper = new DBDoctorHelper(getActivity());
        dbCrudHelper = new DBCrudHelper(getActivity());
        session = dbCrudHelper.getSession();
        empId = String.valueOf(session.getEmpInfoId());
        pcalender = PopCalenderBinding.inflate(getLayoutInflater());
        psession = PopDcpsessionBinding.inflate(getLayoutInflater());
        List<DoctorListViewModel> data = dbDoctorHelper.getDoctorListFromSQLite();
        LoadDoctor(data);

        binding.btnCancel.setOnClickListener(view -> requireDialog().onBackPressed());
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        psession.morning.setOnClickListener(view -> {
            psession.morningImg.setBackgroundResource(R.drawable.shape_oval_white_selected);
            psession.morningImg.setBackgroundResource(R.drawable.shape_oval_black_sole);
            psession.morningImg.setColorFilter(ContextCompat.getColor(getActivity(), R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
            CallApi("Morning");
        });
        psession.evening.setOnClickListener(view -> {
            psession.eveningImg.setBackgroundResource(R.drawable.shape_oval_white_sole);
            psession.eveningImg.setBackgroundResource(R.drawable.shape_oval_white_selected);
            psession.eveningImg.setColorFilter(ContextCompat.getColor(getActivity(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
            CallApi("Evening");
        });

        initCalnder();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireActivity());
        dialogBuilder.setView(pcalender.getRoot());
        alertDialog = dialogBuilder.create();
        pcalender.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        dialogDcpSession();
       /* psession.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = (Date) pcalender.calendarView.getSelectedDate();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String formattedDate = sdf.format(date);
                SnackBarManagement._warning_CustomMessage(pcalender.getRoot(), formattedDate);


                ModelDCPItemSubmit subm = new ModelDCPItemSubmit();
                subm.setDoctorId(String.valueOf(docid));
                subm.setEmpId(empId);
                subm.setDCPDate(formattedDate);
                if (worksession.isEmpty()) {
                    SnackBarManagement._warning_CustomMessage(pcalender.getRoot(), "Select Work Session");
                    return;
                } else {
                    subm.setWorkSession(worksession);
                }
                Gson gson = new Gson();
                String data = gson.toJson(subm);
                submitDCP(subm);
            }
        });*/
        psession.btnCancel.setOnClickListener(v1 -> {
            dSession.dismiss();
        });

        return binding.getRoot();
    }

    private void CallApi(String workSession) {
        Date date = (Date) pcalender.calendarView.getSelectedDate();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String formattedDate = sdf.format(date);
        ModelDCPItemSubmit subm = new ModelDCPItemSubmit();
        subm.setDoctorId(String.valueOf(docid));
        subm.setEmpId(empId);
        subm.setDCPDate(formattedDate);
        subm.setWorkSession(workSession);

        Gson gson = new Gson();
        String datam = gson.toJson(subm);
        submitDCP(subm);
    }

    private void dialogDcpSession() {
        dSession = new Dialog(getContext());
        dSession.setContentView(psession.getRoot());
        dSession.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // dSession.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // dSession.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        dSession.getWindow().setLayout(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        dSession.getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    private void initCalnder() {
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 10);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -10);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pcalender.calendarView.init(lastYear.getTime(), nextYear.getTime(),
                            new SimpleDateFormat("MMMM, yyyy", Locale.US)) //
                    .inMode(CalendarPickerView.SelectionMode.SINGLE);

            pcalender.calendarView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
                @Override
                public void onDateSelected(Date date) {

                    SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtil.DISPLAY_DATE_FORMAT, Locale.US);
                    String formattedDate = sdf.format(date);
                    dSession.show();
                }
                @Override
                public void onDateUnselected(Date date) {
                }
            });
        }
        //Typeface typeface = getResources().getFont(R.font.roboto_bold);
        //or to support all versions use
        Typeface typeface = ResourcesCompat.getFont(requireActivity(), R.font.roboto_bold);
        pcalender.calendarView.setTitleTypeface(typeface);
        pcalender.calendarView.scrollToDate(new Date());

    }

    private void submitDCP(ModelDCPItemSubmit subm) {
        Gson gson = new Gson();
        String data = gson.toJson(subm);
        System.out.println(data);
        pd = new ProgressDialog(requireActivity());
        pd.setMessage("Saving....");
        pd.show();
        try {
            ApiDoctor service = RetrofitClientInstance.getRetrofitInstance().create(ApiDoctor.class);
            Call<ResponseResultInfo> call = service.SaveDCP(subm);
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {
                    ResponseResultInfo info = response.body();
                    if (pd != null || pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (info != null) {
                        if (info.isSuccess) {
                            dSession.dismiss();
                            dialogSuccess();
                        }
                        else {
                            dialogNotSuccess(info.getMsd());
                        }

                    } else {
                        SnackBarManagement._error_CustomMessage(pcalender.getRoot(), "Error");
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ResponseResultInfo> call, @NonNull Throwable t) {
                    if (pd != null || pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (t instanceof SocketTimeoutException) {

                    } else {

                    }
                }
            });

        } catch (Exception ex) {
            String str = ex.toString();
            Log.e("Exception", str);
            if (pd != null || pd.isShowing()) {
                pd.dismiss();
            }
        }
    }

    private void dialogSuccess() {
        new androidx.appcompat.app.AlertDialog.Builder(requireActivity())
                .setTitle("Success")
                .setMessage("DCP Successfully Saved")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                       // alertDialog.dismiss();
                    }

                }).setCancelable(false).show();
    }
    private void dialogNotSuccess(String msg) {
        new androidx.appcompat.app.AlertDialog.Builder(requireActivity())
                .setTitle("Not Success")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                       // alertDialog.dismiss();
                    }

                }).setCancelable(false).show();
    }

    private void LoadDoctor(List<DoctorListViewModel> docList) {
        doctorList = docList;
        adapter = new DoctorAdapter(getActivity(), docList, this);
        binding.rvDoclist.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        binding.rvDoclist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int docd) {
        docid = docd;
        alertDialog.show();
    }
    public interface CallbackApi {
        void onCallApi(String a,String b);
    }
    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        callbackApi.onCallApi(selectemonth,selectedyear);
    }
    private void filter(String docName) {
        try {
            if (doctorList != null) {
                ArrayList<DoctorListViewModel> arrayList = new ArrayList<>();
                for (DoctorListViewModel item : doctorList) {
                    if ((item.getDoctorName().toLowerCase().contains(docName.toLowerCase()))
                            || (item.getDocContact().toLowerCase().contains(docName.toLowerCase()))) {
                        arrayList.add(item);
                    }
                }

                adapter.filterList(arrayList);

            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
