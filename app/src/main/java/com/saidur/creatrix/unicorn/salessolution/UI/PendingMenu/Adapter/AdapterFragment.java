package com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.Fragment.DcrLocalListFragment;
import com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.Fragment.OrderCommercialListFragment;
import com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.Fragment.PrescriptionListFragment;


public class AdapterFragment extends FragmentStateAdapter {
    Context context;

    public AdapterFragment(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Context context) {
        super(fragmentManager, lifecycle);
        this.context = context;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {

            case 1:
                return new PrescriptionListFragment();
            case 2:
                return new OrderCommercialListFragment();
        }
        return new DcrLocalListFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}