package com.chengshi.shop.dao.order;

import com.chengshi.shop.model.order.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface OrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    /**
     * 根据订单号查询订单
     *
     * @param orderNum
     * @return
     */
    Order getOrderByOrderNum(String orderNum);

    /**
     * 查询用户拥有的订单
     *
     * @param memberId
     * @param status
     * @return
     */
    List<Order> getListByMemberId(@Param("memberId") Integer memberId, @Param("status") Byte status);

    /**
     * 获取订单列表数据
     * @param inMap
     * @return
     */
    List<Order> getOrderList(HashMap<String, Object> inMap);

    /**
     * 查询过期未付款的订单
     * @return
     */
    List<Order> getOverdueOrder();

    /**
     * 获取可以自动确认收货的订单
     * @return
     */
    List<Order> getCanReceiptOrder();
}