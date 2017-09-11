package com.chengshi.shop.controller.coupon;

import com.chengshi.shop.model.coupon.Coupon;
import com.chengshi.shop.service.coupon.CouponService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
     * 优惠券列表
     * @return
     */
    @GetMapping(value = "couponList")
    public ModelAndView couponList(){
        return new ModelAndView("admin/coupon/couponList");
    }

    @GetMapping(value = "getCouponList")
    public PageInfo<Coupon> getCouponList(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        PageHelper.startPage(pageNumber, pageSize);
        HashMap<String, Object> inMap = new HashMap<>();
        List<Coupon> couponList = couponService.getCouponList(inMap);
        return new PageInfo<>(couponList);
    }
}
