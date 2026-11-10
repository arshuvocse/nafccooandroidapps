package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.Model;

import java.util.List;

public class PWSaleReportModel {
    String total_qty;
    String total_amount;
    String total_ot;
    List<PWSData> pwsDataList;

    public String getTotal_qty() {
        return total_qty;
    }

    public void setTotal_qty(String total_qty) {
        this.total_qty = total_qty;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTotal_ot() {
        return total_ot;
    }

    public void setTotal_ot(String total_ot) {
        this.total_ot = total_ot;
    }

    public List<PWSData> getPwsDataList() {
        return pwsDataList;
    }

    public void setPwsDataList(List<PWSData> pwsDataList) {
        this.pwsDataList = pwsDataList;
    }
}
