package com.saidur.creatrix.unicorn.salessolution.Model.Expense;

public class ExpenseTypeMaster {

    int ExpenseTypeId;
    String ExpenseTypeName;
    boolean ImageRequired;

    public int getExpenseTypeId() {
        return ExpenseTypeId;
    }

    public void setExpenseTypeId(int expenseTypeId) {
        ExpenseTypeId = expenseTypeId;
    }

    public String getExpenseTypeName() {
        return ExpenseTypeName;
    }

    public void setExpenseTypeName(String expenseTypeName) {
        ExpenseTypeName = expenseTypeName;
    }

    public boolean isImageRequired() {
        return ImageRequired;
    }

    public void setImageRequired(boolean imageRequired) {
        ImageRequired = imageRequired;
    }

    @Override
    public String toString() {
        return ExpenseTypeName;
    }
}
