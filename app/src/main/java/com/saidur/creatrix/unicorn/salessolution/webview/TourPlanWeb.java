package com.saidur.creatrix.unicorn.salessolution.webview;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.ServiceWorkerClient;
import android.webkit.ServiceWorkerController;
import android.webkit.ServiceWorkerWebSettings;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.MainActivity;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityTourPlanApprovalListWebBinding;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityTourPlanWebBinding;

public class TourPlanWeb extends AppCompatActivity {

    private ActivityTourPlanWebBinding binding;
    private ModelSession session;
    private DBCrudHelper dbCrudHelper;
    String empId;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTourPlanWebBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Session
        DBCrudHelper dbCrudHelper = new DBCrudHelper(this);
        ModelSession session = dbCrudHelper.getSession();
        empId = String.valueOf(session.getEmpInfoId());
        url = "http://103.244.247.93:187/TourPlanApps.aspx?EmpId=" + empId;

        // Toolbar back: সরাসরি Activity finish
        binding.toolbarCustom.setNavigationOnClickListener(v -> finish());

        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.tourPlanWebView.reload();
            binding.swipeRefresh.setRefreshing(false);
        });

        // Retry button
        binding.btnRetry.setOnClickListener(v -> {
            binding.errorLayout.setVisibility(View.GONE);
            binding.loadingLayout.setVisibility(View.VISIBLE);
            binding.tourPlanWebView.reload();
        });

        setupWebView();
    }

    private void setupWebView() {
        WebView webView = binding.tourPlanWebView;
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setSupportMultipleWindows(true);
        s.setJavaScriptCanOpenWindowsAutomatically(true);

        // প্রথমে loading দেখান
        binding.loadingLayout.setVisibility(View.VISIBLE);
        binding.errorLayout.setVisibility(View.GONE);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // পেজ লোড শুরু হলে loading দেখান
                binding.loadingLayout.setVisibility(View.VISIBLE);
                binding.errorLayout.setVisibility(View.GONE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // পেজ লোড শেষ হলে loading লুকান
                binding.loadingLayout.setVisibility(View.GONE);
                view.clearHistory();
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Error হলে error layout দেখান
                binding.loadingLayout.setVisibility(View.GONE);
                binding.errorLayout.setVisibility(View.VISIBLE);
                binding.errorText.setText("Can't load page.\n" + description);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView temp = new WebView(view.getContext());
                temp.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView v, String url, Bitmap favicon) {
                        view.loadUrl(url);
                        v.destroy();
                        super.onPageStarted(v, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView v, String url) {
                        view.clearHistory();
                        super.onPageFinished(v, url);
                    }
                });
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(temp);
                resultMsg.sendToTarget();
                return true;
            }
        });

        webView.clearCache(true);
        webView.clearFormData();
        webView.loadUrl(url);
    }

    // Back press = শুধু এই Activity বন্ধ
    @Override
    public void onBackPressed() {
        finish();
    }
}
