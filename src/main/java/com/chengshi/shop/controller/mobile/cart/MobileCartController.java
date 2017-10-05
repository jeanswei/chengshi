package com.chengshi.shop.controller.mobile.cart;

import com.chengshi.shop.controller.common.BaseController;
import com.chengshi.shop.model.cart.CartItem;
import com.chengshi.shop.model.member.Member;
import com.chengshi.shop.model.member.MemberAddress;
import com.chengshi.shop.service.cart.CartService;
import com.chengshi.shop.service.member.MemberAddressService;
import com.chengshi.shop.util.MessageUtils;
import com.chengshi.shop.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "cart", description = "购物车相关接口")
@RestController
@RequestMapping("/mobile/cart")
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
    @ApiOperation(value = "获取会员购物车商品数量", notes = "获取会员购物车商品数量")
    @GetMapping(value = "/getCartNum")
    public HashMap<String, Object> getCartNum() {
        HashMap<String, Object> retMap = MessageUtils.success("查询成功");
        try {
            Member nowMember = SessionUtils.getMember();
            int cartNum = cartService.getCartNumByMemberId(nowMember.getMemberId());
            retMap.put("cartNum", cartNum);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 将货品添加到购物车
     *
     * @param productId
     * @param productNum
     * @return
     */
    @ApiOperation(value = "添加商品到购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="productId", value = "货品ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name ="productNum", value = "货品数量", required = true, paramType = "query", dataType = "int")
    })
    @PostMapping(value = "addCartGoods")
    public HashMap<String, Object> addCartGoods(@RequestParam Integer productId, @RequestParam Integer productNum) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            //获得当前登陆用户
            Member nowMember = SessionUtils.getMember();
            retMap.putAll(cartService.addItemToCart(nowMember.getMemberId(), productId, productNum));
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
    @ApiOperation(value = "清空购物车商品")
    @PostMapping(value = "emptyCartGoods")
    public HashMap<String, Object> emptyCartGoods() {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            //获得当前登陆用户
            Member nowMember = SessionUtils.getMember();
            cartService.emptyCartGoods(nowMember.getMemberId());
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
    @ApiOperation(value = "获取购物车商品列表")
    @GetMapping(value = "/getCartGoodsList")
    public HashMap<String, Object> getCartGoodsList() {
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
    @ApiOperation(value = "获取购物车商品列表")
    @ApiImplicitParam(name = "productIds", value = "货品Id，多个以逗号隔开", paramType = "query", dataType = "String", required = true)
    @PostMapping(value = "/deleteCartGoods")
    public HashMap<String, Object> deleteCartGoods(@RequestParam String productIds) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            //获得当前登陆用户
            Member nowMember = SessionUtils.getMember();
            cartService.batchDelInCart(nowMember.getMemberId(), productIds);
            List<CartItem> cartItem = cartService.getCartItemsByMember(nowMember.getMemberId());
            /*
             将List<CartItem>装换为一个HashMap，
             HashMap包括cartGoodsList和Cart，直接作为返回结果。
             */
            HashMap<String, Object> cartInfo = cartService.changeCartItemToCartGoods(cartItem, nowMember.getMemberId());
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
    @ApiOperation(value = "购物车去结算")
    @PostMapping(value = "/settleAccount")
    public HashMap<String, Object> settleAccount() {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            //获得当前登陆用户
            Member nowMember = SessionUtils.getMember();
            List<CartItem> cartItemList = cartService.getCartItemsByMember(nowMember.getMemberId());
            List<CartItem> list = new ArrayList<>();
            for (CartItem cartItem : cartItemList) {
                if (cartItem.getIsChoose() && cartItem.getIsValid()) {
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
     */
    @ApiOperation(value = "购物车选择商品或取消选择商品")
    @ApiImplicitParam(name = "productId", value = "货品Id", paramType = "query", dataType = "int", required = true)
    @PostMapping(value = "/chooseCartGoods")
    public HashMap<String, Object> chooseCartGoods(@RequestParam Integer productId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            //获得当前登陆用户
            Member nowMember = SessionUtils.getMember();
            //购物车选中的商品的shopProductId和chooseType
            cartService.changeChoose(nowMember.getMemberId(), productId);
            List<CartItem> cartItem = cartService.getCartItemsByMember(nowMember.getMemberId());
            /*
             将List<CartItem>装换为一个HashMap，
             HashMap包括cartGoodsList和Cart，直接作为返回结果。
             */
            HashMap<String, Object> cartInfo = cartService.changeCartItemToCartGoods(cartItem, nowMember.getMemberId());
            retMap.putAll(cartInfo);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }


    /**
     * 改变购物车里商品数量
     *
     * @param productId
     * @param productNum
     * @return
     */
    @ApiOperation(value = " 改变购物车里商品数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "货品Id", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "productNum", value = "货品数量", paramType = "query", dataType = "int", required = true)
    })
    @PostMapping(value = "/changeCartGoodsNum")
    public HashMap<String, Object> changeCartGoodsNum(@RequestParam Integer productId, @RequestParam Integer productNum) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            //获得当前登陆用户
            Member nowMember = SessionUtils.getMember();
            Integer memberId = nowMember.getMemberId();
            //购物车选中的商品的shopProductId和count
            cartService.changeQuantity(memberId, productId, productNum);
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
     * 全部选中或取消选中
     *
     * @return
     */
    @ApiOperation(value = "全选或全不选购物车商品")
    @ApiImplicitParam(name = "isChooseAll", value = "是否全选", paramType = "query", dataType = "boolean", required = true)
    @PostMapping(value = "/chooseAll")
    public HashMap<String, Object> chooseAll(@RequestParam Boolean isChooseAll) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            // 获得当前登陆用户
            Member nowMember = SessionUtils.getMember();
            // 购物车选中的商品的goodsId和count
            cartService.chooseAll(nowMember.getMemberId(), isChooseAll);
            List<CartItem> cartItem = cartService.getCartItemsByMember(nowMember.getMemberId());
            /*
              将List<CartItem>装换为一个HashMap，
              HashMap包括cartGoodsList和Cart，直接作为返回结果。
             */
            HashMap<String, Object> cartInfo = cartService.changeCartItemToCartGoods(cartItem, nowMember.getMemberId());
            retMap.putAll(cartInfo);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }
}
