package com.saidur.creatrix.unicorn.salessolution.Presentar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.saidur.creatrix.unicorn.salessolution.Database.DBCrudHelper;
import com.saidur.creatrix.unicorn.salessolution.Database.DBMain;
import com.saidur.creatrix.unicorn.salessolution.Interface.IPendingCounter;


public class PresenterPendingCounter implements IPendingCounter.Presenter {
    IPendingCounter.View view;
    Context context;
    Activity activity;
    ProgressDialog progressDoalog;
    DBMain dbHelper;
    DBCrudHelper crudHelper;
    public PresenterPendingCounter(Activity activity) {
        this.activity = activity;
        this.dbHelper = new DBMain(context);
        this.crudHelper = new DBCrudHelper(context);
    }
    public PresenterPendingCounter(IPendingCounter.View view, Context context) {
        this.view = view;
        this.context = context;
        this.dbHelper = new DBMain(context);
        this.crudHelper = new DBCrudHelper(context);
    }

    @Override
    public void totalDcr() {
      /*  try {
            view.totalDcr(crudHelper.GetDcrInfoFromDB());
            dbHelper.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }*/

    }

    @Override
    public void totalPresc() {
/*         view.totalPresc(crudHelper.GetPrescriptionInfoFromDB());*/
    }


    @Override
    public void totalOrder() {

    }

    @Override
    public void totalOrderMaster() {
        view.totalOrderMaster(crudHelper.getOrderListFromSQLite());
        dbHelper.close();
    }
}
