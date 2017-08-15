package com.chengshi.shop.controller.admin;

import com.chengshi.shop.model.admin.AdminUser;
import com.chengshi.shop.service.admin.SystemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统管理模块
 * @author xuxinlong
 * @version 2017年08月11日
 */
@RestController
@RequestMapping(value = "/admin")
public class SystemController {
    @Resource
    private SystemService systemService;

    /**
     * 登录页面
     * @return
     */
    @RequestMapping(value = "/login")
    public ModelAndView login(){
        return new ModelAndView("admin/login");
    }

    @RequestMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("admin/index");
    }

    @RequestMapping(value = "/user")
    public ModelAndView user(){
        return new ModelAndView("admin/system/user");
    }

    @RequestMapping(value = "/menu")
    public ModelAndView menu(){
        return new ModelAndView("admin/system/menu");
    }


    /**
     * 获取管理后台用户列表
     * @param request
     * @return
     */
    @GetMapping(value = "/getUserList")
    public PageInfo<AdminUser> getUserList(HttpServletRequest request){
        PageHelper.startPage(1,10);
        List<AdminUser> userList = systemService.getUserList();
        return new PageInfo<>(userList);
    }

    /**
     * 管理用户信息页面
     * @param userId
     * @return
     */
    @GetMapping(value = "/userForm")
    public ModelAndView userForm(@RequestParam(required = false) Short userId){
        ModelAndView mav  = new ModelAndView("/admin/system/userForm");
        AdminUser user = new AdminUser();
        if (userId !=null){
            user = systemService.findAdminUser(userId);
        }
        mav.addObject("user", user);
        return mav;
    }
}
