package com.chengshi.shop.service.cart.impl;

import com.chengshi.shop.dao.goods.GoodsMapper;
import com.chengshi.shop.dao.goods.GoodsProductMapper;
import com.chengshi.shop.model.cart.Cart;
import com.chengshi.shop.model.cart.CartGoods;
import com.chengshi.shop.model.cart.CartItem;
import com.chengshi.shop.model.coupon.Coupon;
import com.chengshi.shop.model.goods.Goods;
import com.chengshi.shop.model.goods.GoodsProduct;
import com.chengshi.shop.model.promotion.Promotion;
import com.chengshi.shop.model.promotion.PromotionRule;
import com.chengshi.shop.service.cart.CartService;
import com.chengshi.shop.service.coupon.CouponService;
import com.chengshi.shop.service.member.MemberAssetsService;
import com.chengshi.shop.service.order.OrderSaveService;
import com.chengshi.shop.service.promotion.PromotionService;
import com.chengshi.shop.util.DateFormatUtil;
import com.chengshi.shop.util.EnumUtil;
import com.chengshi.shop.util.NumericUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 购物车相关
 *
 * @author xuxinlong
 * @version 2016年9月5日
 */
@Service
public class CartServiceImpl implements CartService {

    private final StringRedisTemplate stringRedisTemplate;
    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> vopCart;
    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> vopCartItem;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private GoodsProductMapper goodsProductMapper;
    @Resource
    private CouponService couponService;
    @Resource
    private MemberAssetsService memberAssetsService;
    @Resource
    private PromotionService promotionService;
    @Resource
    private OrderSaveService orderSaveService;


    @Autowired
    public CartServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 查询会员购物车商品的数目
     *
     * @param memberId
     */
    @Override
    public int getCartItemsCountByMember(Integer memberId) {
        Cart cart = getCartByMember(memberId);
        if (cart != null) {
            String items = cart.getProductIds();
            if (items == null || items.equals(""))
                return 0;
            else
                return items.split(",").length;
        }
        return 0;
    }

    /**
     * 根据会员Id获取其对应的购物车
     *
     * @param memberId
     * @return
     */
    @Override
    public Cart getCartByMember(Integer memberId) {
        Cart cart = new Cart();
        String cartValue = vopCart.get("cart:" + memberId);
        cart.setProductIds(cartValue);
        return cart;
    }

    /**
     * 根据会员获取购物车列表
     *
     * @param memberId
     * @return
     * @throws Exception
     */
    @Override
    public List<CartItem> getCartItemsByMember(Integer memberId) {
        List<CartItem> cartItemList = new ArrayList<>();
        String cartValue = vopCart.get("cart:" + memberId);

        if (cartValue != null && !"".equals(cartValue)) {
            String prefix = "cartItem:" + memberId + ":";
            for (String productId : cartValue.split(",")) {
                String cartItemValue = vopCartItem.get(prefix + productId);
                String num = "";
                String chooseType = "";
                if (cartItemValue != null && !cartItemValue.equals("")) {
                    num = cartItemValue.split(",")[0];
                    chooseType = cartItemValue.split(",")[1];
                }
                CartItem cartItem = new CartItem();
                cartItem.setMemberId(memberId);
                cartItem.setProductId(Integer.parseInt(productId));
                if (num != null && !num.equals("")) {
                    cartItem.setProductNum(Integer.parseInt(num));
                } else {
                    cartItem.setProductNum(0);
                }
                if (chooseType != null && !chooseType.equals("")) {
                    cartItem.setChooseType(Byte.parseByte(chooseType));
                } else {
                    cartItem.setChooseType((byte) 1);
                }
                cartItemList.add(cartItem);
            }
        }
        return cartItemList;
    }

