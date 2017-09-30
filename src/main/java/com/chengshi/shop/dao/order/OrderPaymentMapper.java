package com.chengshi.shop.dao.order;

import com.chengshi.shop.model.order.OrderPayment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderPaymentMapper {
    int deleteByPrimaryKey(Integer orderPaymentId);

    int insert(OrderPayment record);

    int insertSelective(OrderPayment record);

    OrderPayment selectByPrimaryKey(Integer orderPaymentId);

    int updateByPrimaryKeySelective(OrderPayment record);

    int updateByPrimaryKey(OrderPayment record);

    /**
     * 根据支付类型查询订单所有数据
     * @param orderId
     * @param paymentMethod
     * @return
     */
    List<OrderPayment> getListByOrderId(@Param("orderId") Integer orderId, @Param("paymentMethod") Byte paymentMethod);
}