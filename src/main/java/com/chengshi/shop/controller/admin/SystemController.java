package com.chengshi.shop.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统管理模块
 * @author xuxinlong
 * @version 2017年08月11日
 */
@RestController
@RequestMapping(value = "/admin")
public class SystemController {


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

}
