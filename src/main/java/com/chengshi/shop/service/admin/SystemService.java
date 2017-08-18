package com.chengshi.shop.service.admin;

import com.chengshi.shop.model.admin.AdminMenu;
import com.chengshi.shop.model.admin.AdminUser;

import java.util.List;

/**
 * 后台系统相关接口
 * @author xuxinlong
 * @version 2017年08月15日
 */
public interface SystemService {

    /**
     * 获取管理后台用户列表
     * @return
     */
    List<AdminUser> getUserList();

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    AdminUser findAdminUser(Short userId);

    /**
     * 保存用户信息
     * @param adminUser
     */
    void saveUser(AdminUser adminUser);

    /**
     * 删除用户
     * @param userId
     */
    void deleteUser(Short userId);

    /**
     * 根据用户查询用户
     * @param userName
     * @return
     */
    AdminUser findByUsername(String userName);

    /**
     * 用户拥有的菜单
     * @param userId
     * @return
     */
    List<AdminMenu> getMenuList(Short userId);
}
