package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Product.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.BookViewHolder> {
    private Context context;
    private final static int FADE_DURATION = 700; //FADE_DURATION in milliseconds
    private List<ModelProduct> aList;


    public AdapterProducts(List<ModelProduct> aList) {
        this.aList = aList;
    }

    @Override
    public AdapterProducts.BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_productview, parent, false);
        context = parent.getContext();
        return new AdapterProducts.BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterProducts.BookViewHolder holder, int position) {


        holder.nameTxt.setText(aList.get(position).getProductName());
        holder.codeTxt.setText(aList.get(position).getProductCode());
        if(aList.get(position).getUnitPrice()!=null){
            holder.priceTxt.setText(aList.get(position).getUnitPrice().toString());

        }
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.slide_intop);
        animation.setStartOffset(30 * position);//Provide delay here
        holder.itemView.startAnimation(animation);
    }

    public void filterList(List<ModelProduct> aList){
        this.aList = aList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTxt;
        public TextView codeTxt;
        public TextView priceTxt;
        public TextView vatPercenTxt;

        public BookViewHolder(View view) {
            super(view);
            nameTxt = view.findViewById(R.id.nameTxt);
            codeTxt = view.findViewById(R.id.codeTxt);
            priceTxt = view.findViewById(R.id.priceTxt);
//            vatPercenTxt = (TextView) view.findViewById(R.id.vatPercenTxt);
        }
    }


    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


}
