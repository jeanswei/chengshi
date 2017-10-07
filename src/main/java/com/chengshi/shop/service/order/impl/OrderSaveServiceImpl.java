package com.chengshi.shop.service.order.impl;

import com.chengshi.shop.dao.goods.GoodsMapper;
import com.chengshi.shop.dao.goods.GoodsProductMapper;
import com.chengshi.shop.dao.member.*;
import com.chengshi.shop.dao.order.*;
import com.chengshi.shop.model.coupon.Coupon;
import com.chengshi.shop.model.goods.Goods;
import com.chengshi.shop.model.goods.GoodsProduct;
import com.chengshi.shop.model.member.Member;
import com.chengshi.shop.model.member.MemberAccountLog;
import com.chengshi.shop.model.member.MemberAddress;
import com.chengshi.shop.model.member.MemberCoupon;
import com.chengshi.shop.model.order.*;
import com.chengshi.shop.model.promotion.Promotion;
import com.chengshi.shop.model.promotion.PromotionRule;
import com.chengshi.shop.service.cart.CartService;
import com.chengshi.shop.service.coupon.CouponService;
import com.chengshi.shop.service.order.OrderSaveService;
import com.chengshi.shop.service.order.OrderService;
import com.chengshi.shop.service.promotion.PromotionService;
import com.chengshi.shop.service.system.ShopConfigService;
import com.chengshi.shop.util.DateFormatUtil;
import com.chengshi.shop.util.EnumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 订单保存相关
 *
 * @author xuxinlong
 * @version 2017年07月25日
 */
