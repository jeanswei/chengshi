package com.chengshi.shop.service.admin;

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
}
