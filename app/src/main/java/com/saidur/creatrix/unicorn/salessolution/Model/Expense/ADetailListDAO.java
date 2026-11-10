package com.saidur.creatrix.unicorn.salessolution.Model.Expense;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ADetailListDAO {

    @SerializedName("ExpenseTypDetailsId")
    @Expose
    private int ExpenseTypDetailsId;

    @SerializedName("ValueText")
    @Expose
    private String valueText;
    @SerializedName("FieldName")
    @Expose
    private String fieldName;

    public ADetailListDAO(String valueText, String fieldName) {
        this.valueText = valueText;
        this.fieldName = fieldName;
    }

    public ADetailListDAO() {
    }

    public int getExpenseTypDetailsId() {
        return ExpenseTypDetailsId;
    }

    public void setExpenseTypDetailsId(int expenseTypDetailsId) {
        ExpenseTypDetailsId = expenseTypDetailsId;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
