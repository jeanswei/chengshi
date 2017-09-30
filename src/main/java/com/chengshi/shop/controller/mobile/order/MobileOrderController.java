package com.chengshi.shop.controller.mobile.order;

import com.chengshi.shop.controller.common.BaseController;
import com.chengshi.shop.model.member.Member;
import com.chengshi.shop.model.order.Order;
import com.chengshi.shop.model.order.OrderInputBean;
import com.chengshi.shop.service.member.MemberAssetsService;
import com.chengshi.shop.service.order.OrderPaymentService;
import com.chengshi.shop.service.order.OrderSaveService;
import com.chengshi.shop.service.order.OrderService;
import com.chengshi.shop.util.EnumUtil;
import com.chengshi.shop.util.MessageUtils;
import com.chengshi.shop.util.SessionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * 订单相关
 *
 * @author xuxinlong
 * @version 2017年07月18日
 */
@RestController
@RequestMapping("/mobile/order")
public class MobileOrderController extends BaseController {
    @Resource
    private OrderService orderService;
    @Resource
    private OrderSaveService orderSaveService;
    @Resource
    private OrderPaymentService orderPaymentService;
    @Resource
    private MemberAssetsService memberAssetsService;


    /**
     * 添加保存订单
     *
     * @param orderInputBean
     * @return
     */
    @PostMapping(value = "/addOrderList")
    public HashMap<String, Object> addOrderList(@ModelAttribute("orderInputBean") OrderInputBean orderInputBean) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            // 获得当前登陆用户
            Member nowMember = SessionUtils.getMember();
            orderInputBean.setMemberId(nowMember.getMemberId());
            retMap.putAll(orderSaveService.saveOrder(orderInputBean));
        } catch (Exception e) {
            retMap = MessageUtils.error("提交订单失败!");
        }
        return retMap;
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
    @PostMapping(value = "/cancelOrder")
    public HashMap<String, Object> cancelOrder(@RequestParam Integer orderId) {
        HashMap<String, Object> retMap = MessageUtils.success("取消订单成功");
        try {
            Order order = orderService.getOrderByOrderId(orderId);
            if (order.getStatus() == EnumUtil.ORDER_STATUS.待付款.getValue().byteValue()) {
                orderService.closeOrder(order, "客户", "取消订单");
            } else {
                retMap = MessageUtils.error("订单状态已改变,取消订单失败");
            }
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 删除订单
     *
     * @param orderId
     * @return
     */
    @PostMapping(value = "/delOrder")
    public HashMap<String, Object> delOrder(@RequestParam Integer orderId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            retMap.putAll(orderSaveService.delOrder(orderId));
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }


    /**
     * 订单金额计算
     *
     * @param orderInputBean
     * @return
     */
    @GetMapping(value = "/countOrder")
    public HashMap<String, Object> countOrder(@ModelAttribute("orderInputBean") OrderInputBean orderInputBean) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            // 获得当前登陆用户
            Member nowMember = SessionUtils.getMember();
            orderInputBean.setMemberId(nowMember.getMemberId());
            retMap.putAll(orderSaveService.countOrder(orderInputBean));
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 用户确认收货
     *
     * @param orderId
     * @return
     */
    @PostMapping(value = "/saveReceipt")
    public HashMap<String, Object> saveReceipt(@RequestParam Integer orderId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            orderService.saveReceipt(orderId, "客户");
        } catch (Exception e) {
            return MessageUtils.error("确认收货失败");
        }
        return retMap;
    }

    /**
     * 获取订单可用支付方式
     *
     * @return
     */
    @GetMapping(value = "/getPaymentList")
    public HashMap<String, Object> getPaymentList() {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
//            retMap.put("paymentList", orderPaymentService.getMallPayment(SessionUtils.getMallId(), SessionUtils.getMember().getMemberId()));
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 使用余额支付
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "balancePay")
    public HashMap<String, Object> balancePay(@RequestParam Integer orderId) {
        HashMap<String, Object> retMap = MessageUtils.success("支付成功");
        try {
            Order order = orderService.getOrderByOrderId(orderId);
            if (order.getStatus().intValue() == EnumUtil.ORDER_STATUS.待付款.getValue()) {
                retMap.putAll(orderSaveService.balancePay(order));
            } else {
                retMap = MessageUtils.error("订单状态已改变，支付失败");
            }
        } catch (Exception e) {
            retMap = MessageUtils.error("支付失败");
        }
        return retMap;
    }

    /**
     * 检查余额是否够支付订单
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "checkBalancePay")
    public HashMap<String, Object> checkBalancePay(@RequestParam Integer orderId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            Member nowMember = SessionUtils.getMember();
            Order order = orderService.getOrderByOrderId(orderId);
            if (order.getStatus().intValue() == EnumUtil.ORDER_STATUS.待付款.getValue()) {
                BigDecimal memberBalance = memberAssetsService.getMemberBalanceByMemberId(nowMember.getMemberId());
                //实付金额
                BigDecimal tradeAmount = order.getTotalAmount().add(order.getFare()).subtract(order.getPayed());
                if (memberBalance.compareTo(tradeAmount) >= 0) {//如果余额够付订单
                    tradeAmount = BigDecimal.ZERO;
                } else {
                    tradeAmount = tradeAmount.subtract(memberBalance);
                }
                retMap.put("finalMoney", tradeAmount.doubleValue());
            } else {
                retMap = MessageUtils.error("订单状态已改变");
            }
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }
}
