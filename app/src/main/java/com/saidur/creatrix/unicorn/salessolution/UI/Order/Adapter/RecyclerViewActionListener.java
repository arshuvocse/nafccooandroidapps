package com.saidur.creatrix.unicorn.salessolution.UI.Order.Adapter;


import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMaster;

public interface RecyclerViewActionListener {
    void onEditTextFocusChange(int position,int value);
    boolean onLongClick(int position);
    void OrderSync(OrderMaster orderMaster);
}