    /**
     * 添加商品到购物车
     *
     * @param memberId
     * @param productIdAndNum
     * @return
     * @throws Exception
     */
    @Override
    public HashMap<String, Object> addItemToCart(Integer memberId, String productIdAndNum) {
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("status", "y");
        String cartKey = "cart:" + memberId;
        for (String ids : productIdAndNum.split(",")) {
            String productId = ids.split("-")[0];
            String num = ids.split("-")[1];

            String cartItemKey = "cartItem:" + memberId + ":";
            String cartValue = vopCart.get(cartKey);
            String chooseType = "1";//默认选中

            // 如果还不存在购物车 则新建个
            if (cartValue == null) {
                cartValue = productId;
                //对购物车对应数据的设置
                vopCart.set(cartKey, cartValue);
                //将添加的商品加入购物车
                String cartItemValue = num + "," + chooseType;
                cartItemKey = cartItemKey + productId;
                vopCartItem.set(cartItemKey, cartItemValue);
            } else {
                cartItemKey = cartItemKey + productId;
                String cartItemValue = vopCartItem.get(cartItemKey);
                // 购物车中还没有对应的商品 将商品加入购物车 并将商品的id追加到购物车productIds中
                if (cartItemValue == null) {
                    cartItemValue = num + "," + chooseType;
                    vopCartItem.set(cartItemKey, cartItemValue);
                    if (cartValue.equals(""))
                        cartValue = productId;
                    else
                        cartValue = cartValue + "," + productId;
                    vopCart.set(cartKey, cartValue);
                } else {
                    String count = cartItemValue.split(",")[0];
                    String choseType = cartItemValue.split(",")[1];
                    // 购物车中有对应的商品，直接修改数量
                    Integer temp = Integer.parseInt(count);
                    temp += Integer.valueOf(num);
                    cartItemValue = temp.toString() + "," + choseType;
                    vopCartItem.set(cartItemKey, cartItemValue);
                }
            }
        }
        String cartValue = vopCart.get(cartKey);
        retMap.put("cartNum", cartValue.split(",").length);
        return retMap;
    }


    /**
     * 根据memberId 和 传入的货品的id 批量删除购物车中的货品
     *
     * @param memberId   memberId 会员Id
     * @param cartkey
     * @param productIds
     * @throws Exception
     */
    @Override
    public void batchDelInCart(Integer memberId, String cartkey, String productIds) {
        String prefix = "cartItem:" + memberId + ":";
        List<String> keys = new ArrayList<>();
        // 批量生产keys 在商品前加上 cartItem:memberId:productId
        for (String productId : productIds.split(",")) {
            keys.add(prefix + productId);
        }
        // 批量删除商品
        stringRedisTemplate.delete(keys);

        String cartValue = vopCart.get(cartkey);
        Collection<String> disjunction = disjunction(cartValue, productIds);
        StringBuilder sb = new StringBuilder();
        for (String aDisjunction : disjunction) {
            sb.append(aDisjunction).append(",");
        }
        int len = sb.length();
        if (len > 0)
            cartValue = sb.substring(0, len - 1);
        else
            cartValue = sb.toString();
        vopCart.set(cartkey, cartValue);
    }

    /**
     * 删除购物车对应的数据
     *
     * @param memberId
     * @param cartKey
     * @param cartItemkey
     * @param productId
     * @throws Exception
     */
    @Override
    public void batchDelInCartPart(Integer memberId, String cartKey, String cartItemkey, String productId) {
        List<String> keys = new ArrayList<>();
        // 批量生产keys 在商品前加上 cartItem:memberId:productId
        keys.add(cartItemkey);
        // 批量删除商品
        stringRedisTemplate.delete(keys);
        String cartValue = vopCart.get(cartKey);
        Collection<String> disjunction = disjunction(cartValue, productId);
        StringBuilder sb = new StringBuilder();
        for (String aDisjunction : disjunction) {
            sb.append(aDisjunction).append(",");
        }
        int len = sb.length();
        if (len > 0)
            cartValue = sb.substring(0, len - 1);
        else
            cartValue = sb.toString();
        vopCart.set(cartKey, cartValue);
    }

    /**
     * 根据memberId 删除对应的购物车
     *
     * @param memberId
     */
    @Override
    public void deleteCart(Integer memberId) {
        String key = "cart:" + memberId;
        String cartValue = vopCart.get(key);
        // 删除购物车里的所有商品
        batchDelInCart(memberId, key, cartValue);

        stringRedisTemplate.delete(key);
    }

