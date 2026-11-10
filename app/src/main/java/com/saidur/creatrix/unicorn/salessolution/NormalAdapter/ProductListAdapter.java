package com.saidur.creatrix.unicorn.salessolution.NormalAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.ArrayList;

public class ProductListAdapter extends ArrayAdapter<ModelProduct> {

private static final String TAG = "ProductListAdapter";

private Context mContext;
private int mResource;
private int lastPosition = -1;

/**
 * Holds variables in a View
 */
private static class ViewHolder {
    TextView productName;
    TextView unitPrice;
    EditText Quantity;
    TextView tp;
    TextView price;

}
    public ProductListAdapter(Context context, int resource, ArrayList<ModelProduct> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int productId = getItem(position).getProductId();
        String productName = getItem(position).getProductName();
        String productCode = getItem(position).getProductCode();
        double unitPrice = getItem(position).getUnitPrice();
        int quantity = getItem(position).getQuantity();
        double vatAmountPerUnit = getItem(position).getVatAmountPerunit();

        Toast.makeText(mContext, "Adap pricec "+String.valueOf(vatAmountPerUnit), Toast.LENGTH_SHORT).show();

        //Create the person object with the information
        ModelProduct product = new ModelProduct(productId,productName,unitPrice,vatAmountPerUnit,quantity,productCode);
        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();


            holder.productName = (TextView) convertView.findViewById(R.id.itemNameTxt);
            holder.unitPrice = (TextView) convertView.findViewById(R.id.unitPriceTxt);
            holder.Quantity = (EditText) convertView.findViewById(R.id.quantityTxt);
            holder.tp = (TextView) convertView.findViewById(R.id.totalPriceTxt);
            holder.price = (TextView) convertView.findViewById(R.id.priceTxt);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        lastPosition = position;

        holder.productName.setText(product.getProductName());
        holder.unitPrice.setText(Double.toString(product.getUnitPrice()));
        holder.Quantity.setText(product.getQuantity());
        holder.tp.setText(0);
        holder.price.setText(0);


        return convertView;
    }
}
