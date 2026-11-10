package com.saidur.creatrix.unicorn.salessolution.UI.CategoryRequest;

import java.io.Serializable;

/** Gson models for the Customer Category Request API (field names match the SP columns). */
public final class CatReqModels {
    private CatReqModels() {}

    public static class Summary {
        public int CustomerTypeId;
        public String CustomerType;
        public Integer RequestLimit; // 0 = no limit
        public int UsedCount;

        public boolean isFull() {
            return RequestLimit != null && RequestLimit > 0 && UsedCount >= RequestLimit;
        }

        @Override
        public String toString() {
            return CustomerType;
        }
    }

    public static class Customer implements Serializable {
        public int CustomerMasterId;
        public String CustomerCode, CustomerName, Address, CellNo, Market, CustomerType, PendingType;
        public Integer CustomerTypeId;
    }

    public static class Approval {
        public int RequestId;
        public String CustomerCode, CustomerName, Address, CellNo, Market, FromType, ToType,
                RequestBy, RequestDate, Status, ShopPhotoUrl, NIDUrl, TradeLicenseUrl, DrugLicenseUrl;
    }

    public static class SaveBody {
        public int EmpId, CustomerMasterId, CustomerTypeId;
        public String ShopPhoto, NID, TradeLicense, DrugLicense;
    }

    public static class ApproveBody {
        public int RequestId, EmpId;
        public String Status, Comments;
    }

    public static class ApproveResult {
        public int Success;
        public String Msg;
    }
}
