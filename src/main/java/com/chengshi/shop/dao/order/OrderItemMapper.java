package com.chengshi.shop.dao.order;

import com.chengshi.shop.model.order.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer orderItemId);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer orderItemId);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    /**
     * 查询订单下所有子订单
     * @param orderId
     * @return
     */
    List<OrderItem> getListByOrderId(Integer orderId);

    /**
     * 查询用户可退换货的子订单
     * @param memberId
     * @return
     */
    List<OrderItem> getCanReturnOrderItem(Integer memberId);
}