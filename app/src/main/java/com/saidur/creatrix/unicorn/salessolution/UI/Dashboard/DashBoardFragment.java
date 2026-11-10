package com.saidur.creatrix.unicorn.salessolution.UI.Dashboard;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.arch.core.util.Function;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.ImagePickerActivity;
import com.github.dhaval2404.imagepicker.constant.ImageProvider;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.TP.ActivityTourPlan_;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Attendance.AttendanceActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.Customer.AddCustomerActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.Customer.ChemistCallActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.Customer.CustomerListForOrderActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.ActivityDoctorDashboard;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.ActivityDoctorEntry;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.ActivityPrescriptionEntry;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.ActivityDCP;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCR.ActivityDCR;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForABM.ActivityTwwpABM;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForMBE.ActivityTwwp;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Twwp.ForMBE.ActivityTwwpApproval;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.ActivityWSApproval;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.WorkSchedule.ActivityWorkSchedule;
import com.saidur.creatrix.unicorn.salessolution.UI.Leave.ActivityLeaveList;
import com.saidur.creatrix.unicorn.salessolution.UI.Leave.LeaveApprovalListActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.CategoryRequest.ActivityCategoryApproval;
import com.saidur.creatrix.unicorn.salessolution.UI.CategoryRequest.ActivityCategoryRequest;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.Campaign.CampaignActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.OrderTracking.OrderTrackingActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.PendingListActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.Reports.Tagetvs.ReportTargetvsActivity;

