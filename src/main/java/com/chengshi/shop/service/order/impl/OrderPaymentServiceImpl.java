package com.chengshi.shop.service.order.impl;

import com.chengshi.shop.dao.order.OrderPaymentMapper;
import com.chengshi.shop.model.order.Order;
import com.chengshi.shop.model.order.OrderPayment;
import com.chengshi.shop.service.order.OrderPaymentService;
import com.chengshi.shop.service.order.OrderSaveService;
import com.chengshi.shop.util.EnumUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 订单支付相关接口
 *
 * @author xuxinlong
 * @version 2017年07月25日
 */
@Service
public class OrderPaymentServiceImpl implements OrderPaymentService {
    @Resource
    private OrderPaymentMapper orderPaymentMapper;
    @Resource
    private OrderSaveService orderSaveService;


    /**
     * 支付完成保存订单支付明细
     *
     * @param orderPayment
     * @param order
     */
    @Override
    @Transactional
    public void saveOrderPayment(OrderPayment orderPayment, Order order) {
        orderPayment.setCreateTime(new Date());
        orderPaymentMapper.insertSelective(orderPayment);
        // 处理支付完成的订单
        orderSaveService.handlePayedOrder(order);
    }

    /**
     * 订单支付方式
     *
     * @param orderId
     * @return
     */
    @Override
    public List<HashMap<String, Object>> orderPayment(Integer orderId) {
        List<HashMap<String, Object>> mapList = new ArrayList<>();

        //积分抵用金额
        BigDecimal payPoints = BigDecimal.ZERO;
        List<OrderPayment> orderPaymentList = orderPaymentMapper.getListByOrderId(orderId, EnumUtil.PAYMENT_METHOD.积分.getValue().byteValue());
        for (OrderPayment payments : orderPaymentList) {
            payPoints = payPoints.add(payments.getMoney());
        }
        //优惠券使用金额
        BigDecimal payCoupon = BigDecimal.ZERO;
        List<OrderPayment> orderPaymentList1 = orderPaymentMapper.getListByOrderId(orderId, EnumUtil.PAYMENT_METHOD.优惠券.getValue().byteValue());
        for (OrderPayment payments : orderPaymentList1) {
            payCoupon = payCoupon.add(payments.getMoney());
        }
        //折扣金额
        BigDecimal discount = BigDecimal.ZERO;
        List<OrderPayment> orderPaymentList2 = orderPaymentMapper.getListByOrderId(orderId, EnumUtil.PAYMENT_METHOD.会员折扣.getValue().byteValue());
        for (OrderPayment payments : orderPaymentList2) {
            discount = discount.add(payments.getMoney());
        }
        //活动优惠
        BigDecimal promotionMoney = BigDecimal.ZERO;
        List<OrderPayment> orderPaymentList3 = orderPaymentMapper.getListByOrderId(orderId, EnumUtil.PAYMENT_METHOD.活动优惠.getValue().byteValue());
        for (OrderPayment payments : orderPaymentList3) {
            promotionMoney = promotionMoney.add(payments.getMoney());
        }
        //余额
        BigDecimal balanceMoney = BigDecimal.ZERO;
        List<OrderPayment> orderPaymentList4 = orderPaymentMapper.getListByOrderId(orderId, EnumUtil.PAYMENT_METHOD.余额.getValue().byteValue());
        for (OrderPayment payments : orderPaymentList4) {
            balanceMoney = balanceMoney.add(payments.getMoney());
        }
        //微信支付
        BigDecimal wxPay = BigDecimal.ZERO;
        List<OrderPayment> orderPaymentList5 = orderPaymentMapper.getListByOrderId(orderId, EnumUtil.PAYMENT_METHOD.微信.getValue().byteValue());
        for (OrderPayment payments : orderPaymentList5) {
            wxPay = wxPay.add(payments.getMoney());
        }
        //支付宝支付
        BigDecimal aliPay = BigDecimal.ZERO;
        List<OrderPayment> orderPaymentList6 = orderPaymentMapper.getListByOrderId(orderId, EnumUtil.PAYMENT_METHOD.支付宝.getValue().byteValue());
        for (OrderPayment payments : orderPaymentList6) {
            aliPay = aliPay.add(payments.getMoney());
        }
        if (payPoints.compareTo(BigDecimal.ZERO) > 0) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", "积分");
            map.put("value", payPoints.toString());
            mapList.add(map);
        }
        if (payCoupon.compareTo(BigDecimal.ZERO) > 0) {
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("name", "优惠券");
            map1.put("value", payCoupon.toString());
            mapList.add(map1);
        }
        if (discount.compareTo(BigDecimal.ZERO) > 0) {
            HashMap<String, Object> map2 = new HashMap<>();
            map2.put("name", "会员折扣");
            map2.put("value", discount.toString());
            mapList.add(map2);
        }
        if (promotionMoney.compareTo(BigDecimal.ZERO) > 0) {
            HashMap<String, Object> map3 = new HashMap<>();
            map3.put("name", "活动优惠");
            map3.put("value", promotionMoney.toString());
            mapList.add(map3);
        }
        if (balanceMoney.compareTo(BigDecimal.ZERO) > 0) {
            HashMap<String, Object> map4 = new HashMap<>();
            map4.put("name", "余额");
            map4.put("value", balanceMoney.toString());
            mapList.add(map4);
        }
        if (wxPay.compareTo(BigDecimal.ZERO) > 0) {
            HashMap<String, Object> map5 = new HashMap<>();
            map5.put("name", "微信支付");
            map5.put("value", wxPay.toString());
            mapList.add(map5);
        }
        if (aliPay.compareTo(BigDecimal.ZERO) > 0) {
            HashMap<String, Object> map6 = new HashMap<>();
            map6.put("name", "支付宝支付");
            map6.put("value", aliPay.toString());
            mapList.add(map6);
        }
        return mapList;
    }

}
