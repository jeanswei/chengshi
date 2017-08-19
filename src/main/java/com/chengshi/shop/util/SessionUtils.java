package com.chengshi.shop.util;

import com.chengshi.shop.model.admin.AdminUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;


public class SessionUtils {
    /**
     * 获取当前用户对象shiro
     *
     * @return AdminUser
     */
    public static AdminUser getCurrentUser() {
        return (AdminUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取当前用户session
     *
     * @return session
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 获取当前用户名
     *
     * @return
     */
    public static String getUserName() {
        if (getCurrentUser() != null) {
            return getCurrentUser().getUserName();
        } else {
            return "";
        }
    }

    /**
     * 获取当前用户id
     *
     * @return
     */
    public static Short getUserId() {
        if (getCurrentUser() != null) {
            return getCurrentUser().getUserId();
        } else {
            return null;
        }
    }
}
 