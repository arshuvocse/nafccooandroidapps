package com.saidur.creatrix.unicorn.salessolution.Model.Expense;

import java.util.List;

public class ExpenseClaimMaster {
    String ExpenseClaimID;
    int ExpenseTypeId;
    String ExpenseDate;
    int EmpInfoId;
    double Amount;
    String  Remarks;
    String  ImageBase64String;
    boolean IsFromApp;
    public List<ExpenseTypeDetails> aDetailList;

    public String getExpenseClaimID() {
        return ExpenseClaimID;
    }

    public void setExpenseClaimID(String expenseClaimID) {
        ExpenseClaimID = expenseClaimID;
    }

    public int getExpenseTypeId() {
        return ExpenseTypeId;
    }

    public void setExpenseTypeId(int expenseTypeId) {
        ExpenseTypeId = expenseTypeId;
    }

    public String getExpenseDate() {
        return ExpenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        ExpenseDate = expenseDate;
    }

    public int getEmpInfoId() {
        return EmpInfoId;
    }

    public void setEmpInfoId(int empInfoId) {
        EmpInfoId = empInfoId;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getImageBase64String() {
        return ImageBase64String;
    }

    public void setImageBase64String(String imageBase64String) {
        ImageBase64String = imageBase64String;
    }

    public boolean isFromApp() {
        return IsFromApp;
    }

    public void setFromApp(boolean fromApp) {
        IsFromApp = fromApp;
    }

    public List<ExpenseTypeDetails> getaDetailList() {
        return aDetailList;
    }

    public void setaDetailList(List<ExpenseTypeDetails> aDetailList) {
        this.aDetailList = aDetailList;
    }
}
