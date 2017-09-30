package com.chengshi.shop.service.order.impl;

import com.chengshi.shop.dao.order.OrderHistoryMapper;
import com.chengshi.shop.model.order.OrderHistory;
import com.chengshi.shop.service.order.OrderHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuxinlong
 * @version 2017年07月28日
 */
@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {
    @Resource
    private OrderHistoryMapper orderHistoryMapper;

    /**
     * 获取订单历史记录
     * @param orderId
     * @return
     */
    @Override
    public List<OrderHistory> getListByOrderId(Integer orderId) {
        return orderHistoryMapper.getListByOrderId(orderId);
    }
}
