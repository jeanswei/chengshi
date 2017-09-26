package com.chengshi.shop.controller.admin.coupon;

import com.chengshi.shop.controller.common.BaseController;
import com.chengshi.shop.model.coupon.Coupon;
import com.chengshi.shop.model.coupon.CouponGoods;
import com.chengshi.shop.model.goods.Goods;
import com.chengshi.shop.service.coupon.CouponService;
import com.chengshi.shop.service.goods.GoodsService;
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
public class CouponController extends BaseController{
    @Resource
    private CouponService couponService;
    @Resource
    private GoodsService goodsService;



    /**
     * 优惠券列表页面
     * @return
     */
    @GetMapping(value = "couponList")
    public ModelAndView couponList(){
        return new ModelAndView("admin/coupon/couponList");
    }

    /**
     * 优惠券编辑页面
     * @return
     */
    @GetMapping(value = "couponForm")
    public ModelAndView couponForm(@RequestParam(required = false)Integer couponId){
        ModelAndView mav = new ModelAndView("admin/coupon/couponForm");
        Coupon coupon = new Coupon();
        if (couponId!=null){
            coupon = couponService.getCoupon(couponId);
        }
        mav.addObject("coupon", coupon);
        return mav;
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
     * 优惠适用商品列表
     * @param couponId
     * @return
     */
    @GetMapping(value = "getCouponGoodsList")
    public List<CouponGoods> getCouponGoodsList(@RequestParam Integer couponId){
        return couponService.getCouponGoodsList(couponId);
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
