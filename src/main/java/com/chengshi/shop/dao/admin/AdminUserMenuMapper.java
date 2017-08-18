package com.chengshi.shop.dao.admin;

import com.chengshi.shop.model.admin.AdminUserMenu;

public interface AdminUserMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminUserMenu record);

    int insertSelective(AdminUserMenu record);

    AdminUserMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminUserMenu record);

    int updateByPrimaryKey(AdminUserMenu record);
}