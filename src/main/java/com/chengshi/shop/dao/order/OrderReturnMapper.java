package com.chengshi.shop.dao.order;

import com.chengshi.shop.model.order.OrderReturn;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderReturnMapper {
    int deleteByPrimaryKey(Integer orderReturnId);

    int insert(OrderReturn record);

    int insertSelective(OrderReturn record);

    OrderReturn selectByPrimaryKey(Integer orderReturnId);

    int updateByPrimaryKeySelective(OrderReturn record);

    int updateByPrimaryKey(OrderReturn record);
}