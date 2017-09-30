package com.chengshi.shop.service.cart;



import com.chengshi.shop.model.cart.Cart;
import com.chengshi.shop.model.cart.CartItem;

import java.util.HashMap;
import java.util.List;

public interface CartService {

    /**
     * 查询会员购物车商品的数目
     *
     * @param memberId
     */
    int getCartItemsCountByMember(Integer memberId);

    /**
     * 根据会员Id获取其对应的购物车
     *
     * @param memberId
     * @return
     */
    Cart getCartByMember(Integer memberId);

    /**
     * 根据会员获取购物车列表
     *
     * @return
     */
    List<CartItem> getCartItemsByMember(Integer memberId);

    /**
     * 添加商品到购物车
     *
     * @param memberId
     * @param productIdAndNum
     * @return
     */
    HashMap<String, Object> addItemToCart(Integer memberId, String productIdAndNum);

    /**
     * 根据memberId 和 传入的货品的id 批量删除购物车中的货品
     *
     * @param memberId       memberId 会员Id
     * @param cartkey
     * @param productIds 货品Id，以逗号“ , ”隔开
     */
    void batchDelInCart(Integer memberId, String cartkey, String productIds);

    /**
     * 删除会员购物车
     *
     * @param memberId
     * @throws Exception
     */
    void deleteCart(Integer memberId);

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
     * 删除购物车
     *
     * @param memberId
     * @param cartKey
     * @param cartItemkey
     * @param productId
     */
    void batchDelInCartPart(Integer memberId, String cartKey, String cartItemkey, String productId);

    /**
     * 改变购物车的选中状态
     *
     * @param memberId
     * @param productId
     * @param chooseType
     * @return
     */
    HashMap<String, Object> changeChooseType(Integer memberId, String productId, String chooseType);

    /**
     * 改变购物车商品的购买数量
     *
     * @param memberId
     * @param productId
     * @param count
     * @return
     * @throws Exception
     */
    HashMap<String, Object> changeProductNum(Integer memberId, String productId, String count);

    /**
     * 购物车全选
     *
     * @param memberId
     * @param allType
     * @return
     */
    HashMap<String, Object> chooseAll(Integer memberId, String allType);

    String getCartItemByKey(String key);

    String getCartByKey(String key);

    void saveCartItem(String key, String cartItemValue);

    void saveCart(String key, String cartValue);

}