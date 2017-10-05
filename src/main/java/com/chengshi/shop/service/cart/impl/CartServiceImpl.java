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
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${img_url}")
    private String IMG_URL;

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
    public int getCartNumByMemberId(Integer memberId) {
        String cartValue = vopCart.get("cart:" + memberId);
        if (cartValue != null) {
            return cartValue.split(",").length;
        }
        return 0;
    }

    /**
     * 根据会员获取购物车列表
     *
     * @param memberId
     * @return
     */
    @Override
    public List<CartItem> getCartItemsByMember(Integer memberId) {
        List<CartItem> cartItemList = new ArrayList<>();
        String cartValue = vopCart.get("cart:" + memberId);

        if (cartValue != null && !"".equals(cartValue)) {
            String prefix = "cartItem:" + memberId + ":";
            for (String productId : cartValue.split(",")) {
                String cartItemValue = vopCartItem.get(prefix + productId);
                String count = "";
                String isChoose = "";
                if (cartItemValue != null && !cartItemValue.equals("")) {
                    count = cartItemValue.split(",")[0];
                    isChoose = cartItemValue.split(",")[1];
                }
                CartItem cartItem = new CartItem();
                cartItem.setMemberId(memberId);
                cartItem.setProductId(Integer.parseInt(productId));
                if (count != null && !count.equals("")) {
                    cartItem.setProductNum(Integer.parseInt(count));
                } else {
                    cartItem.setProductNum(0);
                }
                if (isChoose != null && !isChoose.equals("")) {
                    cartItem.setIsChoose(Boolean.valueOf(isChoose));
                } else {
                    cartItem.setIsChoose(true);
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
     * @param productId
     * @param productNum
     * @return
     */
    @Override
    public HashMap<String, Object> addItemToCart(Integer memberId, Integer productId, Integer productNum) {
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("status", "y");
        String cartKey = "cart:" + memberId;

        String cartItemKey = "cartItem:" + memberId + ":";
        String cartValue = vopCart.get(cartKey);

        // 如果还不存在购物车 则新建个
        if (cartValue == null) {
            cartValue = productId.toString();
            //对购物车对应数据的设置
            vopCart.set(cartKey, cartValue);
            //将添加的商品加入购物车
            String cartItemValue = productNum + "," + true;
            cartItemKey = cartItemKey + productId;
            vopCartItem.set(cartItemKey, cartItemValue);
        } else {
            cartItemKey = cartItemKey + productId;
            String cartItemValue = vopCartItem.get(cartItemKey);
            // 购物车中还没有对应的商品 将商品加入购物车 并将商品的id追加到购物车productIds中
            if (cartItemValue == null) {
                cartItemValue = productNum + "," + true;
                vopCartItem.set(cartItemKey, cartItemValue);
                if (cartValue.equals(""))
                    cartValue = productId.toString();
                else
                    cartValue = cartValue + "," + productId;
                vopCart.set(cartKey, cartValue);
            } else {
                String count = cartItemValue.split(",")[0];
                String isChoose = cartItemValue.split(",")[1];
                // 购物车中有对应的商品，直接修改数量
                Integer temp = Integer.parseInt(count);
                temp += productNum;
                cartItemValue = temp.toString() + "," + isChoose;
                vopCartItem.set(cartItemKey, cartItemValue);
            }
        }
        retMap.put("cartNum", vopCart.get(cartKey).split(",").length);
        return retMap;
    }


    /**
     * 根据memberId 和 传入的货品的id 批量删除购物车中的货品
     *
     * @param memberId   memberId 会员Id
     * @param productIds
     */
    @Override
    public void batchDelInCart(Integer memberId, String productIds) {
        String cartkey = "cart:" + memberId;
        String prefix = "cartItem:" + memberId + ":";
        List<String> keys = new ArrayList<>();
        // 批量生产keys 在商品前加上 cartItem:memberId:productId
        for (String productId : productIds.split(",")) {
            keys.add(prefix + productId);
        }
        // 批量删除商品
        stringRedisTemplate.delete(keys);

        String cartValue = vopCart.get(cartkey);
        Collection disjunction = disjunction(cartValue, productIds);
        StringBuilder sb = new StringBuilder();
        for (Object aDisjunction : disjunction) {
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
     * 根据memberId清空对应的购物车商品
     *
     * @param memberId
     */
    @Override
    public void emptyCartGoods(Integer memberId) {
        String key = "cart:" + memberId;
        String cartValue = vopCart.get(key);
        // 删除购物车里的所有商品
        batchDelInCart(memberId, cartValue);
        stringRedisTemplate.delete(key);
    }

    /**
     * 将List<CartItem>装换为一个HashMap，
     * HashMap包括cartGoodsList和Cart，直接作为返回结果。
     *
     * @param cartItem
     */
    @Override
    public HashMap<String, Object> changeCartItemToCartGoods(List<CartItem> cartItem, Integer memberId) {
        HashMap<String, Object> retMap = new HashMap<>();
        Cart cart = new Cart();
        if (cartItem == null || cartItem.isEmpty()) {
            cart.setMemberId(memberId);
            retMap.put("cartGoodsList", new ArrayList<CartGoods>());
            retMap.put("cart", cart);
            return retMap;// 如果还没有购物车 请买家去购物 添加购物车
        }
        //计算购物车商品总价
        BigDecimal totalMoney = BigDecimal.ZERO;
        //优惠金额
        BigDecimal promotionMoney = BigDecimal.ZERO;

        Boolean isChooseAll = true;
        //商品按活动分类
        HashMap<Integer, HashMap<String, Object>> promotionMap = new HashMap<>();
        //遍历cartItem
        HashSet<Integer> goodsIdList = new HashSet<>();
        for (CartItem cItem : cartItem) {
            CartGoods cGoods = new CartGoods();
            GoodsProduct product = goodsProductMapper.selectByPrimaryKey(cItem.getProductId());
            Goods goods = goodsMapper.selectByPrimaryKey(product.getGoodsId());
            if (goods.getIsDelete() || product.getIsDelete()) {
                cGoods.setIsValid(false);
                cGoods.setNoValReason("商品已失效");
                cGoods.setIsChoose(false);
            } else if (!goods.getIsOnSale()) {
                cGoods.setIsValid(false);
                cGoods.setNoValReason("商品已下架");
                cGoods.setIsChoose(false);
            } else if (cItem.getProductNum() > product.getStore()) {
                cGoods.setIsValid(false);
                cGoods.setNoValReason("库存不足");
                cGoods.setIsChoose(false);
            } else {
                cGoods.setIsValid(true);
                cGoods.setIsChoose(cItem.getIsChoose());
            }
            cGoods.setGoodsId(goods.getGoodsId());
            cGoods.setProductId(product.getProductId());
            cGoods.setGoodsName(goods.getGoodsName());
            cGoods.setGoodsImage(IMG_URL + goods.getGoodsImg());
            cGoods.setMarktPrice(product.getMarktPrice());
            cGoods.setPrice(product.getPrice());
            cGoods.setProductNum(cItem.getProductNum());
            cGoods.setTotalMoney(cGoods.getPrice().multiply(new BigDecimal(cGoods.getProductNum())));
            cGoods.setStore(product.getStore());
            cGoods.setSpecView(product.getSpecView());

            //无效的商品无法勾选
            if (cItem.getIsChoose() && !cGoods.getIsValid()) {
                String cartItemKey = "cartItem:" + memberId + ":" + cItem.getProductId();
                String cartItemValue = "" + cItem.getProductNum() + "," + false;
                vopCartItem.set(cartItemKey, cartItemValue);
                cGoods.setIsChoose(false);
            }
            //计算勾选商品的金额
            if (cGoods.getIsValid()) {
                totalMoney = totalMoney.add(cGoods.getTotalMoney());
            } else {
                isChooseAll = false;
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
        retMap.put("cart", cart);
        retMap.put("cartGoodsList", mapList);

        HashSet<HashMap<String, Object>> couponMapList = new HashSet<>();
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
                map.put("getCount", coupon.getGetCount());
                map.put("totalCount", coupon.getTotalCount());
                int hasCount = couponService.getHasCountByCouponId(memberId, coupon.getCouponId());
                //是否可以继续领取
                map.put("canGet", true);
                if (coupon.getLimitNum() != 0 && coupon.getLimitNum() - hasCount <= 0) {
                    map.put("canGet", false);
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
        BigDecimal pointsCount = memberAssetsService.getMemberPointsByMemberId(memberId);
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
     * @return
     */
    @Override
    public void changeChoose(Integer memberId, Integer productId) {
        String cartItemKey = "cartItem:" + memberId + ":" + productId;
        String cartItemValue = vopCartItem.get(cartItemKey);

        String count = cartItemValue.split(",")[0];
        String isChoose = cartItemValue.split(",")[1];
        if (Boolean.valueOf(isChoose)) {
            cartItemValue = count + "," + false;
        } else {
            cartItemValue = count + "," + true;
        }
        vopCartItem.set(cartItemKey, cartItemValue);
    }

    /**
     * 改变购物车商品的购买数量
     *
     * @param memberId
     * @param productId
     * @param productNum
     * @return
     */
    @Override
    public void changeQuantity(Integer memberId, Integer productId, Integer productNum) {
        String cartItemKey = "cartItem:" + memberId + ":" + productId;
        String cartItemValue = vopCartItem.get(cartItemKey);

        String isChoose = cartItemValue.split(",")[1];
        cartItemValue = productNum + "," + isChoose;

        vopCartItem.set(cartItemKey, cartItemValue);
    }

    /**
     * 全选或全取消
     *
     * @param memberId
     * @param allType
     * @return
     */
    @Override
    public void chooseAll(Integer memberId, Boolean allType) {
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
    }

    /**
     * s2是s1的子集 求出 不在s2中的id
     *
     * @param s1 超集
     * @param s2 子集
     * @return 补集
     */
    private Collection disjunction(String s1, String s2) {
        List<String> l1 = Arrays.asList(StringUtils.split(s1, ','));
        List<String> l2 = Arrays.asList(StringUtils.split(s2, ','));
        return CollectionUtils.subtract(l1, l2);
    }
}
