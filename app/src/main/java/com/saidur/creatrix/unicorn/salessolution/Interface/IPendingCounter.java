package com.saidur.creatrix.unicorn.salessolution.Interface;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderDetails;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.OrderMaster;

import java.util.List;

public interface IPendingCounter {
    interface  Presenter{
        void totalDcr();
        void totalPresc();
        void totalOrder();
        void totalOrderMaster();

    }
    interface  View{
   /*     void totalDcr(List<DcrSM> dcrList);
        void totalPresc(List<PrescriptionSM> preList);
        void totalSample(List<OrderDetailSample> soList);*/
        void totalOrder(List<OrderDetails>oList);
        void totalOrderMaster(List<OrderMaster>oList);
    }
}
