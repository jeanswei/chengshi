package com.chengshi.shop.service.admin.impl;

import com.chengshi.shop.dao.admin.AdminMenuMapper;
import com.chengshi.shop.dao.admin.AdminUserMapper;
import com.chengshi.shop.dao.admin.AdminUserMenuMapper;
import com.chengshi.shop.model.admin.AdminMenu;
import com.chengshi.shop.model.admin.AdminUser;
import com.chengshi.shop.model.admin.AdminUserMenu;
import com.chengshi.shop.service.admin.SystemService;
import com.chengshi.shop.util.MD5Util;
import org.apache.commons.collections.CollectionUtils;
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
    @Resource
    private AdminUserMenuMapper adminUserMenuMapper;

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
        return adminMenuMapper.getMenuListByUserId(userId, null);
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

    /**
     * 获取所有菜单
     *
     * @return
     */
    @Override
    public List<AdminMenu> selectAllMenu(Short pid) {
        return adminMenuMapper.selectAllMenu(pid);
    }

    /**
     * 删除菜单
     *
     * @param menuId
     */
    @Override
    public void deleteMenu(Short menuId) {
        adminMenuMapper.deleteByPrimaryKey(menuId);
    }

    /**
     * 用户拥有的父级菜单
     *
     * @param userId
     * @param pid
     * @return
     */
    @Override
    public List<AdminMenu> getMenuList(Short userId, Short pid) {
        return adminMenuMapper.getMenuListByUserId(userId, pid);
    }

    /**
     * 获取用户拥有菜单id的集合
     *
     * @param userId
     * @return
     */
    @Override
    public List<Short> getUserMenuIds(Short userId) {
        return adminUserMenuMapper.getMenuIdsByUserId(userId);
    }

    /**
     * 给用户授权菜单权限
     *
     * @param userId
     * @param newList
     */
    @Override
    public void saveUserMenu(Short userId, List<Short> newList) {
        if (CollectionUtils.isEmpty(newList)) {
            throw new RuntimeException("请先选择资源权限");
        }
        List<Short> oldList = getUserMenuIds(userId);
        //是否删除
        if (CollectionUtils.isNotEmpty(oldList)) {
            for (int i = 0, j = oldList.size(); i < j; i++) {
                if (!newList.contains(oldList.get(i))) {
                    adminUserMenuMapper.deleteUserMenu(userId, oldList.get(i));
                }
            }
        }

        //是否添加
        for (int i = 0, j = newList.size(); i < j; i++) {
            if (!oldList.contains(newList.get(i))) {
                adminUserMenuMapper.insertSelective(new AdminUserMenu(userId, newList.get(i)));
            }
        }
    }
}
