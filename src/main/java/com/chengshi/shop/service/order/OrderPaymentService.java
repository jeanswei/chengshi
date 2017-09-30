package com.chengshi.shop.service.order;


import com.chengshi.shop.model.order.Order;
import com.chengshi.shop.model.order.OrderPayment;

import java.util.HashMap;
import java.util.List;

/**
 * 订单支付明细相关接口
 * @author xuxinlong
 * @version 2017年07月24日
 */
public interface OrderPaymentService {

    /**
     * 支付完成保存订单支付明细
     * @param orderPayment
     * @param order
     */
    void saveOrderPayment(OrderPayment orderPayment, Order order);

    /**
     * 订单支付方式
     * @param orderId
     * @return
     */
    List<HashMap<String, Object>> orderPayment(Integer orderId);

}
