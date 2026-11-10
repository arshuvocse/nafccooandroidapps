package com.saidur.creatrix.unicorn.salessolution.Interface;

import com.saidur.creatrix.unicorn.salessolution.Model.Order.CordinateUpdate;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMaster;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMasterModel;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMasterNew;

public interface IOrderManagement {
    interface  Presenter{
        void makeOrder(OrderMaster aOrder);
        void draftOrder(OrderMaster aOrder);
        void SyncOrder(OrderMaster aOrder,int localOrderId);
        void UpdateCustomerLocation(CordinateUpdate location);
        void makeOrder2(OrderMasterModel aOrder, String Who);
        void draftOrder2(OrderMasterNew aOrder);

    }
    interface  View{
        void onOrderSuccess(String message,String Who);
        void onOrderDraftSuccess(String message);
        void onOrderError(String message);
        void onGenericSuccess(String message);
        void onGenericError(String message);
    }
}
