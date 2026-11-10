package com.saidur.creatrix.unicorn.salessolution.UI.Leave;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.ILeave;
import com.saidur.creatrix.unicorn.salessolution.MainActivity;
import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveTypeInfo;

import com.saidur.creatrix.unicorn.salessolution.Model.Leave.LeaveVM;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Network.Request.ModelLeaveSvBody;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseLeaveList;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterLeave;

import com.saidur.creatrix.unicorn.salessolution.R;

import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityLeaveBinding;

import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentLoadingbarBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.DialogSuccessCustomBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LeaveActivity extends AppCompatActivity implements ILeave.View {
    ActivityLeaveBinding binding;
    ILeave.Presenter presenter;
    private static final int PERMISSION_REQUEST_CODE = 200;
    public static final int PICK_IMAGE = 1;
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_PIC_REQUEST = 22;
    Uri imageuri;
    long diffDays;
    DatePickerDialog picker;
    String startdate, enddate;
    int selectedday;
    private Period period;
    private String monthNameArray[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public String monthNameArrayFull[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    DBCrudHelper dbCrudHelper;
    ModelSession session;

    Dialog popup,pd_process;
    DialogSuccessCustomBinding dbinding;
    ComponentLoadingbarBinding lbinding;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbinding= DialogSuccessCustomBinding.inflate(getLayoutInflater());
        lbinding= ComponentLoadingbarBinding.inflate(getLayoutInflater());
        prepareDialog();

        binding.toolbarCustom.setNavigationOnClickListener(v -> finish());
        dbCrudHelper=new DBCrudHelper(LeaveActivity.this);
        session=dbCrudHelper.getSession();

        binding.endDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.spinerLeaveType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                        LeaveTypeInfo lt = (LeaveTypeInfo) parentView.getSelectedItem();
//                        int lid = lt.getLeaveBalanceId();
//                        if (lid == 2) {
//                            binding.presclay.setVisibility(View.GONE);
//                        }else if(lid==3)
//                        {
//                            binding.presclay.setVisibility(View.GONE);
//                        }else {
//                            binding.presclay.setVisibility(View.VISIBLE);
//                        }
//                        binding.endDate.setText("");
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parentView) {
//                        // your code here
//                    }
//
//                });
                if (s.length() != 0) {
                    String sdate, edate;
                    sdate = binding.startDate.getText().toString().trim();
                    edate = binding.endDate.getText().toString().trim();
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
                        Date d1 = sdf.parse(sdate);
                        Date d2 = sdf.parse(edate);
                        long oneday = 24 * 60 * 60 * 1000;
                        long diff = d2.getTime() - d1.getTime();
                        diffDays = (diff + oneday) / (24 * 60 * 60 * 1000);
                        binding.duration.setText(String.valueOf(diffDays));

                        //LeaveTypeInfo leaveTypeInfo = (LeaveTypeInfo) binding.spinerLeaveType.getSelectedItem();
                        if (sdate == null) {
                            SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Start Date required");
                            return;
                        }
                        if (edate == null) {
                            SnackBarManagement._warning_CustomMessage(binding.masterLayout, "End Date required");
                            return;
                        }
//                        if (String.valueOf(leaveTypeInfo.getLeaveBalanceId()).equals("")) {
//                            binding.datPickerEndDate.setClickable(false);
//                            SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Leave Type Can't be null");
//                            return;
//                        }
//                        if(leaveTypeInfo.getLeaveBalanceId() == 1)
//                        {
//                            binding.presclay.setVisibility(View.VISIBLE);
//                            if (binding.imgaeView.getDrawable() == null) {
//                                SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Prescription required");
//                                return;
//                            }
//                        }
//                        if (leaveTypeInfo.getLeaveBalanceId() == 2) {
//                            binding.presclay.setVisibility(View.GONE);
//                            /*if (diffDays > 3) {
//                                SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Casual Leave Max 3 day");
//                                binding.btnSubmit.setEnabled(false);
//                                return;
//                            } else {
//                                binding.btnSubmit.setEnabled(true);
//                            }*/
//                          /*  if (diffDays >= 4 *//*&& diffDays == 3*//*) {
//                                binding.presclay.setVisibility(View.VISIBLE);
//                                if (binding.imgaeView.getDrawable() == null) {
//                                    SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Prescription required");
//                                    return;
//                                }
//                            } else {
//                                binding.presclay.setVisibility(View.GONE);
//                            }*/
//                        }
//                        if (leaveTypeInfo.getLeaveBalanceId() == 3) {
//                            binding.presclay.setVisibility(View.GONE);
//
//                        }
//
//                        //  Toast.makeText(LeaveActivity.this, "Balance : "+String.valueOf(leaveTypeInfo.getYearlyLeaveBalance()), Toast.LENGTH_SHORT).show();
//                        if (leaveTypeInfo.getYearlyLeaveBalance() != 0) {
//                            binding.btnSubmit.setEnabled(true);
//                            if (diffDays > leaveTypeInfo.getYearlyLeaveBalance()) {
//                                SnackBarManagement._warning_CustomMessage(binding.masterLayout, "You Haven't Enough Leave Balance");
//                                binding.btnSubmit.setEnabled(false);
//                                return;
//                            } else {
//                                binding.btnSubmit.setEnabled(true);
//                            }
//                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
        binding.datePickerStartDate.setOnClickListener(v -> {
            Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            picker = new DatePickerDialog(this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        int monthNumber = monthOfYear + 1;
                        String attSelectedMonths = Integer.toString(dayOfMonth) + "-" + monthNameArray[monthOfYear] + "-" + year1;
                        startdate = Integer.toString(dayOfMonth) + "-" + monthNameArray[monthOfYear] + "-" + year1;
                        selectedday=dayOfMonth;
                        binding.startDate.setText(attSelectedMonths);
                    }, year, month, day);

            picker.show();
        });
        binding.datPickerEndDate.setOnClickListener(v -> {
            if (binding.startDate.getText().toString().trim().isEmpty()) {
                SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Select Start Date First");
                return;

            }
            Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            picker = new DatePickerDialog(this,
                    (view, year12, monthOfYear, dayOfMonth) -> {

                        int monthNumber = monthOfYear + 1;
                        String attSelectedMonths = Integer.toString(dayOfMonth) + "-" + monthNameArray[monthOfYear] + "-" + year12;
                        enddate = Integer.toString(dayOfMonth) + "-" + monthNameArray[monthOfYear] + "-" + year12;
                        binding.endDate.setText(attSelectedMonths);
                    }, year, month, selectedday+1/*day+1*/);
            picker.show();


        });
        binding.datPickerReturnDate.setOnClickListener(v -> UtilityHelper._datePickerDialogeForDates(binding.returnDate, LeaveActivity.this));

        presenter = new PresenterLeave(this, this);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        int empId = session.getEmpInfoId();
        presenter.GetLeaveTyep(empId, year);

        binding.btnSubmit.setOnClickListener(v ->
        {
            String img_str = null;
            try {
                binding.imgaeView.buildDrawingCache();
                Bitmap bitmap = binding.imgaeView.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                byte[] image = stream.toByteArray();
                img_str = Base64.encodeToString(image, 0);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            ModelLeaveSvBody leaveSM = new ModelLeaveSvBody();
      LeaveTypeInfo leaveTypeInfo = (LeaveTypeInfo) binding.spinerLeaveType.getSelectedItem();
            if (startdate == null) {
                SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Start Date required");
                return;
            }
            if (enddate == null) {
                SnackBarManagement._warning_CustomMessage(binding.masterLayout, "End Date required");
                return;
            }
            if (binding.reason.getText().toString().trim().isEmpty()) {
                SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Reason Can't be Empty");
                return;
            }

            if (binding.returnDate.getText().toString().trim().isEmpty()) {
                SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Return Date Can't Empty");
                return;

            }
            if (binding.emContact.getText().toString().trim().isEmpty() || binding.emContact.getText().toString().trim().length() <= 10) {
                SnackBarManagement._warning_CustomMessage(binding.masterLayout, "Emergency Contact Can't Be Empty Or Less Than 11");
                return;
            }

            if(NetworkInformation.isConnected(LeaveActivity.this)){
                pd_process.setCancelable(false);
                pd_process.show();
                lbinding.tvText.setText("Processing...");
                SaveLeave(leaveSM, leaveTypeInfo, empId, img_str);
            }else {
                SnackBarManagement._error_CustomMessage(binding.getRoot(),"No Internet!!");
            }

        });
       /* binding.galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });*/
        binding.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(LeaveActivity.this,
                        Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), CAMERA_PIC_REQUEST);

                } else {
                    ActivityCompat.requestPermissions(LeaveActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSION_REQUEST_CODE);
                }
            }
        });
    }

    private void prepareDialog() {
        popup=new Dialog(LeaveActivity.this);
        popup.setContentView(dbinding.getRoot());
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.getWindow().setLayout(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        popup.getWindow().getAttributes().gravity = Gravity.CENTER;

        pd_process=new Dialog(LeaveActivity.this);
        pd_process.setContentView(lbinding.getRoot());
        pd_process.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pd_process.getWindow().setLayout(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        pd_process.getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    private void SaveLeave(ModelLeaveSvBody leaveSM, LeaveTypeInfo leaveTypeInfo, int empId, String img_str) {
        try {
            leaveSM.setLeaveApplicationId(0);
            leaveSM.setLeaveBalanceId(2);
            leaveSM.setStartDate(binding.startDate.getText().toString());
            leaveSM.setEndDate(binding.endDate.getText().toString());
            leaveSM.setReason(binding.reason.getText().toString());
            leaveSM.setEmpId(empId);
            leaveSM.setDateOfReturnsToDuty(binding.returnDate.getText().toString());
            leaveSM.setLeaveAddress(binding.leaveAddress.getText().toString());
            leaveSM.setEmergencyContactNo(binding.emContact.getText().toString());
            leaveSM.setImageBase64String(img_str);
            leaveSM.setComments(binding.comments.getText().toString());
            presenter.SaveLeave(leaveSM);
            Gson gson = new Gson();
            String data = gson.toJson(leaveSM);
            System.out.println("json" + data);

        } catch (Exception exception) {

            exception.printStackTrace();
        }

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    @Override
    public void onLeaveTypeGet(List<LeaveTypeInfo> aList) {
        try {
            ArrayAdapter<LeaveTypeInfo> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, aList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinerLeaveType.setAdapter(dataAdapter);
            binding.spinerLeaveType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    LeaveTypeInfo lt = (LeaveTypeInfo) parentView.getSelectedItem();
                    int lid = lt.getLeaveBalanceId();
                    if (lid == 2) {
                        binding.presclay.setVisibility(View.GONE);
                    }
                    binding.endDate.setText("");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onSaveSuccess(String message) {
        if (pd_process != null || pd_process.isShowing()) {
            pd_process.dismiss();
        }
        popup.show();
        popup.setCancelable(false);
        dbinding.dialogTag.setText("Success");
        dbinding.tvMsg.setText(message);
        dbinding.btnGoto.setOnClickListener(view -> {

            onBackPressed();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            popup.dismiss();
        });

    }

    @Override
    public void onSaveError(String message) {
        if (pd_process != null || pd_process.isShowing()) {
            pd_process.dismiss();
        }
        popup.show();
        popup.setCancelable(false);
        dbinding.dialogTag.setText("Error");
        dbinding.tvMsg.setText(message);
        dbinding.btnGoto.setOnClickListener(view -> {
            popup.dismiss();
        });

   /*     new androidx.appcompat.app.AlertDialog.Builder(LeaveActivity.this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }

                }).setCancelable(false).show();*/

    }

    @Override
    public void onLeaveRecordsGet(ResponseLeaveList aList) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageuri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                binding.imgaeView.setImageBitmap(bitmap);

            } catch (IOException exception) {
                exception.printStackTrace();

            }

        }
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                binding.imgaeView.setImageBitmap(photo);
            } catch (Exception ex) {
                Toast.makeText(LeaveActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
            }

        }


    }
}