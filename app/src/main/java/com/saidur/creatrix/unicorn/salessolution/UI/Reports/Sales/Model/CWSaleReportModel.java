package com.saidur.creatrix.unicorn.salessolution.UI.Reports.Sales.Model;

import java.util.List;

public class CWSaleReportModel {
    String total_qty;
    String total_amount;
    List<CWSData> cwsDataList;

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

    public List<CWSData> getCwsDataList() {
        return cwsDataList;
    }

    public void setCwsDataList(List<CWSData> cwsDataList) {
        this.cwsDataList = cwsDataList;
    }
}
