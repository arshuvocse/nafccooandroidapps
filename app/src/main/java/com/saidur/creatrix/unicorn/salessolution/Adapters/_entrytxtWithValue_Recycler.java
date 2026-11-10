package com.saidur.creatrix.unicorn.salessolution.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.saidur.creatrix.unicorn.salessolution.Model.Expense.ExpenseTypeDetails;
import com.saidur.creatrix.unicorn.salessolution.R;

import java.util.List;

public class _entrytxtWithValue_Recycler extends RecyclerView.Adapter<_entrytxtWithValue_Recycler.BookViewHolder> {
    private Context context;
    private List<ExpenseTypeDetails> aInfo;
    private RecyclerviewExpenseClaimListner mListener;

    public _entrytxtWithValue_Recycler(List<ExpenseTypeDetails> dataInfo, RecyclerviewExpenseClaimListner mListener) {
        this.aInfo = dataInfo;
        this.mListener = mListener;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.zrv_entrytxt_withvalue, parent, false);
        context = parent.getContext();
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        if(aInfo.get(position).isRequied()){

            holder.hntTxt.setHint(aInfo.get(position).getFieldName()+"*");
            holder.rcvTxt.setText(aInfo.get(position).getValueText());

          /*  if(aInfo.get(position).getValueText()==null)
            {
                Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show();
            }else {

            }*/

        }else{
            holder.hntTxt.setHint(aInfo.get(position).getFieldName());
            holder.rcvTxt.setText(aInfo.get(position).getValueText());

        }
//        holder.hntTxt.setHint(Html.fromHtml(aInfo.get(position).getFieldName()));
    }

    @Override
    public int getItemCount() {
        return aInfo.size();
    }


    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextInputLayout hntTxt;
        public EditText rcvTxt;
        public BookViewHolder(View view) {
            super(view);
            hntTxt = view.findViewById(R.id.hntTxt);
            rcvTxt = view.findViewById(R.id.rcvTxt);
            rcvTxt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int postion =getAdapterPosition();
                    if(!String.valueOf(editable).isEmpty()){
                        mListener.onEditTextFocusChange(postion,String.valueOf(editable));
//                        int textLength = quantity.getText().length();
//                        quantity.setSelection(textLength, textLength);
                    }else{
                        mListener.onEditTextFocusChange(postion,"");
                    }

                }
            });



        }
    }
    public interface RecyclerviewExpenseClaimListner {
        void onEditTextFocusChange(int position,String  value);
    }
}