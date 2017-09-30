package com.chengshi.shop.service.order;


import com.chengshi.shop.model.order.OrderHistory;

import java.util.List;

/**
 * @author xuxinlong
 * @version 2017年07月28日
 */
public interface OrderHistoryService {

    /**
     * 获取订单历史记录
     * @param orderId
     * @return
     */
    List<OrderHistory> getListByOrderId(Integer orderId);
}
