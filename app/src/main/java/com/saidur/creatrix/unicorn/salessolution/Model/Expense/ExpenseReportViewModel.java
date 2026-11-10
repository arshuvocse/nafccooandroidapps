package com.saidur.creatrix.unicorn.salessolution.Model.Expense;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpenseReportViewModel {
    @SerializedName("ExpenseClaimID")
    @Expose
    private String expenseClaimID;
    @SerializedName("ExpenseTypeId")
    @Expose
    private String expenseTypeId;
    @SerializedName("ExpDate")
    @Expose
    private String expDate;
    @SerializedName("EmpName")
    @Expose
    private String empName;
    @SerializedName("ExpenseTypeName")
    @Expose
    private String expenseTypeName;
    @SerializedName("ApprovalStatus")
    @Expose
    private String approvalStatus;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("ImageString")
    @Expose
    private String imageString;
    @SerializedName("ImagePreName")
    @Expose
    private String imagePreName;
    @SerializedName("aDetailListDAO")
    @Expose
    private List<ADetailListDAO> aDetailListDAO = null;

    public String getExpenseClaimID() {
        return expenseClaimID;
    }

    public void setExpenseClaimID(String expenseClaimID) {
        this.expenseClaimID = expenseClaimID;
    }

    public String getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(String expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getExpenseTypeName() {
        return expenseTypeName;
    }

    public void setExpenseTypeName(String expenseTypeName) {
        this.expenseTypeName = expenseTypeName;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getImagePreName() {
        return imagePreName;
    }

    public void setImagePreName(String imagePreName) {
        this.imagePreName = imagePreName;
    }

    public List<ADetailListDAO> getaDetailListDAO() {
        return aDetailListDAO;
    }

    public void setaDetailListDAO(List<ADetailListDAO> aDetailListDAO) {
        this.aDetailListDAO = aDetailListDAO;
    }

   /* @SerializedName("ExpenseClaimID")
    @Expose
    private String expenseClaimID;

    @SerializedName("ExpenseTypeId")
    @Expose
    private String expenseTypeId;

    @SerializedName("ExpDate")
    @Expose
    private String expDate;
    @SerializedName("EmpName")
    @Expose
    private String empName;
    @SerializedName("ExpenseTypeName")
    @Expose
    private String expenseTypeName;
    @SerializedName("ApprovalStatus")
    @Expose
    private String approvalStatus;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("ImageString")
    @Expose
    private String imageString;
    @SerializedName("ImagePreName")
    @Expose
    private String imagePreName;
    @SerializedName("aDetailListDAO")
    @Expose
    private List<ADetailListDAO> aDetailListDAO = null;
    public String getExpenseClaimID() {
        return expenseClaimID;
    }

    public void setExpenseClaimID(String expenseClaimID) {
        this.expenseClaimID = expenseClaimID;
    }

    public String getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(String expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getExpenseTypeName() {
        return expenseTypeName;
    }

    public void setExpenseTypeName(String expenseTypeName) {
        this.expenseTypeName = expenseTypeName;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getImagePreName() {
        return imagePreName;
    }

    public void setImagePreName(String imagePreName) {
        this.imagePreName = imagePreName;
    }

    public List<ADetailListDAO> getaDetailListDAO() {
        return aDetailListDAO;
    }

    public void setaDetailListDAO(List<ADetailListDAO> aDetailListDAO) {
        this.aDetailListDAO = aDetailListDAO;

    }
*/
}
