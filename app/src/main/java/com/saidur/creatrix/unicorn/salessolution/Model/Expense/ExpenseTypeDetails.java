package com.saidur.creatrix.unicorn.salessolution.Model.Expense;

public class ExpenseTypeDetails {
    int ExpenseTypDetailsId;
    String FieldName;
    boolean IsRequied;
    String ValueText;

    public String getValueText() {
        return ValueText;
    }

    public void setValueText(String valueText) {
        ValueText = valueText;
    }

    public int getExpenseTypDetailsId() {
        return ExpenseTypDetailsId;
    }

    public void setExpenseTypDetailsId(int expenseTypDetailsId) {
        ExpenseTypDetailsId = expenseTypDetailsId;
    }

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String fieldName) {
        FieldName = fieldName;
    }

    public boolean isRequied() {
        return IsRequied;
    }

    public void setRequied(boolean requied) {
        IsRequied = requied;
    }

    @Override
    public String toString() {
        return "ExpenseTypeDetails{" +
                "FieldName='" + FieldName + '\'' +
                ", ValueText='" + ValueText + '\'' +
                '}';
    }
}
