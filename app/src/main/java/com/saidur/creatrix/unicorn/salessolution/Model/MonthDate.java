package com.saidur.creatrix.unicorn.salessolution.Model;

import java.util.List;

public class MonthDate {
    public  String DateValue;
    public String MonthName;
    public String DateName;
    public int DateV;
    public int monthV;
    public int yearV;
    public List<TourPlanViewModel> aTpViewList;
    public List<VisitplanModel> visitplanList;

    public TourPlanPostModel tourPlanPostModel;
    public boolean IsFinalSubmit;

    public boolean isFinalSubmit() {
        return IsFinalSubmit;
    }

    public void setFinalSubmit(boolean finalSubmit) {
        IsFinalSubmit = finalSubmit;
    }

    public List<TourPlanViewModel> getaTpViewList() {
        return aTpViewList;
    }

    public void setaTpViewList(List<TourPlanViewModel> aTpViewList) {
        this.aTpViewList = aTpViewList;
    }

    public List<VisitplanModel> getVisitplanList() {
        return visitplanList;
    }

    public void setVisitplanList(List<VisitplanModel> visitplanList) {
        this.visitplanList = visitplanList;
    }

    public int getDateV() {
        return DateV;
    }

    public void setDateV(int dateV) {
        DateV = dateV;
    }

    public int getMonthV() {
        return monthV;
    }

    public int getYearV() {
        return yearV;
    }

    public void setYearV(int yearV) {
        this.yearV = yearV;
    }

    public void setMonthV(int monthV) {
        this.monthV = monthV;
    }

    public String getDateValue() {
        return DateValue;
    }

    public void setDateValue(String dateValue) {
        DateValue = dateValue;
    }

    public String getMonthName() {
        return MonthName;
    }

    public void setMonthName(String monthName) {
        MonthName = monthName;
    }

    public String getDateName() {
        return DateName;
    }

    public void setDateName(String dateName) {
        DateName = dateName;
    }

    public TourPlanPostModel getTourPlanPostModel() {
        return tourPlanPostModel;
    }

    public void setTourPlanPostModel(TourPlanPostModel tourPlanPostModel) {
        this.tourPlanPostModel = tourPlanPostModel;
    }



    @Override
    public String toString() {
        return  DateName;
    }
}
