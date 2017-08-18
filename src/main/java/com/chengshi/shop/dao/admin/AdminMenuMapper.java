package com.chengshi.shop.dao.admin;

import com.chengshi.shop.model.admin.AdminMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMenuMapper {
    int deleteByPrimaryKey(Short menuId);

    int insert(AdminMenu record);

    int insertSelective(AdminMenu record);

    AdminMenu selectByPrimaryKey(Short menuId);

    int updateByPrimaryKeySelective(AdminMenu record);

    int updateByPrimaryKey(AdminMenu record);

    /**
     * 用户拥有的菜单
     * @param userId
     * @return
     */
    List<AdminMenu> getMenuListByUserId(Short userId);
}