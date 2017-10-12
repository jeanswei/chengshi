package com.chengshi.shop.controller.common;

import com.github.pagehelper.PageHelper;
import com.sun.beans.editors.DoubleEditor;
import com.sun.beans.editors.FloatEditor;
import com.sun.beans.editors.IntegerEditor;
import com.sun.beans.editors.LongEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 基础处理
 * @author xuxinlong
 * @version 2017年09月12日
 */
@Controller
public class BaseController {

    public static HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 绑定转换格式
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(int.class, new IntegerEditor());
        binder.registerCustomEditor(long.class, new LongEditor());
        binder.registerCustomEditor(double.class, new DoubleEditor());
        binder.registerCustomEditor(float.class, new FloatEditor());
    }

    /**
     * 开启分页
     */
    protected void startPage() {
        int pageNumber = 1;//页号 默认1
        int pageSize = 10;//每页数据条数 默认10条
        if (getRequest().getParameter("pageNumber") != null) {
            pageNumber = Integer.parseInt(getRequest().getParameter("pageNumber"));
        }
        if (getRequest().getParameter("pageSize") != null) {
            pageSize = Integer.parseInt(getRequest().getParameter("pageSize"));
        }
        PageHelper.startPage(pageNumber, pageSize);
    }
}
