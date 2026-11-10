package com.saidur.creatrix.unicorn.salessolution.Model;

public class ModelDSR {
    int TrId;
    String Territory;
    String OrderPlan;
    String ActualOrder;
    String Deviation;
    String Remarks;

    public ModelDSR() {
    }

    public int getTrId() {
        return TrId;
    }

    public void setTrId(int trId) {
        TrId = trId;
    }

    public String getTerritory() {
        return Territory;
    }

    public void setTerritory(String territory) {
        Territory = territory;
    }

    public String getOrderPlan() {
        return OrderPlan;
    }

    public void setOrderPlan(String orderPlan) {
        OrderPlan = orderPlan;
    }

    public String getActualOrder() {
        return ActualOrder;
    }

    public void setActualOrder(String actualOrder) {
        ActualOrder = actualOrder;
    }

    public String getDeviation() {
        return Deviation;
    }

    public void setDeviation(String deviation) {
        Deviation = deviation;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }
}