@Service
public class OrderSaveServiceImpl implements OrderSaveService {
    @Resource
    private OrderService orderService;
    @Resource
    private CartService cartService;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private GoodsProductMapper productMapper;
    @Resource
    private OrderHistoryMapper orderHistoryMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private MemberAddressMapper memberAddressMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private OrderPaymentMapper orderPaymentMapper;
    @Resource
    private MemberCouponMapper memberCouponMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private PromotionService promotionService;
    @Resource
    private OrderCouponMapper orderCouponMapper;
    @Resource
    private OrderGiftMapper orderGiftMapper;
    @Resource
    private CouponService couponService;
    @Resource
    private ShopConfigService shopConfigService;
    @Resource
    private MemberAccountLogMapper memberAccountLogMapper;
    @Resource
    private MemberRankMapper memberRankMapper;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 保存订单
     *
     * @param orderInputBean
     * @return
     */
    @Transactional
    @Override
    public HashMap<String, Object> saveOrder(OrderInputBean orderInputBean) {
        logger.info("-------保存订单接口开始------" + orderInputBean.getProductIdAndNum());
        //1.检查商品状态
        HashMap<String, Object> map = checkNormalGoodsByOrderInputBean(orderInputBean);
        if (map.get("status").equals("n")) {
            map.put("errorCode", "n");
            map.put("errorText", map.get("errorText"));
            return map;
        }
        //2.计算订单金额相关
        HashMap<String, Object> retMap = countOrder(orderInputBean);
        BigDecimal totalMoney = BigDecimal.valueOf((Double) retMap.get("totalMoney"));//订单总金额
        BigDecimal promotionMoney = BigDecimal.valueOf((Double) retMap.get("promotionMoney"));//促销满减金额
        BigDecimal couponMoney = BigDecimal.valueOf((Double) retMap.get("couponMoney"));//优惠券金额
        BigDecimal discountMoney = BigDecimal.valueOf((Double) retMap.get("discountMoney"));//会员折扣金额
        BigDecimal pointsMoney = BigDecimal.valueOf((Double) retMap.get("pointsMoney"));//积分抵扣金额
        BigDecimal postage = BigDecimal.valueOf((Double) retMap.get("postage"));//邮费金额
        BigDecimal tradeMoney = BigDecimal.valueOf((Double) retMap.get("tradeMoney"));//还需付金额
        List<Integer> couponIdList = (List<Integer>) retMap.get("couponIdList");//赠送优惠券
        List<String> giftList = (List<String>) retMap.get("giftList");//赠品

        Member member = memberMapper.selectByPrimaryKey(orderInputBean.getMemberId());
        //用户积分账户
        if (member.getMemberPoints().compareTo(BigDecimal.ZERO) <= 0) {
            orderInputBean.setIsUsePoints(false);
        }

        //删除购物车和锁定库存
        List<OutGoods> outGoodsList = new ArrayList<>();
        for (String id_num : orderInputBean.getProductIdAndNum().split(",")) {
            String[] id_numArray = id_num.split("-");
            Integer productId = Integer.parseInt(id_numArray[0]);
            Integer productNum = Integer.parseInt(id_numArray[1]);
            //根据memberId 和 传入的货品的id 批量删除购物车中的货品
            cartService.batchDelInCart(orderInputBean.getMemberId(), productId.toString());
            //减去库存
            productMapper.subProductStore(productId, productNum);

            GoodsProduct product = productMapper.selectByPrimaryKey(productId);
            Goods goods = goodsMapper.selectByPrimaryKey(product.getGoodsId());
            OutGoods outGoods = new OutGoods();
            outGoods.setProductId(productId);
            outGoods.setGoodsId(product.getGoodsId());
            outGoods.setGoodsName(goods.getGoodsName());
            outGoods.setSpecView(product.getSpecView());
            outGoods.setProductNum(productNum);
            outGoods.setMarktPrice(product.getMarktPrice());
            outGoods.setPrice(product.getPrice());
            outGoods.setTradeAmount(outGoods.getPrice().multiply(new BigDecimal(productNum)).setScale(2, BigDecimal.ROUND_HALF_UP));
            outGoodsList.add(outGoods);
        }

        //保存订单主表
        Order order = new Order(); //订单
        order.setMemberId(orderInputBean.getMemberId());//设置会员
        order.setStatus(EnumUtil.ORDER_STATUS.待付款.getValue().byteValue());
        order.setTotalAmount(totalMoney);//商品总额
        order.setCreateTime(new Date());//创建时间
        order.setAutoCloseTime(new Date(order.getCreateTime().getTime() + Integer.valueOf(shopConfigService.getConfig("ORDER_AUTO_CLOSE_TIME")) * 60 * 1000));//订单失效时间
        order.setMarkText(orderInputBean.getMarkText());
        order.setOrderNum(orderService.getOrderNum());//订单编号 前台展示给用户
        MemberAddress memberAddress = memberAddressMapper.selectByPrimaryKey(orderInputBean.getAddressId());
        order.setConsignee(memberAddress.getName());
        order.setMobile(memberAddress.getMobile());
        order.setAddress(memberAddress.getAddress());
        orderMapper.insertSelective(order);

        //保存订单赠送优惠券
        for (Integer couponId : couponIdList) {
            OrderCoupon orderCoupon = new OrderCoupon();
            orderCoupon.setCouponId(couponId);
            orderCoupon.setOrderId(order.getOrderId());
            orderCouponMapper.insertSelective(orderCoupon);
        }
        //保存订单赠品
        for (String gift : giftList) {
            OrderGift orderGift = new OrderGift();
            orderGift.setOrderId(order.getOrderId());
            orderGift.setGift(gift);
            orderGiftMapper.insertSelective(orderGift);
        }
        //保存订单明细
        for (OutGoods outGoods : outGoodsList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setGoodsId(outGoods.getGoodsId());
            orderItem.setProductId(outGoods.getProductId());
            orderItem.setGoodsName(outGoods.getGoodsName());
            orderItem.setSpecView(outGoods.getSpecView());
            orderItem.setPrice(outGoods.getPrice());
            orderItem.setProductNum(outGoods.getProductNum());
            orderItem.setTotalAmount(outGoods.getTradeAmount());
            orderItemMapper.insertSelective(orderItem);
        }
        //保存订单历史
        orderService.saveOrderHistory(order, "客户", "生成订单");

        /**********************************计算活动优惠抵扣***********************************/
        if (promotionMoney.compareTo(BigDecimal.ZERO) > 0) {
            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setOrderId(order.getOrderId());
            orderPayment.setMoney(promotionMoney);
            orderPayment.setCreateTime(new Date());
            orderPayment.setPaymentMethod(EnumUtil.PAYMENT_METHOD.活动优惠.getValue().byteValue());
            orderPayment.setContent("活动优惠抵扣（订单：" + order.getOrderNum() + "）");
            orderPaymentMapper.insertSelective(orderPayment);
        }
        /**********************************计算优惠券抵扣************************************/
        if (orderInputBean.getMemberCouponId() != null && couponMoney.compareTo(BigDecimal.ZERO) > 0) {
            MemberCoupon memberCoupon = memberCouponMapper.selectByPrimaryKey(orderInputBean.getMemberCouponId());
            memberCoupon.setStatus((byte) 2);
            memberCouponMapper.updateByPrimaryKeySelective(memberCoupon);
            //生成优惠券支付明细
            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setOrderId(order.getOrderId());
            orderPayment.setMoney(couponMoney);
            orderPayment.setCreateTime(new Date());
            orderPayment.setPaymentMethod(EnumUtil.PAYMENT_METHOD.优惠券.getValue().byteValue());
            orderPayment.setContent("优惠券抵扣（订单：" + order.getOrderNum() + "）");
            orderPayment.setTradeNo(orderInputBean.getMemberCouponId().toString());
            orderPaymentMapper.insertSelective(orderPayment);
        }
        /**********************************计算会员折扣********************************************************/
        if (discountMoney.compareTo(BigDecimal.ZERO) > 0) {
            //生成会员折扣支付明细
            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setOrderId(order.getOrderId());
            orderPayment.setMoney(discountMoney);
            orderPayment.setCreateTime(new Date());
            orderPayment.setPaymentMethod(EnumUtil.PAYMENT_METHOD.会员折扣.getValue().byteValue());
            orderPayment.setContent("会员折扣抵扣（订单：" + order.getOrderNum() + "）");
            orderPaymentMapper.insertSelective(orderPayment);
        }
        /**********************************计算积分抵扣*************************************/
        if (orderInputBean.getIsUsePoints() && pointsMoney.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal points = pointsMoney.multiply(new BigDecimal(shopConfigService.getConfig("POINTS_RATIO")));
            //减少用户积分
            memberMapper.subMemberPoints(orderInputBean.getMemberId(), points);
            //积分使用记录
            MemberAccountLog memberAccountLog = new MemberAccountLog();
            memberAccountLog.setMemberId(orderInputBean.getMemberId());
            memberAccountLog.setAccountType(EnumUtil.ACCOUNT_TYPE.会员积分.getValue().byteValue());
            memberAccountLog.setCreateTime(new Date());
            memberAccountLog.setContent("积分抵扣（订单：" + order.getOrderNum() + "）");
            memberAccountLog.setNumber(points);
            memberAccountLog.setFromType(EnumUtil.ACCOUNT_FROM_TYPE.商城订单.getValue().byteValue());
            memberAccountLogMapper.insertSelective(memberAccountLog);
            //添加支付明细表中，抵扣积分的部分
            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setOrderId(order.getOrderId());
            orderPayment.setMoney(pointsMoney);
            orderPayment.setCreateTime(new Date());
            orderPayment.setPaymentMethod(EnumUtil.PAYMENT_METHOD.积分.getValue().byteValue());
            orderPayment.setContent("积分抵扣（订单：" + order.getOrderNum() + "）");
            orderPaymentMapper.insertSelective(orderPayment);
        }

        //已抵扣金额
        BigDecimal payed = promotionMoney.add(couponMoney).add(discountMoney).add(pointsMoney);
        order.setPayed(payed);
        order.setFare(postage);
        orderMapper.updateByPrimaryKeySelective(order);

        //finalMoney为0的时候，改变订单的各种状态
        if (tradeMoney.compareTo(BigDecimal.ZERO) == 0) {
            handlePayedOrder(order);
        }

        map.put("tradeMoneyS", tradeMoney);
        map.put("tradeMoney", tradeMoney.doubleValue());
        map.put("errorCode", "y");
        map.put("errorText", "订单生成成功!");
        map.put("orderNum", order.getOrderNum());
        map.put("orderId", order.getOrderId());
        map.put("payTip", "请于" + DateFormatUtil.transSecond(Integer.valueOf(shopConfigService.getConfig("ORDER_AUTO_CLOSE_TIME")) * 60) + "内完成支付，否则订单将被取消。");
        if (tradeMoney.compareTo(BigDecimal.ZERO) == 0) {
            map.put("payTip", "感谢您在" + shopConfigService.getConfig("SHOP_NAME") + "购物，欢迎下次光临。");
        }
        logger.info("-------保存订单接口结束---订单号---" + order.getOrderNum());
        return map;
    }

