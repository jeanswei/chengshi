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


    @RequestMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("admin/index");
    }

}
