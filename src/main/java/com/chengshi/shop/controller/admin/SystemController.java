package com.chengshi.shop.controller.admin;

import com.chengshi.shop.model.admin.AdminUser;
import com.chengshi.shop.service.admin.SystemService;
import com.chengshi.shop.util.MessageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin/login");
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> "+exception;
                System.out.println("else -- >" + exception);
            }
        }
        mav.addObject("msg", msg);
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout(RedirectAttributes redirectAttributes) {
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message", "您已安全退出");
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        return new ModelAndView("admin/index");
    }

    @RequestMapping(value = "/user")
    public ModelAndView user() {
        return new ModelAndView("admin/system/user");
    }

    @RequestMapping(value = "/menu")
    public ModelAndView menu() {
        return new ModelAndView("admin/system/menu");
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
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
