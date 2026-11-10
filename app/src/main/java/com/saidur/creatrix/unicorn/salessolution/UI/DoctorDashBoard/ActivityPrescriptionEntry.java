package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBDoctorHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.IPrescription;
import com.saidur.creatrix.unicorn.salessolution.MainActivity;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.DoctorListViewModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelBrand;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelProductId;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelRx;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.Presentar.PresenterPrescription;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.CameraHelper;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.UtilityHelper;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityPrescriptionEntryBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.zelory.compressor.Compressor;

public class ActivityPrescriptionEntry extends AppCompatActivity implements IPrescription.View {
    ActivityPrescriptionEntryBinding binding;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int SELECT_DOCTOR_PLACEHOLDER_ID = -1;
    Uri imageuri;
    File imgFile;
    String path, empid;
    PresenterPrescription presenterPrescription;
    ProgressDialog pd;
    boolean checked = false;
    List<ModelProductId> brandList = new ArrayList<>();
    ModelSession session;
    DBCrudHelper dbCrudHelper;
    DBDoctorHelper dbDoctorHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrescriptionEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbarCustom.setNavigationOnClickListener(v -> goToHome());
        dbCrudHelper= new DBCrudHelper(ActivityPrescriptionEntry.this);
        dbDoctorHelper= new DBDoctorHelper(ActivityPrescriptionEntry.this);
        session = dbCrudHelper.getSession();
        presenterPrescription = new PresenterPrescription(this, ActivityPrescriptionEntry.this);
        List<DoctorListViewModel> datas = dbDoctorHelper.getDoctorListFromSQLite();
        LoadDoctor(datas);

