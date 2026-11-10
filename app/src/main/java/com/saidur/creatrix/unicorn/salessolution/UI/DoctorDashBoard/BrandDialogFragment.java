package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saidur.creatrix.unicorn.salessolution.Adapters.AdapterBrands;
import com.saidur.creatrix.unicorn.salessolution.Database.DBDoctorHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBProductSQLiteHelper;
import com.saidur.creatrix.unicorn.salessolution.Model.Doctor.ModelBrand;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.databinding.ComponentBrandBinding;

import java.util.ArrayList;
import java.util.List;

public class BrandDialogFragment extends DialogFragment {
    ComponentBrandBinding binding;
    DBDoctorHelper dbDoctorHelper;
    DBProductSQLiteHelper dbProduct;
    private callbackBrand callback;
    List<ModelProduct> productList=new ArrayList<>();
    AdapterBrands adapter;
    public static BrandDialogFragment newInstance() {
        return new BrandDialogFragment();
    }
    public void setCallback(callbackBrand callback) {
        this.callback = callback;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogCategoryTheme);
        //setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ComponentBrandBinding.inflate(inflater, container, false);
        if (savedInstanceState == null) {

        }
        dbDoctorHelper = new DBDoctorHelper(getActivity());
        dbProduct = new DBProductSQLiteHelper(getActivity());
        try {
            List<ModelBrand> brandList = dbDoctorHelper.getBrandListFromSQLite();
            List<ModelProduct> productList = dbProduct.getProductFromDB();
            if(productList!=null)
            {
                setBrands(productList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.btnCancel.setOnClickListener(view -> requireDialog().onBackPressed());
        binding.searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        return binding.getRoot();
    }

    private void setBrands(List<ModelProduct> brandList) {
        productList=brandList;
        adapter = new AdapterBrands(getActivity(),brandList);
        binding.rvBrandItem.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false));
        binding.rvBrandItem.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //adapter.setBrand(brandList);
        binding.btnConfirm.setOnClickListener(view -> {
            if(adapter.getSelected().size()>0)
            {
                StringBuilder sb=new StringBuilder();
                List<ModelProduct> blists=new ArrayList<>();
                for(int i=0;i<adapter.getSelected().size();i++)
                {
                    ModelProduct brand=new ModelProduct();
                    sb.append(adapter.getSelected().get(i).getProductName());
                    sb.append("\n");
                    brand.setProductId(adapter.getSelected().get(i).getProductId());
                    blists.add(brand);
                }
              callback.onActionClickValue(sb.toString().trim());
              callback.onActionClick(blists);
            }
            dismiss();
        });

    }
    public interface callbackBrand {
        void onActionClick(List<ModelProduct> blist);
        void onActionClickValue(String value);
    }
    private void filter(String productName) {
        try {
            if (productList != null) {

                ArrayList<ModelProduct> arrayList = new ArrayList<>();
                for (ModelProduct item : productList) {
                    if ((item.getProductName().toLowerCase().contains(productName.toLowerCase()))) {
                        arrayList.add(item);
                    }
                }

                adapter.filterList(arrayList);

            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
