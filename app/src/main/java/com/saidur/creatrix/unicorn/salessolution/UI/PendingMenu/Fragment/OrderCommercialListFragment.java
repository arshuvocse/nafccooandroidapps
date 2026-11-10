package com.saidur.creatrix.unicorn.salessolution.UI.PendingMenu.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Interface.DeleteListeners;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMaster;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter.AdapterDraftOrder;
import com.saidur.creatrix.unicorn.salessolution.databinding.FragmentOrderCommercialListBinding;

import java.util.List;


public class OrderCommercialListFragment extends Fragment implements DeleteListeners {

    FragmentOrderCommercialListBinding binding;
    AdapterDraftOrder draftOrderAdapter;
    DBCrudHelper dbCrudHelper;

    public OrderCommercialListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderCommercialListBinding.inflate(getLayoutInflater());
        // return inflater.inflate(R.layout.fragment_order_commercial_list, container, false);
        dbCrudHelper=new DBCrudHelper(getActivity());

        LoadRecyclerView(dbCrudHelper.getOrderListFromSQLite(),"Local");
        return binding.getRoot();
    }
    public void LoadRecyclerView(List<OrderMaster> aList, String type) {


        //  Toast.makeText(getActivity(), ""+, Toast.LENGTH_SHORT).show();
        if (aList != null) {
            draftOrderAdapter=new AdapterDraftOrder(aList,this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            binding.rvCommercial.setLayoutManager(mLayoutManager);
            binding.rvCommercial.setItemAnimator(new DefaultItemAnimator());
            binding.rvCommercial.setAdapter(draftOrderAdapter);
            binding.rvCommercial.setItemAnimator(null);
            binding.rvCommercial.scrollToPosition(0);
            draftOrderAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public boolean onLongClick(int position,int localID) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Are you sure wants to delete the Item ?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(

                "Delete",
                (dialog, id) -> {
                    try {
                        draftOrderAdapter.notifyItemRemoved(position);
                        draftOrderAdapter.notifyDataSetChanged();
                        //Todo:When Delete any product
                        dbCrudHelper.DeleteOldOrder_OrderTable_SQLite(localID);

                        dialog.cancel();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });
        builder1.setNegativeButton(
                "Cancel",
                (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();

        return true;

    }
}