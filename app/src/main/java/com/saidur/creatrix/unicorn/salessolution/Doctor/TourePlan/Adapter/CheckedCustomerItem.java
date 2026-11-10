package com.saidur.creatrix.unicorn.salessolution.Doctor.TourePlan.Adapter;


import com.saidur.creatrix.unicorn.salessolution.Model.ModelCustomer;

import java.util.List;

public interface CheckedCustomerItem {
    void ckdItemName(List<ModelCustomer> st, int Pos);
    void unckdItemName(List<ModelCustomer> st, int Pos);
}
