package com.chengshi.shop.service.goods;

import com.chengshi.shop.model.goods.GoodsProduct;

import java.util.List;

/**
 * 货品相关接口
 * @author xuxinlong
 * @version 2017年09月25日
 */
public interface GoodsProductService {

    /**
     * 保存货品
     * @param goodsProduct
     */
    void saveProduct(GoodsProduct goodsProduct);

    /**
     * 根据商品id获取货品列表
     * @param goodsId
     * @return
     */
    List<GoodsProduct> getProductList(Integer goodsId);
}
