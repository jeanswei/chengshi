package com.chengshi.shop.service.admin.impl;

import com.chengshi.shop.dao.admin.AdminUserMapper;
import com.chengshi.shop.model.admin.AdminUser;
import com.chengshi.shop.service.admin.SystemService;
import com.chengshi.shop.util.DateFormatUtil;
import org.apache.tomcat.util.security.MD5Encoder;
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
            String newPassword = "888888";
            adminUser.setPassword(MD5Encoder.encode(newPassword.getBytes()));
            adminUser.setCreateTime(DateFormatUtil.currentTimeStamp());
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
}