        String strImgUri = getIntent().getStringExtra("ImageUri");
        imageuri = Uri.parse(strImgUri);
        if (imageuri != null) {
            binding.imgaeView.setImageURI(imageuri);
            path = getUriRealPathAboveKitkat(ActivityPrescriptionEntry.this, imageuri);
            if (path == null)
                return;
            imgFile = new File(path);
            try {
                imgFile = new Compressor(ActivityPrescriptionEntry.this).compressToFile(imgFile);
                System.out.println("file" + imgFile);
            } catch (IOException e) {
                Toast.makeText(ActivityPrescriptionEntry.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        binding.presdate.setText(UtilityHelper._GetCurrentDate());
        binding.camera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(ActivityPrescriptionEntry.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                ImagePicker.Companion.with(ActivityPrescriptionEntry.this)
                        .crop()
                        //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            } else {
                ActivityCompat.requestPermissions(ActivityPrescriptionEntry.this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION_REQUEST_CODE);
            }
        });
        View.OnClickListener rxDateClickListener = v -> UtilityHelper._datePickerDialogeForDates(binding.rXDate, ActivityPrescriptionEntry.this);
        binding.rxDateRow.setOnClickListener(rxDateClickListener);
        binding.datePickerDeliveryDate.setOnClickListener(rxDateClickListener);
        View.OnClickListener brandClickListener = view -> {
            List<ModelProductId> productList = new ArrayList<>();
            Bundle args = new Bundle();
            args.putString("From", "ADDProduct");
            DialogFragment dialog = BrandDialogFragment.newInstance();
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "CatList");
            ((BrandDialogFragment) dialog).setCallback(new BrandDialogFragment.callbackBrand() {
                @Override
                public void onActionClick(List<ModelProduct> brandLists) {
                    for (int i = 0; i < brandLists.size(); i++) {
                        ModelProductId mp = new ModelProductId();
                        int productId = brandLists.get(i).getProductId();
                        mp.setProductId(productId);
                        productList.add(mp);
                    }
                    brandList = productList;
                }

                @Override
                public void onActionClickValue(String value) {
                    binding.tvBrandName.setText(value);
                }
            });
        };
        binding.brandRow.setOnClickListener(brandClickListener);
        binding.spinnerBrand.setOnClickListener(brandClickListener);
        binding.btnOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    checked = true;
                    binding.etDocName.setVisibility(View.VISIBLE);
                    binding.llDoctor.setVisibility(View.GONE);
                } else {
                    checked = false;
                    binding.etDocName.setVisibility(View.GONE);
                    binding.llDoctor.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.btnSave.setOnClickListener(view -> {
            if (!isDoctorInputValid()) {
                return;
            }
            if (binding.imgaeView.getDrawable() == null) {
                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Prescription Image required");
                return;
            }
            pd = new ProgressDialog(ActivityPrescriptionEntry.this);
            pd.setMessage("Rx is Submitting.... Please wait");
            pd.show();
            pd.setCanceledOnTouchOutside(false);
            String img_str = null;
            try {
                Bitmap bitmap = new Compressor(ActivityPrescriptionEntry.this).compressToBitmap(imgFile);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                byte[] image = stream.toByteArray();
                img_str = Base64.encodeToString(image, 0);
                System.out.println("img : " + img_str);
            } catch (IOException e) {
                e.printStackTrace();
            }
            callSubmitApi(img_str);
        });
    }

    private void goToHome() {
        Intent intent = new Intent(ActivityPrescriptionEntry.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void LoadDoctor(List<DoctorListViewModel> datas) {
        List<DoctorListViewModel> doctors = new ArrayList<>();
        DoctorListViewModel placeholder = new DoctorListViewModel();
        placeholder.setDoctorId(SELECT_DOCTOR_PLACEHOLDER_ID);
        placeholder.setDoctorName("-- Select Doctor --");
        doctors.add(placeholder);
        if (datas != null) {
            doctors.addAll(datas);
        }

        DoctorSearchAdapter docAdapter = new DoctorSearchAdapter(doctors);
        binding.spinnerDoctor.setTitle("Select Doctor");
        binding.spinnerDoctor.setPositiveButton("Close");
        binding.spinnerDoctor.setPrompt("Search by name / code / contact");
        binding.spinnerDoctor.setAdapter(docAdapter);
        binding.spinnerDoctor.setSelection(0, false);
    }

    private void callSubmitApi(String img_str) {
        ModelRx rxs = new ModelRx();
        rxs.setSubmitDate(UtilityHelper._GetCurrentDate());
        rxs.setRxDate(binding.rXDate.getText().toString());
        if (checked) {
            rxs.setRxDoctor("");
            rxs.setRxOtherDoctor(binding.etDocName.getText().toString().trim());
        } else {
            DoctorListViewModel doctor = getSelectedDoctor();
            int docid = doctor.getDoctorId();
            rxs.setRxDoctor(String.valueOf(docid));
            rxs.setRxOtherDoctor("");
        }

        rxs.setRxBrand(brandList);
        rxs.setRxImg(img_str);
        rxs.setSubmitby(String.valueOf(session.getEmpInfoId()));
        Gson gson = new Gson();
        String data = gson.toJson(rxs);
        System.out.println(data);
        presenterPrescription.submitRx(rxs);
    }

    private boolean isDoctorInputValid() {
        if (checked) {
            if (binding.etDocName.getText().toString().trim().isEmpty()) {
                SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Doctor name required");
                return false;
            }
            return true;
        }

        DoctorListViewModel doctor = getSelectedDoctor();
        if (doctor == null || isPlaceholderDoctor(doctor)) {
            SnackBarManagement._warning_CustomMessage(binding.getRoot(), "Please select a doctor");
            return false;
        }
        return true;
    }

    private DoctorListViewModel getSelectedDoctor() {
        Object selected = binding.spinnerDoctor.getSelectedItem();
        if (selected instanceof DoctorListViewModel) {
            return (DoctorListViewModel) selected;
        }
        return null;
    }

    private boolean isPlaceholderDoctor(DoctorListViewModel doctor) {
        return doctor == null || doctor.getDoctorId() == SELECT_DOCTOR_PLACEHOLDER_ID;
    }

    private String getDoctorDisplayText(DoctorListViewModel doctor) {
        if (doctor == null) {
            return "";
        }
        if (isPlaceholderDoctor(doctor)) {
            return safeString(doctor.getDoctorName());
        }

        String code = safeString(doctor.getDoctorCode());
        String name = safeString(doctor.getDoctorName());
        if (!code.isEmpty() && !name.isEmpty()) {
            return code + " : " + name;
        }
        if (!name.isEmpty()) {
            return name;
        }
        return code;
    }

    private String getDoctorSearchText(DoctorListViewModel doctor) {
        if (doctor == null) {
            return "";
        }
        return (getDoctorDisplayText(doctor) + " "
                + safeString(doctor.getDocContact()) + " "
                + safeString(doctor.getMarketName()) + " "
                + doctor.getDoctorId()).toLowerCase(Locale.US);
    }

    private String safeString(String value) {
        return value == null ? "" : value.trim();
    }

    private class DoctorSearchAdapter extends ArrayAdapter<DoctorListViewModel> {
        private final List<DoctorListViewModel> allDoctors;

        DoctorSearchAdapter(List<DoctorListViewModel> doctors) {
            super(ActivityPrescriptionEntry.this, R.layout.spinner_doctor_item, new ArrayList<>(doctors));
            allDoctors = new ArrayList<>(doctors);
            setDropDownViewResource(R.layout.spinner_doctor_item);
        }

        @Override
        public boolean isEnabled(int position) {
            return !isPlaceholderDoctor(getItem(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            bindDoctorText(view, getItem(position));
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            bindDoctorText(view, getItem(position));
            return view;
        }

        @Override
        public Filter getFilter() {
            return doctorFilter;
        }

        private void bindDoctorText(View view, DoctorListViewModel doctor) {
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                boolean enabled = !isPlaceholderDoctor(doctor);
                textView.setText(getDoctorDisplayText(doctor));
                textView.setEnabled(enabled);
                textView.setAlpha(enabled ? 1f : 0.6f);
            }
        }

        private final Filter doctorFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint == null ? "" : constraint.toString().trim().toLowerCase(Locale.US);
                List<DoctorListViewModel> filtered = new ArrayList<>();
                if (query.isEmpty()) {
                    filtered.addAll(allDoctors);
                } else {
                    for (DoctorListViewModel doctor : allDoctors) {
                        if (!isPlaceholderDoctor(doctor) && getDoctorSearchText(doctor).contains(query)) {
                            filtered.add(doctor);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filtered;
                results.count = filtered.size();
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                if (results.values != null) {
                    addAll((List<DoctorListViewModel>) results.values);
                }
                notifyDataSetChanged();
            }
        };
    }

    private String getUriRealPathAboveKitkat(ActivityPrescriptionEntry activity, Uri contentURI) {
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

    @Override
    public void OnSuccessRx(boolean b) {
        if (b) {

            new AlertDialog.Builder(ActivityPrescriptionEntry.this)
                    .setIcon(R.drawable.tikiconwhite)
                    .setTitle("Success")
                    .setMessage("Rx submitted successfully.")
                    .setPositiveButton("OK", (dialog, which) -> onBackPressed()).setCancelable(false).show();
        }else {
            SnackBarManagement._error_CustomMessage(binding.getRoot(),"Something went wrong");
        }
    }

    @Override
    public void OnGetRxList(List<ModelRx> aList) {

    }

    @Override
    public void OnError(int type) {

    }

    @Override
    public void OnErrorReport(String msg) {

    }


}
