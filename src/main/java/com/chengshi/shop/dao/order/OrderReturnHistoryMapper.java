package com.chengshi.shop.dao.order;

import com.chengshi.shop.model.order.OrderReturnHistory;

public interface OrderReturnHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderReturnHistory record);

    int insertSelective(OrderReturnHistory record);

    OrderReturnHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderReturnHistory record);

    int updateByPrimaryKey(OrderReturnHistory record);
}