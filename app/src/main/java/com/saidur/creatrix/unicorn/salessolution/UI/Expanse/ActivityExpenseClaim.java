package com.saidur.creatrix.unicorn.salessolution.UI.Expanse;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.constant.ImageProvider;
import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Adapters._entrytxtWithValue_Recycler;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.IExpenseClaim;
import com.saidur.creatrix.unicorn.salessolution.Model.Expense.ExpenseClaimMaster;
import com.saidur.creatrix.unicorn.salessolution.Model.Expense.ExpenseTypeDetails;
import com.saidur.creatrix.unicorn.salessolution.Model.Expense.ExpenseTypeMaster;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterExpense;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.ActivityPrescriptionEntry;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.CameraHelper;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityExpenseClaimBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ActivityExpenseClaim extends AppCompatActivity implements IExpenseClaim.View,_entrytxtWithValue_Recycler.RecyclerviewExpenseClaimListner {
    ActivityExpenseClaimBinding binding;
    ActivityResultLauncher<Intent> luncher;
    Uri imageuri;
    File imgFile;
    String path;
    ModelSession session;
    DBCrudHelper dbCrudHelper;
    private static final int PERMISSION_REQUEST_CODE = 200;
    PresenterExpense presenter;
    List<ExpenseTypeMaster> compareTypeList;
    List<ExpenseTypeDetails> aSmDetail = new ArrayList<>();
    private _entrytxtWithValue_Recycler mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpenseClaimBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbCrudHelper = new DBCrudHelper(ActivityExpenseClaim.this);
        session=dbCrudHelper.getSession();
        binding.camera.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(ActivityExpenseClaim.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                ImagePicker.Companion.with(this)
                        .crop()
                        //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)//Final image resolution will be less than 1080 x 1080(Optional)
                        .provider(ImageProvider.BOTH)
                        .createIntent(new Function1<Intent, Unit>() {
                            @Override
                            public Unit invoke(Intent intent) {
                                luncher.launch(intent);
                                return null;
                            }
                        });
            } else {
                ActivityCompat.requestPermissions(ActivityExpenseClaim.this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION_REQUEST_CODE);
            }
        });
        luncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            int resultCode = result.getResultCode();
            Uri data = result.getData().getData();

            if (resultCode == Activity.RESULT_OK && data != null) {
                // imageuri=intent.getData();
                imageuri = data;
                if (imageuri != null) {
                    binding.imgaeView.setImageURI(imageuri);
                    path = getUriRealPathAboveKitkat(ActivityExpenseClaim.this, imageuri);
                    if (path == null)
                        return;
                    imgFile = new File(path);
                    try {
                        imgFile = new Compressor(ActivityExpenseClaim.this).compressToFile(imgFile);
                        System.out.println("file" + imgFile);
                    } catch (IOException e) {
                        Toast.makeText(ActivityExpenseClaim.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        presenter = new PresenterExpense(this, this);
        presenter.GetExpenseType();

        binding.btnSubmit.setOnClickListener(v -> {
            String img_str = null;
            try {
                Bitmap bitmap = new Compressor(ActivityExpenseClaim.this).compressToBitmap(imgFile);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                byte[] image = stream.toByteArray();
                img_str = Base64.encodeToString(image, 0);
                System.out.println("img : " + img_str);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ExpenseTypeMaster typeMaster = (ExpenseTypeMaster) binding.expanseType.getSelectedItem();
            if (typeMaster.isImageRequired()) {
                if (binding.imgaeView.getDrawable() == null && img_str == null) {
                    SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Bill Image required");
                    return;
                }
            }
            SaveExpenseClaim(session.getEmpInfoId(), img_str);
        });
    }

    private String getUriRealPathAboveKitkat(ActivityExpenseClaim activity, Uri contentURI) {
        String ret = "";
        if (activity != null && contentURI != null) {
            if (CameraHelper.isContentUri(contentURI)) {
                //if(isGooglePhotoDoc(contentURI.getAuthority()))
                if (CameraHelper.isGooglePhotoDoc(contentURI.getAuthority())) {
                    ret = contentURI.getLastPathSegment();
                } else {
                    ret = getImageRealPath(getContentResolver(), contentURI, null);
                }
            } else if (CameraHelper.isFileUri(contentURI)) {
                ret = contentURI.getPath();
            } else if (CameraHelper.isDocumentUri(activity, contentURI)) {
                // Get uri related document id.
                String documentId = DocumentsContract.getDocumentId(contentURI);
                // Get uri authority.
                String uriAuthority = contentURI.getAuthority();
                if (CameraHelper.isMediaDoc(uriAuthority)) {
                    String[] idArr = documentId.split(":");
                    if (idArr.length == 2) {
                        // First item is document type.
                        String docType = idArr[0];
                        // Second item is document real id.
                        String realDocId = idArr[1];
                        // Get content uri by document type.
                        Uri mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        if ("image".equals(docType)) {
                            mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        }
                        // Get where clause with real document id.
                        String whereClause = MediaStore.Images.Media._ID + " = " + realDocId;
                        ret = getImageRealPath(getContentResolver(), mediaContentUri, whereClause);
                    }
                } else if (CameraHelper.isDownloadDoc(uriAuthority)) {
                    // Build download URI.
                    Uri downloadUri = Uri.parse("content://downloads/public_downloads");
                    // Append download document id at URI end.
                    Uri downloadUriAppendId = ContentUris.withAppendedId(downloadUri, Long.valueOf(documentId));
                    ret = getImageRealPath(getContentResolver(), downloadUriAppendId, null);
                } else if (CameraHelper.isExternalStoreDoc(uriAuthority)) {
                    String[] idArr = documentId.split(":");
                    if (idArr.length == 2) {
                        String type = idArr[0];
                        String realDocId = idArr[1];
                        if ("primary".equalsIgnoreCase(type)) {
                            ret = Environment.getExternalStorageDirectory() + "/" + realDocId;
                        }
                    }
                }
            }
        }
        return ret;
    }

    private String getImageRealPath(ContentResolver contentResolver, Uri uri, String whereClause) {
        String ret = "";
        // Query the URI with the condition.
        Cursor cursor = contentResolver.query(uri, null, whereClause, null, null);
        if (cursor != null) {
            boolean moveToFirst = cursor.moveToFirst();
            if (moveToFirst) {
                // Get columns name by URI type.
                String columnName = MediaStore.Images.Media.DATA;
                if (uri == MediaStore.Images.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Images.Media.DATA;
                } else if (uri == MediaStore.Audio.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Audio.Media.DATA;
                } else if (uri == MediaStore.Video.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Video.Media.DATA;
                }
                // Get column index.
                int imageColumnIndex = cursor.getColumnIndex(columnName);
                // Get column value which is the uri related file local path.
                ret = cursor.getString(imageColumnIndex);
            }
        }
        return ret;
    }

    private void SaveExpenseClaim(int empId,String img_str) {
        try {
            String exDate = binding.expdate.getText().toString();
            String rmrks = binding.remarksTxt.getText().toString();
            if (TextUtils.isEmpty(binding.amountValue.getText().toString())) {
                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Amount is required");
                return;
            }
            ExpenseTypeMaster typeMaster;
            typeMaster = (ExpenseTypeMaster) binding.expanseType.getSelectedItem();
            double amount = Double.parseDouble(binding.amountValue.getText().toString());
            ExpenseClaimMaster claimMaster = new ExpenseClaimMaster();
            claimMaster.setExpenseClaimID("0");
            claimMaster.setExpenseTypeId(typeMaster.getExpenseTypeId());
            claimMaster.setExpenseDate(exDate);
            claimMaster.setAmount(amount);
            claimMaster.setRemarks(rmrks);
            claimMaster.setaDetailList(aSmDetail);

            boolean hasanyEmptyValue = false;
            for (int i = 0; i < aSmDetail.size(); i++) {
                hasanyEmptyValue = aSmDetail.get(i).getValueText().isEmpty() && aSmDetail.get(i).isRequied() ? true : false;

                if (hasanyEmptyValue) {
                    SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Please Fill up Mandatory Fields");
                    return;
                }

            }

            claimMaster.setImageBase64String(img_str);
            claimMaster.setEmpInfoId(empId);
            claimMaster.setFromApp(true);

            Gson gson = new Gson();
            String expense = gson.toJson(claimMaster);
            System.out.println("offline save" + expense);


            presenter.SaveExpenseClaim(claimMaster);
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    @Override
    public void onExpenseTypeGet(List<ExpenseTypeMaster> aList) {
        if (aList != null && aList.size() > 0) {
            try {
                compareTypeList = aList;
                ArrayAdapter<ExpenseTypeMaster> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, aList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.expanseType.setAdapter(dataAdapter);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Expense Not Saved!!");
        }
    }

    @Override
    public void onExpenseTypeDetailsGet(List<ExpenseTypeDetails> aList) {
        if (aList != null) {
            aSmDetail.clear();
            aSmDetail = aList;
            mAdapter = new _entrytxtWithValue_Recycler(aList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            binding.recyclerViewitem.setLayoutManager(mLayoutManager);
            binding.recyclerViewitem.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerViewitem.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveSuccess(String message) {

    }

    @Override
    public void onSaveError(String message) {

    }

    @Override
    public void onEditTextFocusChange(int position, String value) {
        aSmDetail.get(position).setValueText(value);
    }
}