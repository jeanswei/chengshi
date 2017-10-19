package com.chengshi.shop.controller.mobile.wechat;

import com.chengshi.shop.model.member.Member;
import com.chengshi.shop.service.member.MemberService;
import com.chengshi.shop.util.MessageUtils;
import com.chengshi.shop.util.SessionUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 微信登录模块
 *
 * @author xuxinlong
 * @version 2017年07月20日
 */
@RestController
@RequestMapping(value = "/mobile/wechat")
public class MobileWechatController {
    @Resource
    private WxMpService wxMpService;
    @Resource
    private MemberService memberService;

    @Value("${wechat.mp.host}")
    private String host;
    @Value("${wechat.pay.appSecret}")
    private String appsecret;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 微信验证登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/wxLogin")
    public void wxLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam String requestURI) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        String code = request.getParameter("code");
        if (code == null) {
            Member member = SessionUtils.getMember();
            if (member != null) {
                response.sendRedirect(host + requestURI);
            } else {
                // 构造获取授权链接
                String url = wxMpService.oauth2buildAuthorizationUrl(
                        host + "/mobile/wechat/wxLogin.html?requestURI=" + requestURI,
                        WxConsts.OAUTH2_SCOPE_BASE, "");
                response.sendRedirect(url);
            }
        } else {
            try {
                WxMpOAuth2AccessToken oauth2AccessToken = wxMpService.oauth2getAccessToken(code);
                // 授权信息获取用户
                WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(oauth2AccessToken, "zh_CN");
                //新用户注册
                Member member = memberService.getMemberByOpenId(wxMpUser.getOpenId());
                if (member == null) {
                    member = new Member();
                    member.setOpenId(wxMpUser.getOpenId());
                    member.setHeadImg(wxMpUser.getHeadImgUrl());
                    member.setName(wxMpUser.getNickname());
                    member.setCountry(wxMpUser.getCountry());
                    member.setProvince(wxMpUser.getProvince());
                    member.setCity(wxMpUser.getCity());
                    memberService.saveMember(member);
                }
                SessionUtils.setMember(memberService.getMemberByMemberId(member.getMemberId()));
                response.sendRedirect(host + requestURI);
            } catch (WxErrorException e) {
                response.getWriter().write("非法请求");
            }
        }
    }

    /**
     * 检查登录接口
     *
     * @return
     */
    @RequestMapping(value = "checkLogin")
    public HashMap<String, Object> checkLogin() {
        HashMap<String, Object> retMap = MessageUtils.success("已登录");
        try {
            Member member = SessionUtils.getMember();
            if (member != null) {
                retMap.put("member", member);
            } else {
                retMap.put("errorCode", "NOLOGIN");
                retMap.put("errorText", "未登录");
            }
        } catch (Exception e) {
            retMap = MessageUtils.error("查询失败");
        }
        return retMap;
    }

    /**
     * 获取微信JsapiSignature
     * @param request
     * @return
     */
    @RequestMapping(value = "/getConfig")
    @ResponseBody
    public HashMap<String,Object> getConfig(HttpServletRequest request){
        HashMap<String,Object> retMap  = MessageUtils.success();
        try {
            //当前页面的路径
            String pageUrl = request.getParameter("pageUrl");
            WxJsapiSignature wxJsapiSignature = wxMpService.createJsapiSignature(pageUrl);
            retMap.put("appId", wxJsapiSignature.getAppId());
            retMap.put("nonceStr", wxJsapiSignature.getNonceStr());
            retMap.put("signature", wxJsapiSignature.getSignature());
            retMap.put("timestamp", wxJsapiSignature.getTimestamp());
            retMap.put("url", wxJsapiSignature.getUrl());
        } catch (Exception e){
            retMap = MessageUtils.error();
        }
        return retMap;
    }
}
