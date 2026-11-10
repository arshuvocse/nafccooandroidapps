package com.saidur.creatrix.unicorn.salessolution.Model;

public class ModelReportsMenu {
    int id;
    int icon;
    String menuName;

    public ModelReportsMenu(int id, int icon, String menuName) {
        this.id = id;
        this.icon = icon;
        this.menuName = menuName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
