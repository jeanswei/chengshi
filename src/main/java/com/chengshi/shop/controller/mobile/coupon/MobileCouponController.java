package com.chengshi.shop.controller.mobile.coupon;

import com.chengshi.shop.model.cart.CartItem;
import com.chengshi.shop.model.coupon.Coupon;
import com.chengshi.shop.model.goods.GoodsProduct;
import com.chengshi.shop.model.member.Member;
import com.chengshi.shop.service.cart.CartService;
import com.chengshi.shop.service.coupon.CouponService;
import com.chengshi.shop.service.goods.GoodsProductService;
import com.chengshi.shop.util.DateFormatUtil;
import com.chengshi.shop.util.EnumUtil;
import com.chengshi.shop.util.MessageUtils;
import com.chengshi.shop.util.SessionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 优惠券
 *
 * @author xuxinlong
 * @version 2017年09月14日
 */
@RestController
@RequestMapping(value = "/mobile/coupon")
public class MobileCouponController {
    @Resource
    private CouponService couponService;
    @Resource
    private CartService cartService;
    @Resource
    private GoodsProductService goodsProductService;

    /**
     * 获取商品可用优惠券列表
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "getGoodsCoupon")
    public HashMap<String, Object> getGoodsCoupon(@RequestParam Integer goodsId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            Member member = SessionUtils.getMember();
            List<Coupon> couponList = couponService.getCanGetCouponList(goodsId);
            List<HashMap<String, Object>> mapList = new ArrayList<>();
            for (Coupon coupon : couponList) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("couponId", coupon.getCouponId());
                map.put("couponName", coupon.getCouponName());
                map.put("labelName", coupon.getCouponType() == EnumUtil.COUPON_TYPE.全部商品.getValue().byteValue() ? "全场通用" : "指定商品");
                map.put("couponType", coupon.getCouponType());
                map.put("money", coupon.getMoney());
                map.put("couponMoney", coupon.getMoney().doubleValue());
                map.put("needMoney", coupon.getNeedMoney().doubleValue());
                map.put("useStart", DateFormatUtil.formatDate(coupon.getUseStart(), "yyyy.MM.dd"));
                map.put("useEnd", DateFormatUtil.formatDate(coupon.getUseEnd(), "yyyy.MM.dd"));
                map.put("content", coupon.getContent());
                map.put("totalCount", coupon.getTotalCount());
                map.put("getCount", coupon.getGetCount());
                Integer hasCount = couponService.getHasCountByCouponId(member.getMemberId(), coupon.getCouponId());
                //是否可以继续领取
                map.put("canGet", 1);
                if (coupon.getLimitNum() != 0 && coupon.getLimitNum() - hasCount <= 0) {
                    map.put("canGet", 0);
                }
                mapList.add(map);
            }
            retMap.put("couponList", mapList);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 获取购物车内商品可用优惠券
     *
     * @return
     */
    @GetMapping(value = "getCartCoupon")
    public HashMap<String, Object> getCartCoupon() {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            Member member = SessionUtils.getMember();
            List<CartItem> cartItemList = cartService.getCartItemsByMember(member.getMemberId());
            HashSet<Integer> goodsIdList = new HashSet<>();
            for (CartItem cartItem : cartItemList) {
                GoodsProduct product = goodsProductService.getProductByProductId(cartItem.getProductId());
                if (product != null) {
                    goodsIdList.add(product.getGoodsId());
                }
            }
            HashSet<HashMap<String, Object>> mapList = new HashSet<>();
            for (Integer goodsId : goodsIdList) {
                List<Coupon> couponList = couponService.getCanGetCouponList(goodsId);
                for (Coupon coupon : couponList) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("couponId", coupon.getCouponId());
                    map.put("couponName", coupon.getCouponName());
                    map.put("labelName", coupon.getCouponType() == EnumUtil.COUPON_TYPE.全部商品.getValue().byteValue() ? "全场通用" : "指定商品");
                    map.put("money", coupon.getMoney());
                    map.put("couponType", coupon.getCouponType());
                    map.put("needMoney", coupon.getNeedMoney().doubleValue());
                    map.put("useStart", DateFormatUtil.formatDate(coupon.getUseStart(), "yyyy.MM.dd"));
                    map.put("useEnd", DateFormatUtil.formatDate(coupon.getUseEnd(), "yyyy.MM.dd"));
                    map.put("content", coupon.getContent());
                    map.put("totalCount", coupon.getTotalCount());
                    map.put("getCount", coupon.getGetCount());
                    Integer hasCount = couponService.getHasCountByCouponId(member.getMemberId(), coupon.getCouponId());
                    //是否可以继续领取
                    map.put("canGet", 1);
                    if (coupon.getLimitNum() != 0 && coupon.getLimitNum() - hasCount <= 0) {
                        map.put("canGet", 0);
                    }
                    mapList.add(map);
                }
            }
            retMap.put("couponList", mapList);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 领取优惠券
     *
     * @param couponId
     * @return
     */
    @PostMapping(value = "/receiveCoupon")
    public HashMap<String, Object> receiveCoupon(@RequestParam Integer couponId) {
        HashMap<String, Object> retMap = MessageUtils.success("领取成功");
        try {
            Member member = SessionUtils.getMember();
            Coupon coupon = couponService.getCoupon(couponId);
            HashMap<String, Object> resultMap = couponService.checkCouponCanGet(member.getMemberId(), coupon);
            if ("n".equals(resultMap.get("errorCode").toString())) {
                return resultMap;
            }
            couponService.receiveCoupon(member.getMemberId(), coupon);
        } catch (Exception e) {
            retMap = MessageUtils.error("领取失败");
        }
        return retMap;
    }
}
