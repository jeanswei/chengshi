package com.chengshi.shop.util;

import com.chengshi.shop.model.admin.AdminUser;
import com.chengshi.shop.model.member.Member;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SessionUtils {

    public static HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpServletResponse getReponse() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

    /**
     * 把会员信息存放到session中
     *
     * @param user
     */
    public static void setMember(Member user) {
        getRequest().getSession().setAttribute("member", user);
    }

    /**
     * 获取当前登录的会员
     */
    public static Member getMember() {
        return (Member) getRequest().getSession().getAttribute("member");
    }
}
 