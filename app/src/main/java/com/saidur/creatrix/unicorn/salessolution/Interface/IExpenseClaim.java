package com.saidur.creatrix.unicorn.salessolution.Interface;

import com.saidur.creatrix.unicorn.salessolution.Model.Expense.ExpenseClaimMaster;
import com.saidur.creatrix.unicorn.salessolution.Model.Expense.ExpenseTypeDetails;
import com.saidur.creatrix.unicorn.salessolution.Model.Expense.ExpenseTypeMaster;

import java.util.List;

public interface IExpenseClaim {
    interface  Presenter{
        void GetExpenseType();
        void GetExpenseDetails(int id);
        void SaveExpenseClaim(ExpenseClaimMaster aInfo);


    }
    interface View{
        void onExpenseTypeGet(List<ExpenseTypeMaster> aList);
        void onExpenseTypeDetailsGet(List<ExpenseTypeDetails> aList);
        void onSaveSuccess(String message);
        void onSaveError(String message);
    }
}
