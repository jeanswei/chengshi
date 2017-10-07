package com.chengshi.shop.service.goods.impl;

import com.chengshi.shop.dao.goods.GoodsEvaluateMapper;
import com.chengshi.shop.model.goods.GoodsEvaluate;
import com.chengshi.shop.model.order.Order;
import com.chengshi.shop.service.goods.GoodsEvaluateService;
import com.chengshi.shop.service.goods.GoodsService;
import com.chengshi.shop.service.order.OrderService;
import com.chengshi.shop.util.EnumUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 商品评价相关
 *
 * @author xuxinlong
 * @version 2017年07月24日
 */
@Service
public class GoodsEvaluateServiceImpl implements GoodsEvaluateService {
    @Resource
    private GoodsEvaluateMapper goodsEvaluateMapper;
    @Resource
    private GoodsService goodsService;
    @Resource
    private OrderService orderService;

    /**
     * 查询商品评价数据（0全部，1好评，2中评，3差评）
     *
     * @param goodsId
     * @param score
     * @return
     */
    @Override
    public List<GoodsEvaluate> getEvaluateList(Integer goodsId, Byte score) {
        return goodsEvaluateMapper.getEvaluateListByGoodsId(goodsId, score);
    }

    /**
     * 保存商品评价
     *
     * @param evaluateList
     * @param orderId
     * @param memberId
     */
    @Override
    @Transactional
    public void saveGoodsEvaluate(List<GoodsEvaluate> evaluateList, Integer orderId, Integer memberId) {
        Order order = orderService.getOrderByOrderId(orderId);
        for (GoodsEvaluate goodsEvaluate : evaluateList) {
            goodsEvaluate.setMemberId(memberId);
            goodsEvaluate.setCreateTime(new Date());
            if (order.getStatus() == EnumUtil.ORDER_STATUS.待评价.getValue().byteValue()) {
                goodsEvaluate.setCreateTime(new Date());
                goodsEvaluateMapper.insertSelective(goodsEvaluate);
                //增加商品评价数量
                goodsService.addEvaluateCount(goodsEvaluate.getGoodsId(), 1);
            }
        }
        order.setStatus(EnumUtil.ORDER_STATUS.交易成功.getValue().byteValue());
        orderService.updateByPrimaryKeySelective(order);
    }
}
