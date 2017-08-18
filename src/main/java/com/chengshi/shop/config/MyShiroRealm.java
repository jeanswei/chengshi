package com.chengshi.shop.config;


import com.chengshi.shop.model.admin.AdminMenu;
import com.chengshi.shop.model.admin.AdminUser;
import com.chengshi.shop.service.admin.SystemService;
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
 * @author xuxinlong
 * @version 2017年08月18日
 */
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private SystemService systemService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        AdminUser adminUser = (AdminUser) principals.getPrimaryPrincipal();
        List<AdminMenu> menuList = systemService.getMenuList(adminUser.getUserId());
        for (AdminMenu menu : menuList) {
            authorizationInfo.addStringPermission(menu.getMenuUrl());
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("身份认证方法：MyShiroRealm.doGetAuthenticationInfo()");

        String username = (String) token.getPrincipal();
        AdminUser user = systemService.findByUsername(username);
        if (null == user) {
            throw new AccountException("帐号或密码不正确！");
        } else if (user.getStatus() == 2) {
            /**
             * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
             */
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