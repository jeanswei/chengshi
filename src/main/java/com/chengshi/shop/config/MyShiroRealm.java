package com.chengshi.shop.config;


import com.chengshi.shop.model.admin.AdminMenu;
import com.chengshi.shop.model.admin.AdminUser;
import com.chengshi.shop.service.admin.SystemService;
import com.chengshi.shop.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

/**
 * 自定义验证权限登录
 *
 * @author xuxinlong
 * @version 2017年08月18日
 */
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private SystemService systemService;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        AdminUser adminUser = (AdminUser) principals.getPrimaryPrincipal();
        List<AdminMenu> menuList = systemService.getMenuList(adminUser.getUserId());
        for (AdminMenu menu : menuList) {
            authorizationInfo.addStringPermission(menu.getMenuUrl());
        }
        return authorizationInfo;
    }

    /**
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        AdminUser user = systemService.findByUsername(token.getUsername());
        if (null == user) {
            throw new AccountException("用户名不存在！");
        } else if (!user.getPassword().equals(MD5Util.MD5Encode(new String(token.getPassword())))) {
            throw new AccountException("密码不正确！");
        } else if (user.getStatus() == 2) {
            throw new DisabledAccountException("帐号已被禁用！");
        } else {
            //更新登录时间 last login time
            user.setLastLogin(new Date());
            try {
                user.setLastIp(InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            systemService.saveUser(user);
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }

}