package com.chengshi.shop.service.goods.impl;

import com.chengshi.shop.dao.goods.GoodsImageMapper;
import com.chengshi.shop.dao.goods.GoodsMapper;
import com.chengshi.shop.model.goods.Goods;
import com.chengshi.shop.model.goods.GoodsImage;
import com.chengshi.shop.service.goods.GoodsImageService;
import com.chengshi.shop.service.goods.GoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 商品相关接口实现
 *
 * @author xuxinlong
 * @version 2017年09月04日
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private GoodsImageService goodsImageService;

    /**
     * 查询商品列表
     *
     * @param inMap
     * @return
     */
    @Override
    public List<Goods> getGoodsList(HashMap<String, Object> inMap) {
        return goodsMapper.getGoodsList(inMap);
    }

    /**
     * 获取商品详情
     *
     * @param goodsId
     * @return
     */
    @Override
    public Goods getGoodsByGoodsId(Integer goodsId) {
        return goodsMapper.selectByPrimaryKey(goodsId);
    }

    /**
     * 逻辑删除商品
     *
     * @param goodsId
     */
    @Override
    public void deleteGoods(Integer goodsId) {
        goodsMapper.deleteByGoodsId(goodsId);
    }

    /**
     * 保存商品信息
     *
     * @param goods
     */
    @Override
    @Transactional
    public void saveGoods(Goods goods) {
        goods.setGoodsImg(goods.getImageList().get(0).getImgUrl());
        if (goods.getGoodsId() != null) {
            goods.setLastUpdate(new Date());
            goodsMapper.updateByPrimaryKeySelective(goods);
        } else {
            goods.setCreateTime(new Date());
            goodsMapper.insertSelective(goods);
        }
        //保存商品图片
        for (GoodsImage image : goods.getImageList()) {
            image.setGoodsId(goods.getGoodsId());
            goodsImageService.saveGoodsImage(image);
        }
    }
}
