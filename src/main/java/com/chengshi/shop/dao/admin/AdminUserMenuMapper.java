package com.chengshi.shop.dao.admin;

import com.chengshi.shop.model.admin.AdminUserMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminUserMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminUserMenu record);

    int insertSelective(AdminUserMenu record);

    AdminUserMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminUserMenu record);

    int updateByPrimaryKey(AdminUserMenu record);

    /**
     * 获取用户拥有菜单id的集合
     * @param userId
     * @return
     */
    List<Short> getMenuIdsByUserId(Short userId);

    /**
     * 删除对应的权限
     * @param userId
     * @param menuId
     */
    void deleteUserMenu(@Param("userId") Short userId,@Param("menuId") Short menuId);
}