package com.saidur.creatrix.unicorn.salessolution.Model.Doctor;

public class DoctorCategory {
    int pk;
    int CategoryId;
    String CategoryName;


    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public DoctorCategory() {
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    @Override
    public String toString() {
        return CategoryName;
    }
}
