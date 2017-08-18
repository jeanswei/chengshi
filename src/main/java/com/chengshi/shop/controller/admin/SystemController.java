package com.chengshi.shop.controller.admin;

import com.chengshi.shop.model.admin.AdminUser;
import com.chengshi.shop.service.admin.SystemService;
import com.chengshi.shop.util.MessageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 系统管理模块
 *
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
     *
     * @return
     */
    @RequestMapping(value = "/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("admin/login");
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            mav = new ModelAndView("redirect:/admin/index");
        }
        return mav;
    }

    /**
     * 登录接口
     *
     * @param username
     * @param password
     * @param rememberMe
     * @return
     */
    @PostMapping(value = "/doLogin")
    public HashMap<String, Object> doLogin(@RequestParam String username, @RequestParam String password,
                                           @RequestParam(required = false) String rememberMe) {
        HashMap<String, Object> retMap = MessageUtils.success("登录成功");
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe != null);
            SecurityUtils.getSubject().login(token);
        } catch (Exception e) {
            retMap = MessageUtils.error(e.getMessage());
        }
        return retMap;
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("/logout")
    public ModelAndView logout() {
        SecurityUtils.getSubject().logout();
        return new ModelAndView("redirect:/admin/login");
    }

    /**
     * 后台首页
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        return new ModelAndView("admin/index");
    }

    /**
     * 后台用户管理
     *
     * @return
     */
    @RequestMapping(value = "/user")
    public ModelAndView user() {
        return new ModelAndView("admin/system/user");
    }

    /**
     * 后台菜单管理
     *
     * @return
     */
    @RequestMapping(value = "/menu")
    public ModelAndView menu() {
        return new ModelAndView("admin/system/menu");
    }

    @RequestMapping("/403")
    public String unauthorizedRole() {
        System.out.println("------没有权限-------");
        return "403";
    }

    /**
     * 获取管理后台用户列表
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/getUserList")
    public PageInfo<AdminUser> getUserList(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<AdminUser> userList = systemService.getUserList();
        return new PageInfo<>(userList);
    }

    /**
     * 管理用户信息页面
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/userForm")
    public ModelAndView userForm(@RequestParam(required = false) Short userId) {
        ModelAndView mav = new ModelAndView("/admin/system/userForm");
        AdminUser user = new AdminUser();
        if (userId != null) {
            user = systemService.findAdminUser(userId);
        }
        mav.addObject("user", user);
        return mav;
    }

    /**
     * 保存用户信息
     *
     * @param adminUser
     * @return
     */
    @PostMapping(value = "saveUser")
    public HashMap<String, Object> saveUser(@ModelAttribute AdminUser adminUser) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            systemService.saveUser(adminUser);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 检查用户名是否存在
     *
     * @param userName
     * @return
     */
    @GetMapping(value = "checkUserName")
    public Boolean checkUserName(@RequestParam Short userId, @RequestParam String userName) {
        AdminUser user = systemService.findByUsername(userName);
        if (userId != null) {
            AdminUser adminUser = systemService.findAdminUser(userId);
            return adminUser.getUserName().equals(userName) || user == null;
        } else {
            return user == null;
        }
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @PostMapping(value = "deleteUser")
    public HashMap<String, Object> deleteUser(@RequestParam Short userId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            systemService.deleteUser(userId);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }
}
