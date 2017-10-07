package com.chengshi.shop.controller.mobile.member;

import com.chengshi.shop.controller.common.BaseController;
import com.chengshi.shop.model.member.Member;
import com.chengshi.shop.model.order.Order;
import com.chengshi.shop.model.order.OrderItem;
import com.chengshi.shop.service.order.OrderItemService;
import com.chengshi.shop.service.order.OrderPaymentService;
import com.chengshi.shop.service.order.OrderService;
import com.chengshi.shop.util.DateFormatUtil;
import com.chengshi.shop.util.MessageUtils;
import com.chengshi.shop.util.SessionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 会员订单信息
 *
 * @author xuxinlong
 * @version 2017年08月01日
 */
@Api(value = "member", description = "会员订单信息相关接口")
@RestController
@RequestMapping(value = "/mobile/member")
public class MobileMemberOrderController extends BaseController {
    @Resource
    private OrderService orderService;
    @Resource
    private OrderItemService orderItemService;
    @Resource
    private OrderPaymentService orderPaymentService;


    /**
     * 获取我的订单列表的ajax请求
     *
     * @param request
     * @param status
     * @return
     */
    @ApiOperation(value = "获取会员订单列表,可以通过status过滤，不传则为全部订单")
    @ApiImplicitParam(name = "status", value = "订单状态", paramType = "query", dataType = "byte")
    @GetMapping(value = "/getOrderList")
    public HashMap<String, Object> getOrderList(HttpServletRequest request, @RequestParam(required = false) Byte status) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            List<HashMap<String, Object>> orderList = new ArrayList<>();
            Member nowMember = SessionUtils.getMember();
            PageHelper.startPage(request);
            List<Order> list = orderService.getListByMemberId(nowMember.getMemberId(), status);

            for (Order order : list) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("orderId", order.getOrderId());
                map.put("orderNum", order.getOrderNum());
                map.put("status", order.getStatus());
                map.put("totalAmount", order.getTotalAmount().add(order.getFare()));
                map.put("tradeAmount", order.getTotalAmount().add(order.getFare()).subtract(order.getPayed()));

                // 订单明细
                List<OrderItem> itemList = orderItemService.getListByOrderId(order.getOrderId());
                List<HashMap<String, Object>> itemMapList = new ArrayList<>();
                for (OrderItem orderItem : itemList) {
                    HashMap<String, Object> itemMap = new HashMap<>();
                    itemMap.put("orderItemId", orderItem.getOrderItemId());
                    itemMap.put("goodsId", orderItem.getGoodsId());
                    itemMap.put("status", orderItem.getStatus());
                    itemMap.put("specView", orderItem.getSpecView());
                    itemMap.put("goodsImage", orderItem.getGoodsImg());
                    itemMap.put("productId", orderItem.getProductId());
                    itemMap.put("goodsName", orderItem.getGoodsName());
                    itemMap.put("price", orderItem.getPrice());
                    itemMap.put("productNum", orderItem.getProductNum());
                    itemMapList.add(itemMap);
                }
                map.put("orderItemList", itemMapList);
                orderList.add(map);
            }
            PageInfo pageList = new PageInfo<>(list);
            pageList.setList(orderList);
            retMap.put("data", pageList);
        } catch (Exception e) {
            return MessageUtils.error("获取订单列表失败!");
        }
        return retMap;
    }

    /**
     * 获取我的订单详情的ajax请求
     *
     * @param orderId
     * @return
     */
    @ApiOperation(value = "获取会员订单详情信息")
    @ApiImplicitParam(name = "orderId", value = "订单id", paramType = "query", dataType = "int", required = true)
    @GetMapping(value = "/getOrderDetail")
    public HashMap<String, Object> getOrderDetail(@RequestParam Integer orderId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            Order order = orderService.getOrderByOrderId(orderId);
            if (order != null) {
                // 订单基本信息
                HashMap<String, Object> orderBaseMap = new HashMap<>();
                orderBaseMap.put("orderId", order.getOrderId());
                orderBaseMap.put("orderNum", order.getOrderNum());
                //订单状态
                orderBaseMap.put("status", order.getStatus());

                orderBaseMap.put("createTime", DateFormatUtil.formatDate(order.getCreateTime(), DateFormatUtil.FULL_DATE_FORMAT));
                orderBaseMap.put("goodsAmount", order.getTotalAmount());
                orderBaseMap.put("postage", order.getFare());
                orderBaseMap.put("totalAmount", order.getTotalAmount().add(order.getFare()));
                orderBaseMap.put("tradeAmount", order.getTotalAmount().add(order.getFare()).subtract(order.getPayed()));
                orderBaseMap.put("markText", order.getMarkText());
                // 收货地址
                orderBaseMap.put("consignee", order.getConsignee());
                orderBaseMap.put("address", order.getAddress());
                orderBaseMap.put("mobile", order.getMobile());
                //订单赠品
                List<String> gifts = orderService.getOrderGiftList(order.getOrderId());
                orderBaseMap.put("giftList", gifts);
                // 各种支付方式使用明细
                List<HashMap<String, Object>> orderPaymentMap = orderPaymentService.orderPayment(orderId);
                orderBaseMap.put("orderPayment", orderPaymentMap);
                retMap.put("orderBase", orderBaseMap);
                // 订单明细
                List<OrderItem> itemsList = orderItemService.getListByOrderId(order.getOrderId());
                List<HashMap<String, Object>> itemsMapList = new ArrayList<>();

                for (OrderItem orderItem : itemsList) {
                    HashMap<String, Object> itemMap = new HashMap<>();
                    itemMap.put("orderItemId", orderItem.getOrderItemId());
                    itemMap.put("goodsId", orderItem.getGoodsId());
                    itemMap.put("goodsName", orderItem.getGoodsName());
                    itemMap.put("status", orderItem.getStatus());
                    itemMap.put("specView", orderItem.getSpecView());
                    itemMap.put("goodsImage", orderItem.getGoodsImg());
                    itemMap.put("productId", orderItem.getProductId());
                    itemMap.put("price", orderItem.getPrice());
                    itemMap.put("productNum", orderItem.getProductNum());
                    itemsMapList.add(itemMap);
                }
                retMap.put("orderItem", itemsMapList);
            } else {
                return MessageUtils.error("没有找到该订单！");
            }
        } catch (Exception e) {
            return MessageUtils.error("获取订单详情失败！");
        }
        return retMap;
    }

    /**
     * 获取订单下所有订单明细
     *
     * @param orderId
     * @return
     */
    @ApiOperation(value = "获取会员订单下的子订单列表")
    @ApiImplicitParam(name = "orderId", value = "订单id", paramType = "query", dataType = "int", required = true)
    @GetMapping(value = "/getOrderItemList")
    public HashMap<String, Object> getOrderItemList(@RequestParam Integer orderId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            // 订单明细
            List<OrderItem> itemsList = orderItemService.getListByOrderId(orderId);
            List<HashMap<String, Object>> itemsMapList = new ArrayList<>();

            for (OrderItem orderItem : itemsList) {
                HashMap<String, Object> itemMap = new HashMap<>();
                itemMap.put("orderItemId", orderItem.getOrderItemId());
                itemMap.put("goodsId", orderItem.getGoodsId());
                itemMap.put("goodsImage", orderItem.getGoodsImg());
                itemMap.put("goodsName", orderItem.getGoodsName());
                itemMap.put("price", orderItem.getPrice());
                itemMap.put("productNum", orderItem.getProductNum());
                itemsMapList.add(itemMap);
            }
            retMap.put("orderItem", itemsMapList);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }
}
