package com.saidur.creatrix.unicorn.salessolution.webview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.ModelSession;
import com.saidur.creatrix.unicorn.salessolution.databinding.FragmentDashboardOverviewBinding;

public class DashboardOverviewFragment extends Fragment {

    private FragmentDashboardOverviewBinding binding;
    private String url;

    public DashboardOverviewFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardOverviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DBCrudHelper dbCrudHelper = new DBCrudHelper(requireContext());
        ModelSession session = dbCrudHelper.getSession();
        String empId = session != null ? String.valueOf(session.getEmpInfoId()) : "";
        url = "http://103.244.247.93:187/DoctorModule_UI/DasboardOverview.aspx?EmpId=" + empId;

        binding.swipeRefresh.setOnRefreshListener(() -> {
            if (binding != null) {
                binding.dashboardOverviewWebView.reload();
                binding.swipeRefresh.setRefreshing(false);
            }
        });

        binding.btnRetry.setOnClickListener(v -> {
            if (binding != null) {
                binding.errorLayout.setVisibility(View.GONE);
                binding.loadingLayout.setVisibility(View.VISIBLE);
                binding.dashboardOverviewWebView.reload();
            }
        });

        setupWebView();
    }

    private void setupWebView() {
        if (binding == null) return;
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
                if (binding != null) {
                    binding.loadingLayout.setVisibility(View.VISIBLE);
                    binding.errorLayout.setVisibility(View.GONE);
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (binding != null) {
                    binding.loadingLayout.setVisibility(View.GONE);
                }
                view.clearHistory();
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (binding != null) {
                    binding.loadingLayout.setVisibility(View.GONE);
                    binding.errorLayout.setVisibility(View.VISIBLE);
                    binding.errorText.setText("Can't load page.\n" + description);
                }
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

    public boolean canGoBack() {
        return binding != null && binding.dashboardOverviewWebView != null && binding.dashboardOverviewWebView.canGoBack();
    }

    public void goBack() {
        if (binding != null && binding.dashboardOverviewWebView != null) {
            binding.dashboardOverviewWebView.goBack();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
