package com.saidur.creatrix.unicorn.salessolution.UtilsApp;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class NetworkStatusBanner {

    private static final String BANNER_TAG = "network_status_banner";

    public static void show(Activity activity, String message) {
        if (activity == null || activity.isFinishing()) return;
        ViewGroup rootView = activity.findViewById(android.R.id.content);
        if (rootView == null) return;

        TextView banner = rootView.findViewWithTag(BANNER_TAG);
        if (banner == null) {
            banner = new TextView(activity);
            banner.setTag(BANNER_TAG);
            banner.setTextColor(Color.WHITE);
            banner.setBackgroundColor(Color.rgb(211, 47, 47));
            banner.setGravity(Gravity.CENTER);
            int paddingH = (int) (16 * activity.getResources().getDisplayMetrics().density);
            int paddingV = (int) (8 * activity.getResources().getDisplayMetrics().density);
            banner.setPadding(paddingH, paddingV, paddingH, paddingV);
            banner.setTextSize(13);
            banner.setElevation(20f);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.TOP;
            rootView.addView(banner, params);
        }
        banner.setText(message);
        banner.setVisibility(View.VISIBLE);
    }

    public static void hide(Activity activity) {
        if (activity == null || activity.isFinishing()) return;
        ViewGroup rootView = activity.findViewById(android.R.id.content);
        if (rootView == null) return;
        View banner = rootView.findViewWithTag(BANNER_TAG);
        if (banner != null) {
            rootView.removeView(banner);
        }
    }
}