    /**
     * 将List<CartItem>装换为一个HashMap，
     * HashMap包括cartGoodsList和Cart，直接作为返回结果。
     *
     * @param cartItem
     * @throws Exception
     */
    @Override
    public HashMap<String, Object> changeCartItemToCartGoods(List<CartItem> cartItem, Integer memberId) {
        HashMap<String, Object> retMap = new HashMap<>();
        Cart cart = new Cart();
        if (cartItem == null || cartItem.isEmpty()) {
            retMap.put("cartGoodsList", new ArrayList<CartGoods>());
            retMap.put("cart", new Cart());
            return retMap;// 如果还没有购物车 请买家去购物 添加购物车
        }
        //计算购物车商品总价
        BigDecimal totalMoney = BigDecimal.ZERO;
        //优惠金额
        BigDecimal promotionMoney = BigDecimal.ZERO;

        byte isChooseAll = 1;
        //商品按活动分类
        HashMap<Integer, HashMap<String, Object>> promotionMap = new HashMap<>();
        //遍历cartItem
        HashSet<Integer> goodsIdList = new HashSet<>();
        for (CartItem cItem : cartItem) {
            CartGoods cGoods = new CartGoods();
            GoodsProduct product = goodsProductMapper.selectByPrimaryKey(cItem.getProductId());
            Goods goods = goodsMapper.selectByPrimaryKey(product.getGoodsId());
            if (goods.getIsDelete() || product.getIsDelete()) {
                cGoods.setIsValid(0);
                cGoods.setNoValReason("商品已失效");
                cGoods.setChooseType((byte) 0);
            } else if (!goods.getIsOnSale()) {
                cGoods.setIsValid(0);
                cGoods.setNoValReason("商品已下架");
                cGoods.setChooseType((byte) 0);
            } else if (cItem.getProductNum() > product.getStore()) {
                cGoods.setIsValid(2);
                cGoods.setNoValReason("库存不足");
                cGoods.setChooseType((byte) 0);
            } else {
                cGoods.setIsValid(1);
                cGoods.setChooseType(cItem.getChooseType());
            }
            cGoods.setGoodsId(goods.getGoodsId());
            cGoods.setProductId(product.getProductId());
            cGoods.setGoodsName(goods.getGoodsName());
            cGoods.setGoodsImage(goods.getGoodsImg());
            cGoods.setMarktPrice(product.getMarktPrice());
            cGoods.setPrice(product.getPrice());
            cGoods.setProductNum(cItem.getProductNum());
            cGoods.setTotalMoney(cGoods.getPrice().multiply(new BigDecimal(cGoods.getProductNum())));
            cGoods.setStore(product.getStore());
            cGoods.setSpecView(product.getSpecView());

            //无效的商品无法勾选
            if (cItem.getChooseType() == 1 && cGoods.getIsValid() != 1) {
                String cartItemKey = "cartItem:" + memberId + ":" + cItem.getProductId();
                String cartItemValue = "" + cItem.getProductNum() + ",0";
                vopCartItem.set(cartItemKey, cartItemValue);
                cGoods.setChooseType((byte) 0);
            }
            //计算勾选商品的金额
            if (cGoods.getChooseType() == 1) {
                totalMoney = totalMoney.add(cGoods.getTotalMoney());
            } else {
                isChooseAll = 0;
            }
            //将同一活动下商品归类
            Promotion promotion = promotionService.getPromotionByGoodsId(product.getGoodsId());
            HashMap<String, Object> map = new HashMap<>();
            if (promotion != null) {
                //如果已存在则插入，不存在就新建
                if (promotionMap.get(promotion.getPromotionId()) != null) {
                    ((List<CartGoods>) promotionMap.get(promotion.getPromotionId()).get("goodsList")).add(cGoods);
                } else {
                    List<CartGoods> goodsList = new ArrayList<>();
                    goodsList.add(cGoods);
                    map.put("goodsList", goodsList);
                    map.put("promotionList", promotionService.getGoodsPromotionList(product.getGoodsId()));
                    promotionMap.put(promotion.getPromotionId(), map);
                }
            } else {
                if (promotionMap.get(0) != null) {
                    ((List<CartGoods>) promotionMap.get(0).get("goodsList")).add(cGoods);
                } else {
                    List<CartGoods> goodsList = new ArrayList<>();
                    goodsList.add(cGoods);
                    map.put("goodsList", goodsList);
                    map.put("promotionList", promotionService.getGoodsPromotionList(product.getGoodsId()));
                    promotionMap.put(0, map);
                }
            }
            goodsIdList.add(product.getGoodsId());
        }
        List<HashMap<String, Object>> mapList = new ArrayList<>();
        for (Object o : promotionMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            mapList.add((HashMap<String, Object>) entry.getValue());
            List<CartGoods> goodsList = (List<CartGoods>) ((HashMap<String, Object>) entry.getValue()).get("goodsList");
            BigDecimal goodsTotalMoney = BigDecimal.ZERO;
            for (CartGoods goods : goodsList) {
                goodsTotalMoney = goodsTotalMoney.add(goods.getTotalMoney());
            }
            List<PromotionRule> ruleList = promotionService.getPromotionRuleList((Integer) entry.getKey());
            BigDecimal discountMoney = BigDecimal.ZERO;
            for (PromotionRule promotionRule : ruleList) {
                //计算满减金额
                if (promotionRule.getDiscount().compareTo(BigDecimal.ZERO) > 0 && promotionRule.getNeedMoney().compareTo(goodsTotalMoney) < 0) {
                    discountMoney = promotionRule.getDiscount();
                }
            }
            promotionMoney = promotionMoney.add(discountMoney);
            //计算每个商品满减多少
            for (CartGoods goods : goodsList) {
                goods.setPromotionMoney(discountMoney.multiply(goods.getTotalMoney()).divide(goodsTotalMoney, 2, BigDecimal.ROUND_HALF_UP));
            }
        }
        //促销的商品排序在前面
        Collections.reverse(mapList);
        //购物车金额
        cart.setMemberId(memberId);
        cart.setTotalMoney(totalMoney);
        cart.setPromotionMoney(promotionMoney);
        cart.setTradeMoney(totalMoney.subtract(promotionMoney));
        cart.setIsChooseAll(isChooseAll);
        String cartValue = vopCart.get("cart:" + memberId);
        cart.setProductIds(cartValue);
        retMap.put("cart", cart);
        retMap.put("cartGoodsList", mapList);

        HashSet<HashMap<String, Object>> couponMapList = new HashSet<>();
        for (Integer goodsId : goodsIdList){
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
                map.put("getCount", coupon.getGetCount());
                map.put("totalCount", coupon.getTotalCount());
                int hasCount = couponService.getHasCountByCouponId(memberId, coupon.getCouponId());
                //是否可以继续领取
                map.put("canGet", 1);
                if (coupon.getLimitNum() != 0 && coupon.getLimitNum() - hasCount <= 0) {
                    map.put("canGet", 0);
                }
                couponMapList.add(map);
            }
        }
        retMap.put("couponList", couponMapList);
        return retMap;
    }

    /**
     * 将List<CartItem>转换为List<CartGoods>
     * 生成具体order信息
     *
     * @param cartItem
     * @param memberId
     * @return
     * @throws Exception
     */
    @Override
    public HashMap<String, Object> generateOrderInfo(List<CartItem> cartItem, Integer memberId) {
        //计算购物车商品金额以及满减金额
        HashMap<String, Object> cartMap = this.changeCartItemToCartGoods(cartItem, memberId);
        HashMap<String, Object> retMap = new HashMap<>();
        //商品列表
        List<CartGoods> cartGoodsList = new ArrayList<>();
        List<HashMap<String, Object>> mapList = (List<HashMap<String, Object>>) cartMap.get("cartGoodsList");
        for (HashMap<String, Object> map : mapList) {
            cartGoodsList.addAll((List<CartGoods>) map.get("goodsList"));
        }

        //计算购物车商品总价
        BigDecimal totalMoney = ((Cart) cartMap.get("cart")).getTotalMoney();
        //满减金额
        BigDecimal promotionMoney = ((Cart) cartMap.get("cart")).getPromotionMoney();
        retMap.put("goodsList", cartGoodsList);
        retMap.put("baseInfo", orderSaveService.getBaseInfo(memberId, totalMoney, promotionMoney));
        //判断使用支付方式
        HashMap<String, Object> assetMap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        BigDecimal pointsCount = memberAssetsService.getPointsByMemberId(memberId);
        map.put("name", "积分");//积分
        map.put("value", NumericUtil.formatBigNum(pointsCount.toString()));//积分余额
        map.put("instruction", "积分抵用规则：100积分=1元");//积分使用说明
        assetMap.put("score", map);

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("name", "优惠券");//优惠券
        List<HashMap<String, Object>> couponList = couponService.getCanUseCouponList(memberId, totalMoney.subtract(promotionMoney), cartGoodsList);
        map1.put("value", couponList.size());//优惠券数量
        map1.put("instruction", "优惠券抵用规则：每笔订单仅可使用一张优惠券，使用后优惠券自动失效，订单取消或退款概不退回");//优惠券使用说明
        map1.put("couponList", couponList);
        assetMap.put("coupon", map1);
        retMap.put("payInfo", assetMap);

        return retMap;
    }

    /**
     * 改变购物车的选中状态
     *
     * @param memberId
     * @param productId
     * @param chooseType
     * @return
     * @throws Exception
     */
    @Override
    public HashMap<String, Object> changeChooseType(Integer memberId, String productId, String chooseType) {
        HashMap<String, Object> retMap = new HashMap<>();

        String cartItemKey = "cartItem:" + memberId + ":" + productId;
        String cartItemValue = vopCartItem.get(cartItemKey);

        String count = cartItemValue.split(",")[0];
        cartItemValue = count + "," + chooseType;

        vopCartItem.set(cartItemKey, cartItemValue);

        retMap.put("status", "y");
        return retMap;
    }

    /**
     * 改变购物车商品的购买数量
     *
     * @param memberId
     * @param productId
     * @param count
     * @return
     * @throws Exception
     */
    @Override
    public HashMap<String, Object> changeProductNum(Integer memberId, String productId, String count) {
        HashMap<String, Object> retMap = new HashMap<>();

        String cartItemKey = "cartItem:" + memberId + ":" + productId;
        String cartItemValue = vopCartItem.get(cartItemKey);

        String chooseType = cartItemValue.split(",")[1];
        cartItemValue = count + "," + chooseType;

        vopCartItem.set(cartItemKey, cartItemValue);

        retMap.put("status", "y");
        return retMap;
    }

    /**
     * 全选或全取消
     *
     * @param memberId
     * @param allType
     * @return
     * @throws Exception
     */
    @Override
    public HashMap<String, Object> chooseAll(Integer memberId, String allType) {
        HashMap<String, Object> retMap = new HashMap<>();

        String cartValue = vopCart.get("cart:" + memberId);

        if (cartValue != null && !"".equals(cartValue)) {//普通商品
            String prefix = "cartItem:" + memberId + ":";
            String[] productIdArr = cartValue.split(",");
            for (String productId : productIdArr) {
                String cartItemValue = vopCartItem.get(prefix + productId);
                String count = cartItemValue.split(",")[0];
                cartItemValue = count + "," + allType;
                vopCartItem.set(prefix + productId, cartItemValue);
            }
        }

        retMap.put("status", "y");
        return retMap;
    }

    /* 操作redis */

    /**
     * 保存购物车
     *
     * @param key       redis里的key值
     * @param cartValue redis里的value
     */
    @Override
    public void saveCart(String key, String cartValue) {
        vopCart.set(key, cartValue);
    }


    /**
     * 保存商品到购物车
     *
     * @param key
     * @param cartItemValue
     */
    @Override
    public void saveCartItem(String key, String cartItemValue) {
        vopCartItem.set(key, cartItemValue);
    }

    /**
     * @param key 根据key值到redis中获取对应的商品
     * @return
     */
    @Override
    public String getCartByKey(String key) {
        return vopCart.get(key);
    }

    /**
     * @param key 根据key值到redis中获取对应的购物车
     * @return
     */
    @Override
    public String getCartItemByKey(String key) {
        return vopCartItem.get(key);
    }

    /**
     * s2是s1的子集 求出 不在s2中的id
     *
     * @param s1 超集
     * @param s2 子集
     * @return 补集
     */
    private Collection<String> disjunction(String s1, String s2) {
        List<String> l1 = Arrays.asList(StringUtils.split(s1, ','));
        List<String> l2 = Arrays.asList(StringUtils.split(s2, ','));
        return CollectionUtils.subtract(l1, l2);
    }
}
