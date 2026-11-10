package com.saidur.creatrix.unicorn.salessolution.Model.Doctor;

import com.saidur.creatrix.unicorn.salessolution.Model.Order.ModelProduct;

import java.util.List;

public class ModelRx {
    String submitDate;
    String rxDate;
    String rxImg;
    String rxDoctor;
    List<ModelProductId> rxBrand;
    String rxOtherDoctor;
    String submitby;

    public ModelRx() {
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getRxDate() {
        return rxDate;
    }

    public void setRxDate(String rxDate) {
        this.rxDate = rxDate;
    }

    public String getRxImg() {
        return rxImg;
    }

    public void setRxImg(String rxImg) {
        this.rxImg = rxImg;
    }

    public String getRxDoctor() {
        return rxDoctor;
    }

    public void setRxDoctor(String rxDoctor) {
        this.rxDoctor = rxDoctor;
    }

    public List<ModelProductId> getRxBrand() {
        return rxBrand;
    }

    public void setRxBrand(List<ModelProductId> rxBrand) {
        this.rxBrand = rxBrand;
    }

    public String getRxOtherDoctor() {
        return rxOtherDoctor;
    }

    public void setRxOtherDoctor(String rxOtherDoctor) {
        this.rxOtherDoctor = rxOtherDoctor;
    }

    public String getSubmitby() {
        return submitby;
    }

    public void setSubmitby(String submitby) {
        this.submitby = submitby;
    }
}