    /**
     * 处理支付完成的订单
     *
     * @param order
     */
    @Override
    public void handlePayedOrder(Order order) {
        order.setStatus(EnumUtil.ORDER_STATUS.待发货.getValue().byteValue());
        orderMapper.updateByPrimaryKeySelective(order);
        //插入订单历史记录
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setCreateTime(new Date());
        orderHistory.setOrderId(order.getOrderId());
        orderHistory.setOpName("客户");
        orderHistory.setContent("支付完成，等待处理订单");
        orderHistoryMapper.insertSelective(orderHistory);
        // 销量数量统计
        List<OrderItem> orderItemList = orderItemMapper.getListByOrderId(order.getOrderId());
        for (OrderItem orderItem : orderItemList) {
            goodsMapper.addSaleCount(orderItem.getGoodsId(), orderItem.getProductNum());
        }

        //赠送订单优惠券
        List<OrderCoupon> couponList = orderCouponMapper.getListByOrderId(order.getOrderId());
        for (OrderCoupon orderCoupon : couponList) {
            Coupon coupon = couponService.getCoupon(orderCoupon.getCouponId());
            HashMap<String, Object> resultMap = couponService.checkCouponCanGet(order.getMemberId(), coupon);
            if ("n".equals(resultMap.get("errorCode").toString())) {
                continue;
            }
            couponService.receiveCoupon(order.getMemberId(), coupon);
        }
    }

