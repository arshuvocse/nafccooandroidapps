package com.saidur.creatrix.unicorn.salessolution.Interface;

public interface DeleteListener {
    void deleteItemFromServer(int pos,int id);
    void deleteItem(int pos);
    void editItem(int pos,int id,int rid,int aid,int tid,int stid,int mid,String region,String area,String territory,String subTerritory,String market);

    void editTourPlanInfo(int pos,int id);

}
