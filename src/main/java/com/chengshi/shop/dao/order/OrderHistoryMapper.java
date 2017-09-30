package com.chengshi.shop.dao.order;

import com.chengshi.shop.model.order.OrderHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderHistory record);

    int insertSelective(OrderHistory record);

    OrderHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderHistory record);

    int updateByPrimaryKey(OrderHistory record);

    /**
     * 查询订单历史记录
     * @param orderId
     * @return
     */
    List<OrderHistory> getListByOrderId(Integer orderId);
}