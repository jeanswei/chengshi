package com.chengshi.shop.service.order;


import com.chengshi.shop.model.order.Order;
import com.chengshi.shop.model.order.OrderInputBean;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * 订单保存相关
 * @author xuxinlong
 * @version 2017年07月25日
 */
public interface OrderSaveService {


    /**
     * 保存订单
     * @param orderInputBean
     * @return
     */
    HashMap<String, Object> saveOrder(OrderInputBean orderInputBean);

    /**
     * 用户删除订单
     * @param orderId
     * @return
     */
    HashMap<String, Object> delOrder(Integer orderId);

    /**
     * 订单金额计算
     * @param orderInputBean
     * @return
     */
    HashMap<String,Object> countOrder(OrderInputBean orderInputBean);

    /**
     * 处理完成的订单
     * @param order
     */
    void handlePayedOrder(Order order);

    /**
     * 余额支付订单
     * @param order
     * @return
     */
    HashMap<String, Object> balancePay(Order order);

    /**
     * 优惠金额及邮费等
     * @param memberId
     * @param totalMoney
     * @param promotionMoney
     * @return
     */
    HashMap<String,Object> getBaseInfo(Integer memberId, BigDecimal totalMoney, BigDecimal promotionMoney);

}
