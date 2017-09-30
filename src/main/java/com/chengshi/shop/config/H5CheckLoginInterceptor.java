package com.chengshi.shop.config;

import com.chengshi.shop.model.member.Member;
import com.chengshi.shop.service.member.MemberService;
import com.chengshi.shop.util.JsonToHtmlUtil;
import com.chengshi.shop.util.SessionUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Objects;

/**
 * 检验h5是否登陆
 *
 * @author Administrator
 */
public class H5CheckLoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private MemberService memberService;

    /**
     * controller 执行之前调用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //测试环境
        boolean devMode = true;
        if (devMode) {
            //测试环境代码
            Member member = new Member();
            member.setMemberId(1);
            member.setHeadImg("http://wx.qlogo.cn/mmhead/OB5u3FQB56UXXf7WHZeTVtV9AeicCfFsuL3xDTGnVXBE/0");
            member.setOpenId("owLFNw2rHaEbaOxnbi-x5drzjKHI");
            member.setName("测试会员");
            SessionUtils.setMember(member);
        }
        Member nowMember = SessionUtils.getMember();
        if (nowMember == null) {
            HashMap<String, Object> retMap = new HashMap<>();
            retMap.put("errorCode", "NOLOGIN");
            retMap.put("errorText", "未登录");
            retMap.put("accreditUrl", "/mobile/login/wxLogin.html?requestURI=");
            JsonToHtmlUtil.outJson(response, retMap);
            return false;
        }
        return true;
    }

    /**
     * controller 执行之后，且页面渲染之前调用
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    /**
     * 页面渲染之后调用，一般用于资源清理操作
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }


}