package com.saidur.creatrix.unicorn.salessolution.UI.CategoryRequest;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Network.ApiCategoryRequest;
import com.saidur.creatrix.unicorn.salessolution.Network.Response.ResponseResultInfo;
import com.saidur.creatrix.unicorn.salessolution.Network.RetrofitClientInstance;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.NetworkInformation;
import com.saidur.creatrix.unicorn.salessolution.UtilsApp.SnackBarManagement;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityCategoryDocUploadBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ItemCategoryDocBinding;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** Screen 2: four mandatory documents, then submit. */
public class ActivityCategoryDocUpload extends AppCompatActivity {
    private static final String[] TITLES = {"Shop Photo", "NID", "Trade License", "Drug License"};
    private static final int REQ_BASE = 7100; // request codes 7100..7103, one per document

    private ActivityCategoryDocUploadBinding binding;
    private ItemCategoryDocBinding[] rows;
    private final String[] docs = new String[4]; // base64 jpg per document
    private CatReqModels.Customer customer;
    private int typeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryDocUploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        customer = (CatReqModels.Customer) getIntent().getSerializableExtra("customer");
        typeId = getIntent().getIntExtra("typeId", 0);
        String typeName = getIntent().getStringExtra("typeName");

        binding.header.tvTitle.setText("Document Upload");
        binding.header.ivBack.setOnClickListener(v -> finish());
        binding.tvName.setText(customer.CustomerName);
        binding.tvDetails.setText(HtmlCompat.fromHtml("<b>Code :</b> " + ActivityCategoryRequest.nz(customer.CustomerCode)
                + "<br><b>Market :</b> " + ActivityCategoryRequest.nz(customer.Market)
                + "<br><b>Applying For :</b> " + ActivityCategoryRequest.nz(typeName), HtmlCompat.FROM_HTML_MODE_LEGACY));

        rows = new ItemCategoryDocBinding[]{binding.docShop, binding.docNid, binding.docTrade, binding.docDrug};
        for (int i = 0; i < rows.length; i++) {
            final int idx = i;
            rows[i].tvTitle.setText(HtmlCompat.fromHtml(TITLES[i] + " <font color='#C12222'>*</font>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            rows[i].btnUpload.setOnClickListener(v -> pick(idx));
        }
        binding.btnSubmit.setOnClickListener(v -> submit());
        refresh();
    }

    private void pick(int idx) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            return;
        }
        // 400 KB cap per photo keeps the 4-photo request under IIS's default 4 MB body limit
        ImagePicker.Companion.with(this).compress(400).maxResultSize(1080, 1080).start(REQ_BASE + idx);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int idx = requestCode - REQ_BASE;
        if (idx < 0 || idx >= docs.length || resultCode != RESULT_OK || data == null || data.getData() == null) return;
        Uri uri = data.getData();
        try (InputStream in = getContentResolver().openInputStream(uri)) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            for (int n; (n = in.read(buf)) > 0; ) out.write(buf, 0, n);
            docs[idx] = Base64.encodeToString(out.toByteArray(), Base64.NO_WRAP);
            ImageView thumb = rows[idx].ivThumb;
            thumb.setPadding(0, 0, 0, 0);
            thumb.setImageURI(uri);
        } catch (Exception e) {
            docs[idx] = null;
            SnackBarManagement._error_CustomMessage(binding.getRoot(), "Could not read image.");
        }
        refresh();
    }

    private void refresh() {
        int done = 0;
        for (int i = 0; i < docs.length; i++) {
            boolean ok = docs[i] != null;
            if (ok) done++;
            rows[i].tvStatus.setText(ok ? "Uploaded" : "Required – not uploaded");
            rows[i].tvStatus.setTextColor(ContextCompat.getColor(this, ok ? R.color.color_main_deep : R.color.red));
            rows[i].btnUpload.setText(ok ? "Change" : "Upload");
        }
        binding.header.tvCount.setText("Uploaded : " + done + " / " + docs.length);
        binding.btnSubmit.setEnabled(done == docs.length);
    }

    private void submit() {
        if (!NetworkInformation.isConnected(this)) {
            SnackBarManagement.NoInternetSnackbar(binding.getRoot());
            return;
        }
        CatReqModels.SaveBody body = new CatReqModels.SaveBody();
        body.EmpId = new DBCrudHelper(this).getSession().getEmpInfoId();
        body.CustomerMasterId = customer.CustomerMasterId;
        body.CustomerTypeId = typeId;
        body.ShopPhoto = docs[0];
        body.NID = docs[1];
        body.TradeLicense = docs[2];
        body.DrugLicense = docs[3];

        ProgressDialog pd = ProgressDialog.show(this, null, "Submitting...", true, false);
        binding.btnSubmit.setEnabled(false);
        RetrofitClientInstance.getRetrofitInstance().create(ApiCategoryRequest.class).save(body)
                .enqueue(new Callback<ResponseResultInfo>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseResultInfo> call, @NonNull Response<ResponseResultInfo> response) {
                        pd.dismiss();
                        ResponseResultInfo r = response.body();
                        if (r != null && Boolean.TRUE.equals(r.isSuccess)) {
                            setResult(RESULT_OK);
                            finish();
                            return;
                        }
                        binding.btnSubmit.setEnabled(true);
                        SnackBarManagement._error_CustomMessage(binding.getRoot(),
                                r != null && r.Msd != null ? r.Msd : getString(R.string.api_error));
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseResultInfo> call, @NonNull Throwable t) {
                        pd.dismiss();
                        binding.btnSubmit.setEnabled(true);
                        SnackBarManagement._error_CustomMessage(binding.getRoot(), getString(R.string.api_error));
                    }
                });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
