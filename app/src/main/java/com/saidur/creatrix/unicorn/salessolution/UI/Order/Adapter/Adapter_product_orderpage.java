package com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.R;
import com.saidur.creatrix.unicorn.salessolution.UI.Order.PlaceOrderActivity;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter_product_orderpage extends RecyclerView.Adapter<Adapter_product_orderpage.BookViewHolder> {
    private Context context;
    private final static int FADE_DURATION = 500; //FADE_DURATION in milliseconds
    private List<ModelProduct> aProductList;
    PlaceOrderActivity orderMainActivity;
    TextView totalTP;
    TextView totalVAT;
    TextView total;

    DecimalFormat decimalFormat = new DecimalFormat("##.00");
    private RecyclerViewActionListener mListener;
    String who;

    public Adapter_product_orderpage(List<ModelProduct> aProductList, PlaceOrderActivity orderMainActivity, RecyclerViewActionListener mListener, String mainOrder) {
        this.context = context;
        this.aProductList = aProductList;
        this.orderMainActivity = orderMainActivity;
        this.mListener = mListener;
        this.who = mainOrder;
    }



    @Override
    public Adapter_product_orderpage.BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_product_order_page, parent, false);
        context = parent.getContext();
        return new Adapter_product_orderpage.BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_product_orderpage.BookViewHolder holder, int position) {
        if(aProductList!=null)
        {
            holder.tp.setVisibility(View.VISIBLE);
            holder.price.setVisibility(View.VISIBLE);
            holder.unitPrice.setVisibility(View.VISIBLE);
            holder.productName.setText(aProductList.get(position).getProductName());
            holder.unitPrice.setText(Double.toString(aProductList.get(position).getUnitPrice()));
            holder.quantity.setText(String.valueOf(aProductList.get(position).getQuantity()));
            holder.tp.setText(Double.toString(Double.parseDouble(decimalFormat.format(aProductList.get(position).getTp()))));
            // holder.tp.setText(String.valueOf(aProductList.get(position).getTp()));
            holder.price.setText(Double.toString(Double.parseDouble(decimalFormat.format(aProductList.get(position).getPrice()))));
            //holder.price.setText(String.valueOf(aProductList.get(position).getPrice()));
        }else {
            Toast.makeText(orderMainActivity, "No Data", Toast.LENGTH_SHORT).show();
        }




    }

    @Override
    public int getItemCount() {
        if(aProductList!=null)
        {
            return aProductList.size();
            //return aProductList.size();
        }
        return 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public TextView productId;
        public TextView unitPrice;
        public EditText quantity;
        public TextView tp;
        public TextView price;

        public LinearLayout clickID;

        public BookViewHolder(View view) {
            super(view);

            productName = (TextView) view.findViewById(R.id.itemNameTxt);
            unitPrice = (TextView) view.findViewById(R.id.unitPriceTxt);
            quantity = (EditText) view.findViewById(R.id.quantityTxt);
            tp = (TextView) view.findViewById(R.id.totalPriceTxt);
            price = (TextView) view.findViewById(R.id.priceTxt);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                   mListener.onLongClick(getAdapterPosition());
                   return true;
                }
            });

            quantity.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                public void afterTextChanged(Editable editable) {
                    boolean id = quantity.hasFocus();
                    if (id==true){
                        int postion =getAdapterPosition();
                            int values = 0;
                            if(!String.valueOf(editable).isEmpty()){
                                mListener.onEditTextFocusChange(postion,Integer.parseInt(String.valueOf(editable)));
                                int textLength = quantity.getText().length();
                               quantity.setSelection(textLength, textLength);
                            }else{
                                mListener.onEditTextFocusChange(postion,0);
                            }
                    }

                }
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }
            });

        }

    }



}




