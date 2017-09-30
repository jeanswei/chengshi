package com.chengshi.shop.service.order;


import com.chengshi.shop.model.order.OrderItem;

import java.util.List;

/**
 * 子订单相关接口
 * @author xuxinlong
 * @version 2017年07月25日
 */
public interface OrderItemService {

    /**
     * 查询子订单详情
     * @param orderItemId
     * @return
     */
    OrderItem getOrderItemById(Integer orderItemId);

    /**
     * 查询用户可退换货的子订单
     * @param memberId
     * @return
     */
    List<OrderItem> getCanReturnOrderItem(Integer memberId);

    /**
     * 查询订单下所有订单明细
     * @param orderId
     * @return
     */
    List<OrderItem> getListByOrderId(Integer orderId);

    /**
     * 保存子订单
     * @param orderItem
     */
    void saveOrderItem(OrderItem orderItem);


}
