package com.chengshi.shop.controller.admin;

import com.chengshi.shop.model.admin.AdminUser;
import com.chengshi.shop.service.admin.SystemService;
import com.chengshi.shop.util.MessageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    /**
     * 保存用户信息
     * @param adminUser
     * @return
     */
    @PostMapping(value = "saveUser")
    public HashMap<String,Object> saveUser(@ModelAttribute AdminUser adminUser){
        HashMap<String,Object> retMap = MessageUtils.success();
        try {
            systemService.saveUser(adminUser);
        } catch (Exception e){
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @PostMapping(value = "deleteUser")
    public HashMap<String,Object> deleteUser(@RequestParam Short userId){
        HashMap<String,Object> retMap = MessageUtils.success();
        try {
            systemService.deleteUser(userId);
        } catch (Exception e){
            retMap = MessageUtils.error();
        }
        return retMap;
    }
}
