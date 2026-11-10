package com.saidur.creatrix.unicorn.salessolution.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.MainActivity;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Dashboard.DashBoardFragment;

public class WebViewTourPlanActivity extends AppCompatActivity {
    private WebView webView;
    private ModelSession session;
    private DBCrudHelper dbCrudHelper;
    private String URL = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_tour_plan);

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(WebViewTourPlanActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish(); // যদি চাও যে বর্তমান Activity বন্ধ হয়ে যাক
        });

        // Initialize DB and session
        dbCrudHelper = new DBCrudHelper(this);
        session = dbCrudHelper.getSession();

        // Set URL based on session
        if (session != null && session.getEmpInfoId() > 0) {
            URL = "http://103.244.247.93:91/TourPlanApprovalListForApp.aspx?empId=" + session.getEmpInfoId();
        } else {
            URL = "about:blank"; // fallback to blank page
        }

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript
        webSettings.setDomStorageEnabled(true); // Enable DOM storage
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        // Allow mixed content for HTTP on Android 5.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // Enable WebView debugging on debug builds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        // Ensure links open inside WebView
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        // Load the page
        webView.loadUrl(URL);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WebViewTourPlanActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish(); // যদি চাও যে বর্তমান Activity বন্ধ হয়ে যাক
    }

}