package com.chengshi.shop.dao.goods;

import com.chengshi.shop.model.goods.GoodsProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsProductMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(GoodsProduct record);

    int insertSelective(GoodsProduct record);

    GoodsProduct selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(GoodsProduct record);

    int updateByPrimaryKey(GoodsProduct record);

    /**
     * 根据商品id获取货品列表
     * @param goodsId
     * @return
     */
    List<GoodsProduct> getList(Integer goodsId);
}