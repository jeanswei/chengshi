package com.chengshi.shop.schedule;

import com.chengshi.shop.model.order.Order;
import com.chengshi.shop.service.order.OrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时器任务管理
 *
 * @author xuxinlong
 * @version 2017年08月02日
 */
@Component
public class ScheduledTasks {
    @Resource
    private OrderService orderService;

    /**
     * 订单关闭定时任务 每10分钟执行一次
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void closeOrder() {
        List<Order> list = orderService.getOverdueOrder();
        for (Order order : list) {
            orderService.closeOrder(order, "系统", "订单超时未支付，系统自动关闭");
            System.out.println("关闭订单：" + order.getOrderNum() + "成功");
        }
    }

    /**
     * 系统自动确认收货
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void receiptOrder() {
        List<Order> list = orderService.getCanReceiptOrder();
        for (Order order : list) {
            orderService.saveReceipt(order.getOrderId(), "系统");
        }
    }
}