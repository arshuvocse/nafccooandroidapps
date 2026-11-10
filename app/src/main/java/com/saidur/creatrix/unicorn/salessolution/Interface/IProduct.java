package com.saidur.creatrix.unicorn.salessolution.Interface;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelCompanyUnit;
import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;

import java.util.ArrayList;
import java.util.List;

public interface IProduct {

    interface Presenter{
        void getProductsFromServer(int empId);
        void getCompanyUnitFromServer(int empId);
        void InsertIntoProductTable(List<ModelProduct> aProduct);
        void getProductFromDB(int empId);
        ArrayList<ModelCompanyUnit> getCompanyUnitFromDB();
        void getRegularProducts(int empId);
    }

    interface View{
        void OnError(String message);
        void onProductsGet(List<ModelProduct> aList);
    }
}
