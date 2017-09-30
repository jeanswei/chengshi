package com.chengshi.shop.dao.order;

import com.chengshi.shop.model.order.OrderGift;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderGiftMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderGift record);

    int insertSelective(OrderGift record);

    OrderGift selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderGift record);

    int updateByPrimaryKey(OrderGift record);

    /**
     * 查询订单拥有的赠品
     * @param orderId
     * @return
     */
    List<String> getGiftList(Integer orderId);
}