import com.saidur.creatrix.unicorn.salessolution.UI.WebViewPromoActivity;
import com.saidur.creatrix.unicorn.salessolution.UI.WebViewTourPlanActivity;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.CameraHelper;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.Constants;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityChemistCallBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.DialogSuccessCustomBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.FragmentDashBoardBinding;
import com.saidur.creatrix.unicorn.salessolution.webview.AttendanceApprovalActivity;
import com.saidur.creatrix.unicorn.salessolution.webview.TourPlanApprovalListWeb;
import com.saidur.creatrix.unicorn.salessolution.webview.TourPlanWeb;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBoardFragment extends Fragment {
    FragmentDashBoardBinding binding;
    ModelSession session;
    DBCrudHelper dbCrudHelper;
    ActivityResultLauncher<Intent> luncher;

    Uri imageuri;
    File imgFile;
    String path;

    Dialog popup;
    DialogSuccessCustomBinding dbinding;
    private static final int PERMISSION_REQUEST_CODE = 200;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Intent intentz;

    public DashBoardFragment() {
        // Required empty public constructor
    }

    public static DashBoardFragment newInstance(String param1, String param2) {
        DashBoardFragment fragment = new DashBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        /*   luncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    imageuri = result;//data.getData();
                    if (result != null) {
                        binding.imgaeView.setImageURI(imageuri);
                        binding.imgaeView.setVisibility(View.VISIBLE);
                        // viewBinding.imgbg.setVisibility(View.GONE);
                        path = getUriRealPathAboveKitkat(requireActivity(), imageuri);
                        if (path == null)
                            return;
                        imgFile = new File(path);
                        try {
                            imgFile = new Compressor(requireActivity()).compressToFile(imgFile);
                            System.out.println("file" + imgFile);
                        } catch (IOException e) {
                            Toast.makeText(requireActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });*/
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashBoardBinding.inflate(getLayoutInflater());
        dbinding=DialogSuccessCustomBinding.inflate(getLayoutInflater());
        popup=new Dialog(requireContext());
        popup.setContentView(dbinding.getRoot());
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.getWindow().setLayout(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        popup.getWindow().getAttributes().gravity = Gravity.CENTER;

        dbCrudHelper = new DBCrudHelper(getContext());
        session = dbCrudHelper.getSession();

        binding.userName.setText(session.getUserName());
        binding.designation.setText(session.getDesigName());
        //Role permission for menu
        try {
            switch (session.getRoleType()) {
                case "MBE":
               /*     binding.dashboardTWWP.setVisibility(View.VISIBLE);
                    binding.cardMBEWS.setVisibility(View.VISIBLE);*/
                    binding.tvApprovalTag.setVisibility(View.GONE);
                    binding.llApproval.setVisibility(View.GONE);
                    binding.dashboardWorkSchedule.setVisibility(View.GONE);
                   /* binding.approvalLeave.setVisibility(View.GONE);
                    binding.approvalWS.setVisibility(View.GONE);
                    binding.approvalTwwp.setVisibility(View.GONE);*/
                    binding.approvalTwwp.setVisibility(View.GONE);
                    binding.approvalAtt.setVisibility(View.GONE);
                    break;
                case "MIO":
                case "AM":
                  //  binding.dashboardTWWP.setVisibility(View.GONE);
                    binding.cardMBEWS.setVisibility(View.GONE);
//                    binding.addDcp.setVisibility(View.GONE);
//                    binding.addDcr.setVisibility(View.GONE);
                    binding.dashboardccall.setVisibility(View.GONE);

                    binding.dashboardWorkSchedule.setVisibility(View.GONE);
                    binding.tvApprovalTag.setVisibility(View.VISIBLE);
                    binding.llApproval.setVisibility(View.VISIBLE);
                    /*binding.approvalLeave.setVisibility(View.VISIBLE);
                    binding.approvalTwwp.setVisibility(View.VISIBLE);
                    binding.approvalWS.setVisibility(View.VISIBLE);*/
                    binding.approvalAtt.setVisibility(View.VISIBLE);
                    break;
                case "DZSM":
                    binding.dashboardTWWP.setVisibility(View.GONE);
                    binding.dashboardWorkSchedule.setVisibility(View.GONE);
                    binding.approvalTwwp.setVisibility(View.GONE);
                    binding.approvalAtt.setVisibility(View.VISIBLE);
                    break;
            }
            // Category request list is keyed by MBE; approvers use the Approval card
            binding.dashboardCategoryRequest.setVisibility("MBE".equals(session.getRoleType()) ? View.VISIBLE : View.GONE);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        MenuAction();
        luncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            int resultCode = result.getResultCode();
            Uri data = result.getData().getData();
            intentz = result.getData();
            if (resultCode == Activity.RESULT_OK && data != null) {
                // imageuri=intent.getData();
                imageuri = data;
                Intent in = new Intent(getContext(), ActivityPrescriptionEntry.class);
                in.putExtra("ImageUri", String.valueOf(imageuri));
                startActivity(in);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        LocationManager locationManagerdd = (LocationManager) requireActivity().getSystemService(LOCATION_SERVICE);
        if (!locationManagerdd.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showGPSDisabledAlertToUser();
        }
    }
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireActivity());
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        (dialog, id) -> {
                            Intent callGPSSettingIntent = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(callGPSSettingIntent);
                        });


        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    private void MenuAction() {
        /*Plan And Attend menu*/
        binding.dashboardTWWP.setOnClickListener(v -> {
            Intent i = new Intent(requireActivity(), ActivityTwwp.class);
            startActivity(i);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.clickMBEWS.setOnClickListener(view -> {
            Intent iss = new Intent(requireActivity(), ActivityWorkSchedule.class);
            startActivity(iss);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.dashboardWorkSchedule.setOnClickListener(v -> {
            Intent i = new Intent(requireActivity(), ActivityTwwpABM.class);
            startActivity(i);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.dashboardAttendanceCard.setOnClickListener(v -> {
            if (!NetworkInformation.isConnected(requireActivity())) {
                SnackBarManagement.NoInternetSnackbar(binding.masterLayoutId);
            } else {
                Intent i = new Intent(requireActivity(), AttendanceActivity.class);
                startActivity(i);
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

        });
        binding.dashboardLeavesCard.setOnClickListener(view -> {
            Intent ta = new Intent(requireActivity(), ActivityLeaveList.class);
            startActivity(ta);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        /*scientific menu*/
        binding.dashboardDoctorCard.setOnClickListener(view -> {
            if (NetworkInformation.isConnected(requireActivity())) {
                Intent i = new Intent(requireActivity(), ActivityDoctorEntry.class);
                startActivity(i);
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            } else {
                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "No Internet Connectivity");
            }

        });

        binding.dashboardTourPlanCard.setOnClickListener(v -> {
            if (NetworkInformation.isConnected(requireActivity())) {
                Intent i = new Intent(requireActivity(), TourPlanWeb.class);
                startActivity(i);
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "No Internet Connectivity");
            }
        });
        binding.addDcp.setOnClickListener(view -> {
            if (NetworkInformation.isConnected(requireActivity())) {
                Intent i = new Intent(requireActivity(), ActivityDCP.class);
                startActivity(i);
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            } else {
                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "No Internet Connectivity");
            }

        });
        binding.addDcr.setOnClickListener(view -> {
            if (NetworkInformation.isConnected(requireActivity())) {
                Constants.From="Doctor_DASHBOARD";
                Intent i = new Intent(requireActivity(), ActivityDCR.class);
                startActivity(i);
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            } else {
                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "No Internet Connectivity");
            }

        });
        binding.dashboardRx.setOnClickListener(v -> {
            call_prescription();
        });
        /*Selling Actvity menu*/
        binding.dashboardCustomerCard.setOnClickListener(view -> {
            if (NetworkInformation.isConnected(requireActivity())) {
                Intent i = new Intent(requireActivity(), AddCustomerActivity.class);
                startActivity(i);
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            } else {
                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "No Internet Connectivity");
            }

        });
        binding.clickCampaing.setOnClickListener(view -> {
            if (NetworkInformation.isConnected(requireActivity())) {
                Intent i = new Intent(requireActivity(), CampaignActivity.class);
                startActivity(i);
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            } else {
                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "No Internet Connectivity");
            }

        });
        binding.dashboardPlaceOrder.setOnClickListener(view -> {
            if (NetworkInformation.isConnected(requireActivity())) {
                Intent i = new Intent(requireActivity(), CustomerListForOrderActivity.class);
                startActivity(i);
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            } else {
                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "No Internet Connectivity");
            }

        });
        binding.dashboardOrderTracking.setOnClickListener(view -> {
            if (NetworkInformation.isConnected(requireActivity())) {
                Intent i = new Intent(requireActivity(), OrderTrackingActivity.class);
                startActivity(i);
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            } else {
                SnackBarManagement._warning_CustomMessage(binding.masterLayoutId, "No Internet Connectivity");
            }

        });
        /*Other Actvity menu*/
        binding.dashboardPendingmenuCard.setOnClickListener(v -> {
            //SnackBarManagement._warning_CustomMessage(getView(),"Comming Soon!!");
            Intent i = new Intent(requireActivity(), PendingListActivity.class);
            startActivity(i);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.clickTA.setOnClickListener(view -> {
            Intent ta = new Intent(requireActivity(), ReportTargetvsActivity.class);
            startActivity(ta);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.clickPM.setOnClickListener(view -> {
            Intent ta = new Intent(requireActivity(), WebViewPromoActivity.class);
            startActivity(ta);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        /*Approval Actvity menu*/
        binding.approvalTwwp.setOnClickListener(view -> {
            Intent ta = new Intent(requireActivity(), ActivityTwwpApproval.class);
            ta.putExtra("Who","Home");
            startActivity(ta);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });   binding.approvalAtt.setOnClickListener(view -> {
            Intent ta = new Intent(requireActivity(), AttendanceApprovalActivity.class);
            ta.putExtra("Who","Home");
            startActivity(ta);
            requireActivity().overridePendingTransition(R  .anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.approvalWS.setOnClickListener(view -> {
//            Intent ta = new Intent(requireActivity(), ActivityWSApproval.class);
//            ta.putExtra("Who","Home");
//            startActivity(ta);
//            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            Intent i = new Intent(getActivity(), TourPlanApprovalListWeb.class);
            startActivity(i);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.approvalLeave.setOnClickListener(view -> {
            Intent ta = new Intent(requireActivity(), LeaveApprovalListActivity.class);
            startActivity(ta);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.approvalCategory.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), ActivityCategoryApproval.class));
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.dashboardCategoryRequest.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), ActivityCategoryRequest.class));
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        binding.dashboardccall.setOnClickListener(view -> {
            Intent ta = new Intent(requireActivity(), ChemistCallActivity.class);
            startActivity(ta);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }



    private void call_prescription() {
        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        if (ContextCompat.checkSelfPermission(getActivity(),
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


     /*       ImagePicker.Builder with = ImagePicker.with(this);
            with.crop();
            with.compress(1024);
            with.maxResultSize(1080, 1080);
            with.createIntent(new Function1<Intent, Unit>() {
                @Override
                public Unit invoke(Intent Intent) {
                    luncher.launch(Intent);
                    return null;
                }
            });*/
         /*   luncher.launch(ImagePicker.Companion.with(this)
                            .provider(ImageProvider.BOTH)
                            .createIntent());*/

       /*     ImagePicker.Companion.with(this)
                    .crop()
                    .maxResultSize(512, 512)
                    .createIntentFromDialog((Function1) (new Function1() {
                        public Object invoke(Object var1) {
                            this.invoke((Intent) var1);
                            return Unit.INSTANCE;
                        }

                        public final void invoke(@NotNull Intent it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            launcher.launch(it);
                        }
                    }));*/
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE);
        }

    }
    private String getUriRealPathAboveKitkat(Activity activity, Uri contentURI) {
        String ret = "";
        if (activity != null && contentURI != null) {
            if (CameraHelper.isContentUri(contentURI)) {
                //if(isGooglePhotoDoc(contentURI.getAuthority()))
                if (CameraHelper.isGooglePhotoDoc(contentURI.getAuthority())) {
                    ret = contentURI.getLastPathSegment();
                } else {
                    ret = getImageRealPath(requireActivity().getContentResolver(), contentURI, null);
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
                        ret = getImageRealPath(requireActivity().getContentResolver(), mediaContentUri, whereClause);
                    }
                } else if (CameraHelper.isDownloadDoc(uriAuthority)) {
                    // Build download URI.
                    Uri downloadUri = Uri.parse("content://downloads/public_downloads");
                    // Append download document id at URI end.
                    Uri downloadUriAppendId = ContentUris.withAppendedId(downloadUri, Long.valueOf(documentId));
                    ret = getImageRealPath(requireActivity().getContentResolver(), downloadUriAppendId, null);
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
}