package com.chengshi.shop.model.admin;

public class AdminUserMenu {
    private Integer id;

    private Short userId;

    private Short menuId;

    public AdminUserMenu(Short userId, Short menuId) {
        this.userId = userId;
        this.menuId = menuId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getUserId() {
        return userId;
    }

    public void setUserId(Short userId) {
        this.userId = userId;
    }

    public Short getMenuId() {
        return menuId;
    }

    public void setMenuId(Short menuId) {
        this.menuId = menuId;
    }
}