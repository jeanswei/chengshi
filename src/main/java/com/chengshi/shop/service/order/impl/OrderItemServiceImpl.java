package com.chengshi.shop.service.order.impl;

import com.chengshi.shop.dao.order.OrderItemMapper;
import com.chengshi.shop.model.order.OrderItem;
import com.chengshi.shop.service.order.OrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 子订单相关接口
 * @author xuxinlong
 * @version 2017年07月25日
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Resource
    private OrderItemMapper orderItemMapper;

    /**
     * 查询子订单详情
     *
     * @param orderItemId
     * @return
     */
    @Override
    public OrderItem getOrderItemById(Integer orderItemId) {
        return orderItemMapper.selectByPrimaryKey(orderItemId);
    }

    /**
     * 查询用户可退换货的子订单
     *
     * @param memberId
     * @return
     */
    @Override
    public List<OrderItem> getCanReturnOrderItem(Integer memberId) {
        return orderItemMapper.getCanReturnOrderItem(memberId);
    }

    /**
     * 查询订单下所有订单明细
     *
     * @param orderId
     * @return
     */
    @Override
    public List<OrderItem> getListByOrderId(Integer orderId) {
        return orderItemMapper.getListByOrderId(orderId);
    }

    /**
     * 保存子订单
     *
     * @param orderItem
     */
    @Override
    public void saveOrderItem(OrderItem orderItem) {
        if (orderItem.getOrderItemId()!=null){
            orderItemMapper.updateByPrimaryKeySelective(orderItem);
        } else {
            orderItemMapper.insertSelective(orderItem);
        }
    }


}
