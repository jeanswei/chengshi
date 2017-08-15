package com.chengshi.shop.service.admin.impl;

import com.chengshi.shop.dao.admin.AdminUserMapper;
import com.chengshi.shop.model.admin.AdminUser;
import com.chengshi.shop.service.admin.SystemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台系统相关接口
 * @author xuxinlong
 * @version 2017年08月15日
 */
@Service
public class SystemServiceImpl implements SystemService {
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
}
