package com.chengshi.shop.service.goods.impl;

import com.chengshi.shop.dao.goods.GoodsMapper;
import com.chengshi.shop.dao.goods.GoodsProductMapper;
import com.chengshi.shop.dao.goods.GoodsProductSpecMapper;
import com.chengshi.shop.model.goods.Goods;
import com.chengshi.shop.model.goods.GoodsImage;
import com.chengshi.shop.model.goods.GoodsProduct;
import com.chengshi.shop.service.goods.GoodsImageService;
import com.chengshi.shop.service.goods.GoodsProductService;
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
    @Resource
    private GoodsProductService goodsProductService;
    @Resource
    private GoodsProductMapper goodsProductMapper;
    @Resource
    private GoodsProductSpecMapper goodsProductSpecMapper;


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
        if (!goods.getImageList().isEmpty()) {
            goods.setGoodsImg(goods.getImageList().get(0).getImgUrl());
        } else {
            goods.setGoodsImg("");
        }

        if (goods.getGoodsId() != null) {
            goods.setLastUpdate(new Date());
            goodsMapper.updateByPrimaryKeySelective(goods);
        } else {
            goods.setCreateTime(new Date());
            goodsMapper.insertSelective(goods);
        }

        StringBuilder imgIds = new StringBuilder();
        //保存商品图片
        for (GoodsImage image : goods.getImageList()) {
            image.setGoodsId(goods.getGoodsId());
            goodsImageService.saveGoodsImage(image);
            imgIds.append(",").append(image.getImgId());
        }

        goodsImageService.deleteNotInImgIds(goods.getGoodsId(), imgIds.length() > 0 ? imgIds.substring(1) : "0");

        //如果是重新生成规格
        if (!goods.getProductList().isEmpty() && goods.getProductList().get(0).getProductId() == null) {
            //先删除所有已有的货品和货品规格再插入新货品
            goodsProductMapper.deleteProductByGoodsId(goods.getGoodsId());
            goodsProductSpecMapper.deleteProductSpecByGoodsId(goods.getGoodsId());
        }
        //保存货品
        for (GoodsProduct goodsProduct : goods.getProductList()) {
            goodsProduct.setGoodsId(goods.getGoodsId());
            goodsProductService.saveProduct(goodsProduct);
        }
    }

    /**
     * 增加评价数量
     *
     * @param goodsId
     * @param count
     */
    @Override
    public void addEvaluateCount(Integer goodsId, Integer count) {
        goodsMapper.addEvaluateCount(goodsId, count);
    }
}
