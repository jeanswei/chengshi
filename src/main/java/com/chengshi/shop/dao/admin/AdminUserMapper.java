package com.chengshi.shop.dao.admin;

import com.chengshi.shop.model.admin.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminUserMapper {
    int deleteByPrimaryKey(Short userId);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Short userId);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

    /**
     * 获取后台管理用户列表
     * @return
     */
    List<AdminUser> getUserList();
}