    /**
     * 订单金额计算
     *
     * @param orderInputBean
     * @return
     */
    @Override
    public HashMap<String, Object> countOrder(OrderInputBean orderInputBean) {
        HashMap<String, Object> map = new HashMap<>();
        //订单商品总金额
        BigDecimal totalMoney = BigDecimal.ZERO;
        //用户积分账户
        Member member = memberMapper.selectByPrimaryKey(orderInputBean.getMemberId());
        BigDecimal memberPoints = member.getMemberPoints();
        if (memberPoints.compareTo(BigDecimal.ZERO) <= 0) {
            orderInputBean.setIsUsePoints(false);
        }
        //每个促销活动包含商品的总金额
        HashMap<Integer, BigDecimal> promotionMap = new HashMap<>();
        /**********************************先算出订单的总金额*************************************/
        for (String id_num : orderInputBean.getProductIdAndNum().split(",")) {
            String productId = id_num.split("-")[0];
            String num = id_num.split("-")[1];
            GoodsProduct product = productMapper.selectByPrimaryKey(Integer.valueOf(productId));
            BigDecimal goodsMoney = product.getPrice().multiply(new BigDecimal(num));
            totalMoney = totalMoney.add(goodsMoney);
            //将同一活动下商品归类
            Promotion promotion = promotionService.getPromotionByGoodsId(product.getGoodsId());
            if (promotion != null) {
                //如果已存在则插入，不存在就新建
                if (promotionMap.get(promotion.getPromotionId()) != null) {
                    promotionMap.put(promotion.getPromotionId(), promotionMap.get(promotion.getPromotionId()).add(goodsMoney));
                } else {
                    promotionMap.put(promotion.getPromotionId(), goodsMoney);
                }
            } else {
                if (promotionMap.get(0) != null) {
                    promotionMap.put(0, promotionMap.get(0).add(goodsMoney));
                } else {
                    promotionMap.put(0, goodsMoney);
                }
            }
        }
        /**********************************计算邮费****************************************/
        BigDecimal postage = new BigDecimal(shopConfigService.getConfig("POSTAGE"));
        if (totalMoney.compareTo(new BigDecimal(shopConfigService.getConfig("PACKAGE_POSTAGE"))) >= 0) {
            postage = BigDecimal.ZERO;
        }
        /**********************************计算满减抵扣************************************/
        List<Integer> couponIdList = new ArrayList<>();
        List<String> giftList = new ArrayList<>();
        BigDecimal promotionMoney = BigDecimal.ZERO;
        for (Object o : promotionMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            BigDecimal goodsMoney = (BigDecimal) entry.getValue();
            List<PromotionRule> ruleList = promotionService.getPromotionRuleList((Integer) entry.getKey());
            BigDecimal discountMoney = BigDecimal.ZERO;
            for (PromotionRule promotionRule : ruleList) {
                //计算满减金额
                if (promotionRule.getNeedMoney().compareTo(goodsMoney) < 0) {
                    //满减
                    if (promotionRule.getDiscount().compareTo(BigDecimal.ZERO) > 0) {
                        discountMoney = promotionRule.getDiscount();
                    }
                    //优惠券
                    if (promotionRule.getGiveCoupon() > 0) {
                        couponIdList.add(promotionRule.getGiveCoupon());
                    }
                    //赠品
                    if (promotionRule.getIsGiveGift()) {
                        giftList.add(promotionRule.getGift());
                    }
                }
            }
            promotionMoney = promotionMoney.add(discountMoney);
        }
        /**********************************计算优惠券抵扣************************************/
        BigDecimal couponMoney = BigDecimal.ZERO;//优惠券所能优惠的总额
        if (orderInputBean.getMemberCouponId() != null) {//优惠券金额
            MemberCoupon memberCoupon = memberCouponMapper.selectByPrimaryKey(orderInputBean.getMemberCouponId());
            if (memberCoupon != null) {
                couponMoney = (totalMoney.subtract(promotionMoney)).compareTo(memberCoupon.getMoney()) > 0 ? memberCoupon.getMoney() : totalMoney.subtract(promotionMoney);
            }
        }
        /**********************************计算会员折扣**************************************/
        BigDecimal disAndPointsMoney = totalMoney.subtract(promotionMoney).subtract(couponMoney);
        BigDecimal discountMoney = BigDecimal.ZERO;//会员折扣金额
        if (orderInputBean.getMemberId() != null) {//会员折扣
            BigDecimal discount = memberRankMapper.getMemberDiscount(orderInputBean.getMemberId());
            if (discount != null) {
                discountMoney = disAndPointsMoney.multiply(discount).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
        }
        /**********************************计算积分抵扣*************************************/
        BigDecimal pointsCanUseMoney = disAndPointsMoney.subtract(discountMoney);//用于记录积分抵扣总额(用完优惠券，会员折扣)
        BigDecimal pointsMoney = BigDecimal.ZERO;//积分使用数量(单位：元)
        if (pointsCanUseMoney.compareTo(BigDecimal.ZERO) > 0) {//如果使用余额后，不够
            //积分比例
            BigDecimal pointsRatio = new BigDecimal(shopConfigService.getConfig("POINTS_RATIO"));
            if (orderInputBean.getIsUsePoints()) {//如果使用积分
                //如果抵扣超过需要抵扣的总额，取需要抵扣的总额，防止超出
                pointsMoney = (memberPoints.divide(pointsRatio, 2, BigDecimal.ROUND_HALF_UP)).compareTo(pointsCanUseMoney) > 0 ? pointsCanUseMoney : memberPoints.divide(pointsRatio, 2, BigDecimal.ROUND_HALF_UP);
            }
        }

        BigDecimal tradeMoney = totalMoney.add(postage).subtract(pointsMoney).subtract(couponMoney).subtract(discountMoney).subtract(promotionMoney);
        map.put("totalMoney", totalMoney);
        map.put("postage", postage);
        map.put("pointsMoney", pointsMoney);
        map.put("discountMoney", discountMoney);
        map.put("promotionMoney", promotionMoney);
        map.put("couponMoney", couponMoney);
        map.put("tradeMoney", tradeMoney);
        map.put("couponIdList", couponIdList);
        map.put("giftList", giftList);
        return map;
    }

    /**
     * 用户删除订单
     *
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public HashMap<String, Object> delOrder(Integer orderId) {
        HashMap<String, Object> retMap = new HashMap<>();
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order.getStatus().intValue() == EnumUtil.ORDER_STATUS.交易成功.getValue()
                || order.getStatus().intValue() == EnumUtil.ORDER_STATUS.交易关闭.getValue()) {
            //删除订单
            order.setIsDelete(true);
            orderMapper.updateByPrimaryKeySelective(order);
            orderService.saveOrderHistory(order, "客户", "用户删除订单");
            retMap.put("errorCode", "y");
            retMap.put("errorText", "删除订单成功");
            return retMap;
        } else {
            retMap.put("errorCode", "n");
            retMap.put("errorText", "该订单正在处理，不能删除");
            return retMap;
        }
    }


    /**
     * 余额支付订单
     *
     * @param order
     * @return
     */
    @Override
    @Transactional
    public HashMap<String, Object> balancePay(Order order) {
        HashMap<String, Object> retMap = new HashMap<>();
        Member member = memberMapper.selectByPrimaryKey(order.getMemberId());
        BigDecimal memberBalance = member.getMemberBalance();
        BigDecimal totalMoney = order.getTotalAmount().add(order.getFare()).subtract(order.getPayed());
        if (memberBalance.compareTo(totalMoney) < 0) {
            retMap.put("errorCode", "n");
            retMap.put("errorText", "余额不足支付失败");
            return retMap;
        }
        order.setPayTime(new Date());
        //减少余额金额
        memberMapper.subMemberBalance(order.getMemberId(), totalMoney);
        //余额使用记录
        MemberAccountLog memberAccountLog = new MemberAccountLog();
        memberAccountLog.setMemberId(order.getMemberId());
        memberAccountLog.setAccountType(EnumUtil.ACCOUNT_TYPE.会员余额.getValue().byteValue());
        memberAccountLog.setCreateTime(new Date());
        memberAccountLog.setContent("余额抵扣（订单：" + order.getOrderNum() + "）");
        memberAccountLog.setNumber(totalMoney);
        memberAccountLog.setFromType(EnumUtil.ACCOUNT_FROM_TYPE.商城订单.getValue().byteValue());
        memberAccountLogMapper.insertSelective(memberAccountLog);
        // 添加支付明细表中，实际支付的部分
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setCreateTime(new Date());
        orderPayment.setOrderId(order.getOrderId());
        orderPayment.setMoney(totalMoney);
        orderPayment.setPaymentMethod(EnumUtil.PAYMENT_METHOD.余额.getValue().byteValue());
        orderPayment.setContent("余额支付（订单：" + order.getOrderNum() + "）");
        orderPaymentMapper.insertSelective(orderPayment);

        //支付完成处理订单
        this.handlePayedOrder(order);
        return retMap;
    }

    /**
     * 积分优惠金额及邮费等
     *
     * @param memberId
     * @param totalMoney
     * @param promotionMoney
     * @return
     */
    @Override
    public HashMap<String, Object> getBaseInfo(Integer memberId, BigDecimal totalMoney, BigDecimal promotionMoney) {
        HashMap<String, Object> retMap = new HashMap<>();
        BigDecimal postage = new BigDecimal(shopConfigService.getConfig("POSTAGE"));
        if (totalMoney.compareTo(new BigDecimal(shopConfigService.getConfig("PACKAGE_POSTAGE"))) >= 0) {
            postage = BigDecimal.ZERO;
        }

        retMap.put("totalMoney", totalMoney);//商品总额
        retMap.put("promotionMoney", promotionMoney);//满减金额
        //邮费
        retMap.put("postage", postage);
        BigDecimal discountMoney = BigDecimal.ZERO;
        BigDecimal discount = memberRankMapper.getMemberDiscount(memberId);
        if (discount != null) {
            discountMoney = (totalMoney.subtract(promotionMoney)).multiply(new BigDecimal(1).subtract(discount.multiply(new BigDecimal(0.01)))).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        //会员折扣金额
        retMap.put("discountMoney", discountMoney);
        //需付金额
        retMap.put("tradeMoney", totalMoney.add(postage).subtract(discountMoney).subtract(promotionMoney));
        return retMap;
    }

    /**
     * 普通商品立即购买之前检查商品的情况
     * 普通商品下架、库存不足
     *
     * @return
     * @throws Exception
     */
    private HashMap<String, Object> checkProductStatus(Integer productId, Integer buyNum) {
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("status", "y");
        //供应商货品
        GoodsProduct product = productMapper.selectByPrimaryKey(productId);
        Goods goods = goodsMapper.selectByPrimaryKey(product.getGoodsId());
        if (product.getIsDelete() || goods.getIsDelete() || !goods.getIsOnSale()) {
            retMap.put("status", "n");
            retMap.put("errorText", goods.getGoodsName() + "该商品已经下架");
        } else if (product.getStore() < buyNum || product.getStore() == 0) {
            retMap.put("status", "n");
            retMap.put("errorText", goods.getGoodsName() + "库存不足");
        }
        return retMap;
    }


    /**
     * 普通商品检查商品状态和商品库存
     *
     * @param orderInputBean
     * @return
     */
    private HashMap<String, Object> checkNormalGoodsByOrderInputBean(OrderInputBean orderInputBean) {
        HashMap<String, Object> retMap = new HashMap<>();
        for (String id_num : orderInputBean.getProductIdAndNum().split(",")) {
            String[] id_numArray = id_num.split("-");
            String productId = id_numArray[0];
            String num = id_numArray[1];
            retMap = checkProductStatus(Integer.parseInt(productId), Integer.parseInt(num));
            if (retMap.get("status").equals("n")) {
                retMap.put("status", "n");
                return retMap;
            }
        }
        retMap.put("status", "y");
        return retMap;
    }
}
