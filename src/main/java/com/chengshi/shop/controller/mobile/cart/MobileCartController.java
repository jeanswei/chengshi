package com.chengshi.shop.controller.mobile.cart;

import com.chengshi.shop.controller.common.BaseController;
import com.chengshi.shop.model.cart.CartItem;
import com.chengshi.shop.model.member.Member;
import com.chengshi.shop.model.member.MemberAddress;
import com.chengshi.shop.service.cart.CartService;
import com.chengshi.shop.service.member.MemberAddressService;
import com.chengshi.shop.util.MessageUtils;
import com.chengshi.shop.util.SessionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 购物车相关接口
 *
 * @author 徐新龙
 * @version 2017年4月14日 上午10:57:06
 */
@RestController
@RequestMapping("/mobile/shoppingCart")
public class MobileCartController extends BaseController {

    @Resource
    private CartService cartService;
    @Resource
    private MemberAddressService memberAddressService;



    /**
     * 查询会员购物车货品的数目，判断购物车中是否有货品
     *
     * @return
     */
    @GetMapping(value = "/getCartNum")
    public HashMap<String, Object> getCartNum() {
        HashMap<String, Object> retMap = MessageUtils.success("查询成功");
        try {
            Member nowMember = SessionUtils.getMember();
            int cartNum = cartService.getCartItemsCountByMember(nowMember.getMemberId());
            retMap.put("cartNum", cartNum);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 将货品添加到购物车
     * @param productIdAndNum
     * @return
     */
    @PostMapping(value = "addShoppingCart")
    public HashMap<String, Object> addShoppingCart(@RequestParam String productIdAndNum) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            //获得当前登陆用户
            Member nowMember = SessionUtils.getMember();

            retMap.putAll(cartService.addItemToCart(nowMember.getMemberId(), productIdAndNum.trim()));

        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 清空我的购物车
     *
     * @return
     */
    @PostMapping(value = "emptyMyCart")
    public HashMap<String, Object> emptyMyCart() {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            //获得当前登陆用户
            Member nowMember = SessionUtils.getMember();
            cartService.deleteCart(nowMember.getMemberId());
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 获得购物车商品列表
     *
     * @return
     */
    @GetMapping(value = "/getShoppingCartList")
    public HashMap<String, Object> getShoppingCartList() {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            //获得当前登陆用户
            Member nowMember = SessionUtils.getMember();
            List<CartItem> cartItemList = cartService.getCartItemsByMember(nowMember.getMemberId());
            /*
             将List<CartItem>装换为一个HashMap，
             HashMap包括cartGoodsList和Cart，直接作为返回结果。
             */
            HashMap<String, Object> cartInfo = cartService.changeCartItemToCartGoods(cartItemList, nowMember.getMemberId());
            retMap.putAll(cartInfo);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 传入的货品的id 批量删除购物车中的商品
     * productIds 货品Id，以逗号“ , ”隔开
     *
     * @param productIds
     * @return
     */
    @PostMapping(value = "/deleteShopCart")
    public HashMap<String, Object> deleteShopCart(@RequestParam String productIds) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            //获得当前登陆用户
            Member nowMember = SessionUtils.getMember();
            Integer memberId = nowMember.getMemberId();

            for (String productId : productIds.split(",")) {
                String cartkey = "cart:" + memberId;
                String cartItemkey = "cartItem:" + memberId + ":" + productId;
                cartService.batchDelInCartPart(memberId, cartkey, cartItemkey, productId);
            }

            List<CartItem> cartItem = cartService.getCartItemsByMember(memberId);
            /*
             将List<CartItem>装换为一个HashMap，
             HashMap包括cartGoodsList和Cart，直接作为返回结果。
             */
            HashMap<String, Object> cartInfo = cartService.changeCartItemToCartGoods(cartItem, memberId);
            retMap.putAll(cartInfo);

        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 购物车去结算
     *
     * @return
     */
    @PostMapping(value = "/countShopCart")
    public HashMap<String, Object> countShopCart() {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            //获得当前登陆用户
            Member nowMember = SessionUtils.getMember();
            List<CartItem> cartItemList = cartService.getCartItemsByMember(nowMember.getMemberId());
            List<CartItem> list = new ArrayList<>();
            for (CartItem cartItem : cartItemList) {
                if (cartItem.getChooseType() == 1 && cartItem.getIsValid() == 1) {
                    list.add(cartItem);
                }
            }
            if (list.isEmpty()) {
                return MessageUtils.error("请先选择商品");
            }
            HashMap<String, Object> cartInfo = cartService.generateOrderInfo(list, nowMember.getMemberId());
            retMap.putAll(cartInfo);
            //获取默认收货地址
            MemberAddress address = memberAddressService.getDefaultAddress(nowMember.getMemberId());
            retMap.put("address", address);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 改变购物车里勾选状态
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/chooseButton")
    public HashMap<String, Object> chooseButton(@RequestParam String productId, @RequestParam String chooseType) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            //获得当前登陆用户
            Member nowMember = SessionUtils.getMember();
            //购物车选中的商品的shopProductId和chooseType
            HashMap<String, Object> map = cartService.changeChooseType(nowMember.getMemberId(), productId, chooseType);
            if (map.get("status").equals("y")) {
                List<CartItem> cartItem = cartService.getCartItemsByMember(nowMember.getMemberId());
                /*
                 将List<CartItem>装换为一个HashMap，
                 HashMap包括cartGoodsList和Cart，直接作为返回结果。
                 */
                HashMap<String, Object> cartInfo = cartService.changeCartItemToCartGoods(cartItem, nowMember.getMemberId());
                retMap.putAll(cartInfo);
            } else {
                return MessageUtils.error();
            }
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }


    /**
     * 改变购物车里商品数量
     *
     * @return
     */
    @PostMapping(value = "/changeProductNum")
    public HashMap<String, Object> changeProductNum(@RequestParam String productId, @RequestParam String count) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            //获得当前登陆用户
            Member nowMember = SessionUtils.getMember();
            Integer memberId = nowMember.getMemberId();
            //购物车选中的商品的shopProductId和count
            HashMap<String, Object> map = cartService.changeProductNum(memberId, productId, count);
            if (map.get("status").equals("y")) {
                List<CartItem> cartItem = cartService.getCartItemsByMember(memberId);
                /*
                 将List<CartItem>装换为一个HashMap，
                 HashMap包括cartGoodsList和Cart，直接作为返回结果。
                 */
                HashMap<String, Object> cartInfo = cartService.changeCartItemToCartGoods(cartItem, memberId);
                retMap.putAll(cartInfo);
            } else {
                return MessageUtils.error();
            }
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 全部选中或取消选中
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/chooseAll")
    public HashMap<String, Object> chooseAll(@RequestParam String allType) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            /* 获得当前登陆用户 */
            Member nowMember = SessionUtils.getMember();
            Integer memberId = nowMember.getMemberId();
            /* 购物车选中的商品的goodsId和count */
            /* 0全部取消选中，1全部选中 */
            HashMap<String, Object> map = cartService.chooseAll(memberId, allType);
            if (map.get("status").equals("y")) {
                List<CartItem> cartItem = cartService.getCartItemsByMember(memberId);
                /*
                  将List<CartItem>装换为一个HashMap，
                  HashMap包括cartGoodsList和Cart，直接作为返回结果。
                 */
                HashMap<String, Object> cartInfo = cartService.changeCartItemToCartGoods(cartItem, memberId);
                retMap.putAll(cartInfo);
            }
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }
}
