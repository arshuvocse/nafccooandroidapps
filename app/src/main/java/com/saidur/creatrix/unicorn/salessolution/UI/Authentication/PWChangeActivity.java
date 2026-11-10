package com.saidur.creatrix.unicorn.salessolution.UI.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiLogin;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityPwchangeBinding;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PWChangeActivity extends AppCompatActivity {
    ActivityPwchangeBinding binding;
    ModelSession session;
    DBCrudHelper dbCrudHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPwchangeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbCrudHelper = new DBCrudHelper(PWChangeActivity.this);

        session = dbCrudHelper.getSession();
        String empId = String.valueOf(session.getEmpInfoId());

        binding.inputConfirmPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    try {
                        if (!binding.inputConfirmPassword.getText().toString().trim().equals(binding.inputNewPassword.toString().trim())) {
                            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Password Not Matched");
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        binding.btnCPsubmit.setOnClickListener(v -> hitApi(empId));
    }
    private void hitApi(String empId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("OldPass", binding.inputOldPassword.getText().toString().trim());
        map.put("NewPass", binding.inputNewPassword.getText().toString().trim());
        map.put("empId", empId);

        ProgressDialog progressDoalog = new ProgressDialog(PWChangeActivity.this);
        progressDoalog.setMessage("Processing.... Please wait");
        progressDoalog.show();
        progressDoalog.setCanceledOnTouchOutside(false);

        Toast.makeText(this, "param :"+map, Toast.LENGTH_SHORT).show();

        try {
            ApiLogin service = RetrofitClientInstance.getRetrofitInstance().create(ApiLogin.class);
            Call<ResponseResultInfo> call = service.ChangePass(map);
            call.enqueue(new Callback<ResponseResultInfo>() {
                @Override
                public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {
                    progressDoalog.dismiss();
                    ResponseResultInfo info = response.body();
                    if (info != null) {
                        if (info.getSuccess()) {
                            onSaveSuccess("Password Changed Successfully Submitted", true);
                        } else {
                            onSaveError(info.getMsd());
                        }
                    } else {
                        onSaveError("Slow Internet Detected..Please try again");

                    }

                }

                @Override
                public void onFailure(@NonNull Call<ResponseResultInfo> call, @NonNull Throwable t) {
                    progressDoalog.dismiss();
                    if (t instanceof SocketTimeoutException) {
                        onSaveError("Slow Internet Detected..Please try again");
                    } else {
                        onSaveError("Some error occurred..Please try again");
                    }
                }
            });

        } catch (Exception ex) {
            progressDoalog.dismiss();
            String str = ex.toString();
            Log.e("Exception", str);
            onSaveError("Some error occurred..Please try again");

        }
    }
    private void onSaveError(String msd) {
        SnackBarManagement._error_CustomMessage(binding.getRoot(), msd);
    }

    private void onSaveSuccess(String msd, boolean b) {
        if (b) {
            new androidx.appcompat.app.AlertDialog.Builder(PWChangeActivity.this)
                    .setTitle("Success")
                    .setMessage(msd)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            dbCrudHelper._deleteAllRecordsFromaTable("tblSession");

                            dbCrudHelper._deleteAllRecordsFromaTable("tblInitTable");
                            dbCrudHelper._deleteAllRecordsFromaTable("tbl_ProductInfo");
                            dbCrudHelper._deleteAllRecordsFromaTable("tbl_ProductSampleInfo");
                            dbCrudHelper._deleteAllRecordsFromaTable("tblCustomerInfo");
                            dbCrudHelper._deleteAllRecordsFromaTable("tblDoctorInfo");
                            dbCrudHelper._deleteAllRecordsFromaTable("tblDoctorBrand");

                            Intent i = new Intent(PWChangeActivity.this, LoginActivity.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }

                    }).setCancelable(false).show();
        }

    }
}