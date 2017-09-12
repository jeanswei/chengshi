package com.chengshi.shop.controller.coupon;

import com.chengshi.shop.model.coupon.Coupon;
import com.chengshi.shop.service.coupon.CouponService;
import com.chengshi.shop.util.MessageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 优惠券相关
 * @author xuxinlong
 * @version 2017年09月11日
 */
@RestController
@RequestMapping(value = "/admin")
public class CouponController {
    @Resource
    private CouponService couponService;

    /**
     * 优惠券列表页面
     * @return
     */
    @GetMapping(value = "couponList")
    public ModelAndView couponList(){
        return new ModelAndView("admin/coupon/couponList");
    }

    /**
     * 获取优惠券列表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping(value = "getCouponList")
    public PageInfo<Coupon> getCouponList(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        PageHelper.startPage(pageNumber, pageSize);
        HashMap<String, Object> inMap = new HashMap<>();
        List<Coupon> couponList = couponService.getCouponList(inMap);
        return new PageInfo<>(couponList);
    }

    /**
     * 保存优惠券信息
     * @param coupon
     * @return
     */
    @PostMapping(value = "saveCoupon")
    public HashMap<String, Object> saveCoupon(@ModelAttribute Coupon coupon){
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            couponService.saveCoupon(coupon);
        } catch (Exception e){
            retMap = MessageUtils.error();
        }
        return retMap;
    }
}
