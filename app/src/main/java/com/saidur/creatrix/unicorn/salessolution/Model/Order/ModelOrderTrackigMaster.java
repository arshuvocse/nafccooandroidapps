package com.saidur.creatrix.unicorn.salessolution.Model.Order;


import java.util.List;

public class ModelOrderTrackigMaster {
   String TotalNetAmount;
   List<OrderViewModel>Order_Lists;

    public String getTotalNetAmount() {
        return TotalNetAmount;
    }

    public void setTotalNetAmount(String totalNetAmount) {
        TotalNetAmount = totalNetAmount;
    }

    public List<OrderViewModel> getOrder_Lists() {
        return Order_Lists;
    }

    public void setOrder_Lists(List<OrderViewModel> order_Lists) {
        Order_Lists = order_Lists;
    }
}
