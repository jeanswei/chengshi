package com.chengshi.shop.dao.admin;

import com.chengshi.shop.model.admin.AdminMenu;
import org.apache.ibatis.annotations.Param;
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
     * @param pid
     * @return
     */
    List<AdminMenu> getMenuListByUserId(@Param("userId") Short userId,@Param("pid") Short pid);

    /**
     * 所有菜单
     * @return
     */
    List<AdminMenu> selectAllMenu();
}