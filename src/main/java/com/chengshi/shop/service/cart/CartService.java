package com.chengshi.shop.service.cart;


import com.chengshi.shop.model.cart.Cart;
import com.chengshi.shop.model.cart.CartItem;

import java.util.HashMap;
import java.util.List;

/**
 * 购物车相关接口
 *
 * @author 徐新龙
 * @version 2017年4月14日 上午10:57:06
 */
public interface CartService {

    /**
     * 查询会员购物车商品的数目
     *
     * @param memberId
     */
    int getCartNumByMemberId(Integer memberId);

    /**
     * 根据会员获取购物车列表
     *
     * @return
     */
    List<CartItem> getCartItemsByMember(Integer memberId);

    /**
     * 添加商品到购物车
     * @param memberId
     * @param productId
     * @param productNum
     * @return
     */
    HashMap<String, Object> addItemToCart(Integer memberId, Integer productId, Integer productNum);

    /**
     * 根据memberId 和 传入的货品的id 批量删除购物车中的货品
     *
     * @param memberId       memberId 会员Id
     * @param productIds 货品Id，以逗号“ , ”隔开
     */
    void batchDelInCart(Integer memberId, String productIds);

    /**
     * 删除会员购物车
     *
     * @param memberId
     */
    void emptyCartGoods(Integer memberId);

    /**
     * 将List<CartItem>装换为一个HashMap，
     * HashMap包括cartGoodsList和Cart，直接作为返回结果。
     *
     * @param cartItem
     * @param memberId
     */
    HashMap<String, Object> changeCartItemToCartGoods(List<CartItem> cartItem, Integer memberId);

    /**
     * 预生成订单信息
     *
     * @param cartItem
     * @param memberId
     * @return
     */
    HashMap<String, Object> generateOrderInfo(List<CartItem> cartItem, Integer memberId);

    /**
     * 改变购物车的选中状态
     *
     * @param memberId
     * @param productId
     * @return
     */
    void changeChoose(Integer memberId, Integer productId);

    /**
     * 改变购物车商品的购买数量
     *
     * @param memberId
     * @param productId
     * @param productNum
     * @return
     */
    void changeQuantity(Integer memberId, Integer productId, Integer productNum);

    /**
     * 购物车全选
     *
     * @param memberId
     * @param allType
     * @return
     */
    void chooseAll(Integer memberId, Boolean allType);

}