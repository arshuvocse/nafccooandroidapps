package com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.Approval.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saidur.creatrix.unicorn.salessolution.UI.DoctorDashBoard.DCP.Model.ModelDCPItem;

import java.util.List;

public class ModelDCPApproveList {
    @SerializedName("DCPMasterId")
    @Expose
    private String DCPMasterId;

    @SerializedName("DCPDate")
    @Expose
    private String DCPDate;

    @SerializedName("IsFinalSubmit")
    @Expose
    private String IsFinalSubmit;

    @SerializedName("FinalSubmitRemarks")
    @Expose
    private String FinalSubmitRemarks;

    @SerializedName("EntryBy")
    @Expose
    private String EntryBy;

    @SerializedName("ApprovalStatus")
    @Expose
    private String ApprovalStatus;

    @SerializedName("DCPListDtl")
    @Expose
    private List<ModelDCPItem> DCPListDtl;

    public ModelDCPApproveList() {
    }

    public String getDCPMasterId() {
        return DCPMasterId;
    }

    public void setDCPMasterId(String DCPMasterId) {
        this.DCPMasterId = DCPMasterId;
    }

    public String getDCPDate() {
        return DCPDate;
    }

    public void setDCPDate(String DCPDate) {
        this.DCPDate = DCPDate;
    }

    public String getIsFinalSubmit() {
        return IsFinalSubmit;
    }

    public void setIsFinalSubmit(String isFinalSubmit) {
        IsFinalSubmit = isFinalSubmit;
    }

    public String getFinalSubmitRemarks() {
        return FinalSubmitRemarks;
    }

    public void setFinalSubmitRemarks(String finalSubmitRemarks) {
        FinalSubmitRemarks = finalSubmitRemarks;
    }

    public String getEntryBy() {
        return EntryBy;
    }

    public void setEntryBy(String entryBy) {
        EntryBy = entryBy;
    }

    public List<ModelDCPItem> getDCPListDtl() {
        return DCPListDtl;
    }

    public void setDCPListDtl(List<ModelDCPItem> DCPListDtl) {
        this.DCPListDtl = DCPListDtl;
    }

    public String getApprovalStatus() {
        return ApprovalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        ApprovalStatus = approvalStatus;
    }
}
