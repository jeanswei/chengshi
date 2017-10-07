package com.chengshi.shop.service.order.impl;

import com.chengshi.shop.dao.goods.GoodsProductMapper;
import com.chengshi.shop.dao.member.MemberAccountLogMapper;
import com.chengshi.shop.dao.member.MemberMapper;
import com.chengshi.shop.dao.order.*;
import com.chengshi.shop.dao.system.ExpressMapper;
import com.chengshi.shop.dao.system.SequenceMapper;
import com.chengshi.shop.dao.system.ShopConfigMapper;
import com.chengshi.shop.model.member.MemberAccountLog;
import com.chengshi.shop.model.order.Order;
import com.chengshi.shop.model.order.OrderHistory;
import com.chengshi.shop.model.order.OrderItem;
import com.chengshi.shop.model.order.OrderPayment;
import com.chengshi.shop.model.system.Sequence;
import com.chengshi.shop.service.order.OrderService;
import com.chengshi.shop.util.DateFormatUtil;
import com.chengshi.shop.util.EnumUtil;
import com.chengshi.shop.util.SessionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单相关接口
 *
 * @author xuxinlong
 * @version 2017年07月24日
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private GoodsProductMapper goodsProductMapper;
    @Resource
    private OrderHistoryMapper orderHistoryMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private OrderPaymentMapper orderPaymentMapper;
    @Resource
    private SequenceMapper sequenceMapper;
    @Resource
    private ExpressMapper expressMapper;
    @Resource
    private OrderGiftMapper orderGiftMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberAccountLogMapper memberAccountLogMapper;
    @Resource
    private ShopConfigMapper shopConfigMapper;

    @Override
    public List<Order> getOrderList(HashMap<String, Object> inMap) {
        return orderMapper.getOrderList(inMap);
    }

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    @Override
    public Order getOrderByOrderId(Integer orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }


    /**
     * 关闭订单
     *
     * @param order
     * @param opName
     * @param comments
     */
    @Override
    @Transactional
    public void closeOrder(Order order, String opName, String comments) {
        if (order.getStatus() == EnumUtil.ORDER_STATUS.待付款.getValue().byteValue()) {
            // 返回积分
            List<OrderPayment> orderPaymentList = orderPaymentMapper.getListByOrderId(order.getOrderId(), EnumUtil.PAYMENT_METHOD.积分.getValue().byteValue());
            for (OrderPayment orderPayment : orderPaymentList) {
                BigDecimal points = orderPayment.getMoney().multiply(new BigDecimal(shopConfigMapper.getConfig("POINTS_RATIO")));
                memberMapper.addMemberPoints(order.getMemberId(), points);
                //插入返还记录
                MemberAccountLog memberAccountLog = new MemberAccountLog();
                memberAccountLog.setMemberId(order.getMemberId());
                memberAccountLog.setAccountType(EnumUtil.ACCOUNT_TYPE.会员积分.getValue().byteValue());
                memberAccountLog.setNumber(points);
                memberAccountLog.setCreateTime(new Date());
                memberAccountLog.setContent("订单：" + order.getOrderNum() + "关闭，退还积分");
                memberAccountLog.setFromType(EnumUtil.ACCOUNT_FROM_TYPE.订单退还.getValue().byteValue());
                memberAccountLogMapper.insertSelective(memberAccountLog);

            }
            // 返回余额
            List<OrderPayment> orderPaymentList2 = orderPaymentMapper.getListByOrderId(order.getOrderId(), EnumUtil.PAYMENT_METHOD.余额.getValue().byteValue());
            for (OrderPayment orderPayment : orderPaymentList2) {
                memberMapper.addMemberBalance(order.getMemberId(), orderPayment.getMoney());
                //插入返还记录
                MemberAccountLog memberAccountLog = new MemberAccountLog();
                memberAccountLog.setMemberId(order.getMemberId());
                memberAccountLog.setAccountType(EnumUtil.ACCOUNT_TYPE.会员余额.getValue().byteValue());
                memberAccountLog.setNumber(orderPayment.getMoney());
                memberAccountLog.setCreateTime(new Date());
                memberAccountLog.setContent("订单：" + order.getOrderNum() + "关闭，退还余额");
                memberAccountLog.setFromType(EnumUtil.ACCOUNT_FROM_TYPE.订单退还.getValue().byteValue());
                memberAccountLogMapper.insertSelective(memberAccountLog);
            }

            List<OrderItem> orderItemList = orderItemMapper.getListByOrderId(order.getOrderId());
            for (OrderItem orderItem : orderItemList) {
                orderItem.setStatus(EnumUtil.ORDER_ITEM_STATUS.已退款.getValue().byteValue());
                orderItemMapper.updateByPrimaryKeySelective(orderItem);
                //返还库存
                goodsProductMapper.addProductStore(orderItem.getProductId(), orderItem.getProductNum());
            }
            // 关闭订单
            order.setStatus(EnumUtil.ORDER_STATUS.交易关闭.getValue().byteValue());
            orderMapper.updateByPrimaryKeySelective(order);
            saveOrderHistory(order, opName, comments);
        }
    }


    /**
     * 保存订单历史
     *
     * @param order
     * @param opName
     * @param content
     */
    @Override
    public void saveOrderHistory(Order order, String opName, String content) {
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setCreateTime(new Date());
        orderHistory.setOrderId(order.getOrderId());
        orderHistory.setOpName(opName);
        orderHistory.setContent(content);
        orderHistoryMapper.insertSelective(orderHistory);
    }

    /**
     * 根据订单编号查询订单
     *
     * @param orderNum
     * @return
     */
    @Override
    public Order getOrderByOrderNum(String orderNum) {
        return orderMapper.getOrderByOrderNum(orderNum);
    }

    @Override
    public void updateByPrimaryKeySelective(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 查询用户拥有的订单
     *
     * @param memberId
     * @return
     */
    @Override
    public List<Order> getListByMemberId(Integer memberId) {
        return orderMapper.getListByMemberId(memberId, (byte) 0);
    }

    /**
     * 查询用户拥有的订单
     *
     * @param memberId
     * @param status
     * @return
     */
    @Override
    public List<Order> getListByMemberId(Integer memberId, Byte status) {
        return orderMapper.getListByMemberId(memberId, status);
    }

    /**
     * 查询订单历史记录
     *
     * @param orderId
     * @return
     */
    @Override
    public List<OrderHistory> getOrderHistoryListByOrderId(Integer orderId) {
        return orderHistoryMapper.getListByOrderId(orderId);
    }

    /**
     * 确认收货
     *
     * @param orderId
     * @param opName
     */
    @Override
    @Transactional
    public void saveReceipt(Integer orderId, String opName) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        // 确认收货
        if (order.getStatus() != EnumUtil.ORDER_STATUS.待收货.getValue().byteValue()) {
            throw new RuntimeException("订单状态已改变，不能确认收货");
        }

        List<OrderItem> itemsList = orderItemMapper.getListByOrderId(orderId);
        // 子订单确认收货
        for (OrderItem orderItem : itemsList) {
            orderItem.setStatus(EnumUtil.ORDER_ITEM_STATUS.确认收货.getValue().byteValue());
            orderItemMapper.updateByPrimaryKeySelective(orderItem);
        }

        saveOrderHistory(order, opName, "确认收货");

        // 更新主订单状态
        order.setStatus(EnumUtil.ORDER_STATUS.待评价.getValue().byteValue());
        order.setFinishTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
    }


    /**
     * 生成订单唯一编号
     *
     * @return
     */
    @Override
    public String getOrderNum() {
        Sequence sequence = new Sequence();
        sequenceMapper.insert(sequence);
        int len = 5;
        String orderNum = sequence.getId().toString();
        orderNum = addZeroForNum(orderNum, len);
        orderNum = DateFormatUtil.formatDate(new Date(), "yyyyMMdd") + orderNum;
        return orderNum;
    }

    /**
     * 查询过期未付款的订单
     *
     * @return
     */
    @Override
    public List<Order> getOverdueOrder() {
        return orderMapper.getOverdueOrder();
    }

    /**
     * 商家开始发货
     *
     * @param order
     */
    @Transactional
    @Override
    public void saveOrderDelivery(Order order) {
        //改变主订单状态
        order.setStatus(EnumUtil.ORDER_STATUS.待收货.getValue().byteValue());
        order.setAutoFinishTime(new Date(System.currentTimeMillis() + Integer.valueOf(shopConfigMapper.getConfig("ORDER_AUTO_FINISH_TIME")) * 24 * 3600 * 1000));
        orderMapper.updateByPrimaryKeySelective(order);
        //插入订单历史记录
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setCreateTime(new Date());
        orderHistory.setOrderId(order.getOrderId());
        orderHistory.setOpName(SessionUtils.getUserName());
        orderHistory.setContent("由" + expressMapper.findByExpressCode(order.getExpressCode()) + "开始发货，物流单号" + order.getExpressNum());
        orderHistoryMapper.insertSelective(orderHistory);

        //将订单子表状态改为已发货
        List<OrderItem> list = orderItemMapper.getListByOrderId(order.getOrderId());
        for (OrderItem orderItem : list) {
            orderItem.setStatus(EnumUtil.ORDER_ITEM_STATUS.已发货.getValue().byteValue());
            orderItemMapper.updateByPrimaryKeySelective(orderItem);
        }
    }

    /**
     * 补零
     *
     * @param str
     * @param strLength
     * @return
     */
    private String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append("0").append(str);// 左(前)补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    /**
     * 查询订单赠品列表
     *
     * @param orderId
     * @return
     */
    @Override
    public List<String> getOrderGiftList(Integer orderId) {
        return orderGiftMapper.getGiftList(orderId);
    }

    /**
     * 获取可以自动确认收货的订单
     *
     * @return
     */
    @Override
    public List<Order> getCanReceiptOrder() {
        return orderMapper.getCanReceiptOrder();
    }
}
