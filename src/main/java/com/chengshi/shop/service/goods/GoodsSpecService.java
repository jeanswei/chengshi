package com.chengshi.shop.service.goods;

import com.chengshi.shop.model.goods.GoodsSpec;
import com.chengshi.shop.model.goods.GoodsSpecValue;

import java.util.List;

/**
 * 商品规格相关接口
 * @author xuxinlong
 * @version 2017年09月20日
 */
public interface GoodsSpecService {

    /**
     * 检查是否存在规格
     * @param specName
     * @return
     */
    boolean checkSpecName(String specName);

    /**
     * 保存商品规格
     * @param goodsSpec
     */
    void saveSpec(GoodsSpec goodsSpec);

    /**
     * 检查规格值是否存在
     * @param specId
     * @param specValue
     * @return
     */
    boolean checkSpecValue(Integer specId, String specValue);

    /**
     * 保存规格值
     * @param goodsSpecValue
     */
    void saveSpecValue(GoodsSpecValue goodsSpecValue);

    /**
     * 获取商品规格列表
     * @return
     */
    List<GoodsSpec> getSpecList();

    /**
     * 获取商品规格值列表
     * @param specId
     * @return
     */
    List<GoodsSpecValue> getSpecValueList(Integer specId);
}
