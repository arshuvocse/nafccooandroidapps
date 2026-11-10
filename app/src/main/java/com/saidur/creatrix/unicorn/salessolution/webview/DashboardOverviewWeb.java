package com.saidur.creatrix.unicorn.salessolution.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.databinding.ActivityDashboardOverviewWebBinding;

public class DashboardOverviewWeb extends AppCompatActivity {

    private ActivityDashboardOverviewWebBinding binding;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardOverviewWebBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBCrudHelper dbCrudHelper = new DBCrudHelper(this);
        ModelSession session = dbCrudHelper.getSession();
        String empId = session != null ? String.valueOf(session.getEmpInfoId()) : "";
        url = "http://103.244.247.93:187/DoctorModule_UI/DasboardOverview.aspx?EmpId=" + empId;

        binding.toolbarCustom.setNavigationOnClickListener(v -> finish());

        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.dashboardOverviewWebView.reload();
            binding.swipeRefresh.setRefreshing(false);
        });

        binding.btnRetry.setOnClickListener(v -> {
            binding.errorLayout.setVisibility(View.GONE);
            binding.loadingLayout.setVisibility(View.VISIBLE);
            binding.dashboardOverviewWebView.reload();
        });

        setupWebView();
    }

    private void setupWebView() {
        WebView webView = binding.dashboardOverviewWebView;
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setSupportMultipleWindows(true);
        s.setJavaScriptCanOpenWindowsAutomatically(true);

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
                binding.loadingLayout.setVisibility(View.VISIBLE);
                binding.errorLayout.setVisibility(View.GONE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                binding.loadingLayout.setVisibility(View.GONE);
                view.clearHistory();
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
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

    @Override
    public void onBackPressed() {
        if (binding.dashboardOverviewWebView.canGoBack()) {
            binding.dashboardOverviewWebView.goBack();
        } else {
            finish();
        }
    }
}
