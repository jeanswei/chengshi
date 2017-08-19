package com.chengshi.shop.service.admin.impl;

import com.chengshi.shop.dao.admin.AdminMenuMapper;
import com.chengshi.shop.dao.admin.AdminUserMapper;
import com.chengshi.shop.model.admin.AdminMenu;
import com.chengshi.shop.model.admin.AdminUser;
import com.chengshi.shop.service.admin.SystemService;
import com.chengshi.shop.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 后台系统相关接口
 *
 * @author xuxinlong
 * @version 2017年08月15日
 */
@Service
public class SystemServiceImpl implements SystemService {
    @Resource
    private AdminMenuMapper adminMenuMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    /**
     * 获取管理后台用户列表
     *
     * @return
     */
    @Override
    public List<AdminUser> getUserList() {
        return adminUserMapper.getUserList();
    }

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public AdminUser findAdminUser(Short userId) {
        return adminUserMapper.selectByPrimaryKey(userId);
    }

    /**
     * 保存用户信息
     *
     * @param adminUser
     */
    @Override
    public void saveUser(AdminUser adminUser) {
        if (adminUser.getUserId() != null) {
            adminUserMapper.updateByPrimaryKeySelective(adminUser);
        } else {
            adminUser.setPassword(MD5Util.MD5Encode("888888"));
            adminUser.setCreateTime(new Date());
            adminUserMapper.insertSelective(adminUser);
        }
    }

    /**
     * 删除用户
     *
     * @param userId
     */
    @Override
    public void deleteUser(Short userId) {
        adminUserMapper.deleteByUserId(userId);
    }

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    @Override
    public AdminUser findByUsername(String userName) {
        return adminUserMapper.findByUsername(userName);
    }

    /**
     * 用户拥有的菜单
     *
     * @param userId
     * @return
     */
    @Override
    public List<AdminMenu> getMenuList(Short userId) {
        return adminMenuMapper.getMenuListByUserId(userId);
    }

    /**
     * 查询菜单
     *
     * @param menuId
     * @return
     */
    @Override
    public AdminMenu findAdminMenu(Short menuId) {
        return adminMenuMapper.selectByPrimaryKey(menuId);
    }

    /**
     * 保存菜单
     *
     * @param adminMenu
     */
    @Override
    public void saveMenu(AdminMenu adminMenu) {
        if (adminMenu.getMenuId() != null) {
            adminMenuMapper.updateByPrimaryKeySelective(adminMenu);
        } else {
            adminMenuMapper.insertSelective(adminMenu);
        }
    }
}
