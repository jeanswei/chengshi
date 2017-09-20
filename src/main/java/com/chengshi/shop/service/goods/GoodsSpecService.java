package com.chengshi.shop.service.goods;

import com.chengshi.shop.model.goods.GoodsSpec; /**
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
}
