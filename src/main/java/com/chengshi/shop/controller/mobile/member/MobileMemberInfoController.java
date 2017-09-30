package com.chengshi.shop.controller.mobile.member;

import com.chengshi.shop.controller.common.BaseController;
import com.chengshi.shop.model.member.Member;
import com.chengshi.shop.model.order.Order;
import com.chengshi.shop.service.order.OrderService;
import com.chengshi.shop.util.EnumUtil;
import com.chengshi.shop.util.MessageUtils;
import com.chengshi.shop.util.SessionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 移动端个人信息相关的Controller
 *
 * @author xuxinlong
 * @version 2016年8月30日
 */
@RestController
@RequestMapping(value = "/mobile/member")
public class MobileMemberInfoController extends BaseController {
    @Resource
    private OrderService orderService;


    @Value("${wechat.mp.host}")
    private String HOST;


    /**
     * 获取用户基本信息(订单数)
     *
     * @return
     */
    @GetMapping(value = "/getMemberInfo")
    public HashMap<String, Object> getMemberInfo() {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            Member nowMember = SessionUtils.getMember();
            retMap.put("memberName", nowMember.getName());
            retMap.put("headImg", nowMember.getHeadImg());
            int waitPay = 0; // 待付款订单数量
            int waitDelivery = 0; // 待发货订单数量
            int waitReceipt = 0; // 待收货订单数量
            int waitEvaluate = 0;// 待评价订单数量
            List<Order> orderList = orderService.getListByMemberId(nowMember.getMemberId());
            for (Order order : orderList) {
                if (order.getStatus().intValue() == EnumUtil.ORDER_STATUS.待付款.getValue()) {
                    waitPay++;
                } else if (order.getStatus().intValue() == EnumUtil.ORDER_STATUS.待发货.getValue()) {
                    waitDelivery++;
                } else if (order.getStatus().intValue() == EnumUtil.ORDER_STATUS.待收货.getValue()) {
                    waitReceipt++;
                } else if (order.getStatus().intValue() == EnumUtil.ORDER_STATUS.交易成功.getValue()
                        && !order.getIsEvaluate()) {
                    waitEvaluate++;
                }
            }
            retMap.put("waitPay", waitPay);
            retMap.put("waitDelivery", waitDelivery);
            retMap.put("waitReceipt", waitReceipt);
            retMap.put("waitEvaluate", waitEvaluate);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }
}
