package com.chengshi.shop.service.order;


import com.chengshi.shop.model.order.Order;
import com.chengshi.shop.model.order.OrderHistory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单相关接口
 * @author xuxinlong
 * @version 2017年07月24日
 */
public interface OrderService {
    /**
     * 获取订单数据
     */
    List<Order> getOrderList(HashMap<String, Object> map);


    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    Order getOrderByOrderId(Integer orderId);

    /**
     * 关闭订单
     * @param order
     * @param opName
     * @param comments
     */
    void closeOrder(Order order, String opName, String comments);


    /**
     * 保存订单历史
     * @param order
     * @param content
     */
    void saveOrderHistory(Order order, String opName, String content);

    /**
     * 根据订单编号查询订单
     * @param orderNum
     * @return
     */
    Order getOrderByOrderNum(String orderNum);


    /**
     * 更新订单
     * @param order
     */
    void updateByPrimaryKeySelective(Order order);


    /**
     * 查询用户拥有的订单
     * @param memberId
     * @return
     */
    List<Order> getListByMemberId(Integer memberId);

    /**
     * 查询用户拥有的订单
     * @param memberId
     * @return
     */
    List<Order> getListByMemberId(Integer memberId, Byte status);

    /**
     * 查询订单历史记录
     * @param orderId
     * @return
     */
    List<OrderHistory> getOrderHistoryListByOrderId(Integer orderId);

    /**
     * 确认收货
     * @param orderId
     * @param opName
     */
    void saveReceipt(Integer orderId, String opName);

    /**
     * 生成订单唯一编号
     *
     * @return
     */
    String getOrderNum();

    /**
     * 查询过期未付款的订单
     * @return
     */
    List<Order> getOverdueOrder();

    /**
     * 商家开始发货
     * @param order
     */
    void saveOrderDelivery(Order order);

    /**
     * 查询订单赠品列表
     * @param orderId
     * @return
     */
    List<String> getOrderGiftList(Integer orderId);

    /**
     * 获取可以自动确认收货的订单
     * @return
     */
    List<Order> getCanReceiptOrder();
}
