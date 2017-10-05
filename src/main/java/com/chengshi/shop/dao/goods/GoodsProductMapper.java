package com.chengshi.shop.dao.goods;

import com.chengshi.shop.model.goods.GoodsProduct;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 逻辑删除货品
     * @param goodsId
     */
    void deleteProductByGoodsId(Integer goodsId);

    /**
     * 增加货品库存
     * @param productId
     * @param count
     */
    void addProductStore(@Param("productId") Integer productId, @Param("count") Integer count);

    /**
     * 减少货品库存
     * @param productId
     * @param count
     */
    void subProductStore(@Param("productId") Integer productId, @Param("count") Integer count);

    /**
     * 获取价格最低的货品
     * @param goodsId
     * @return
     */
    GoodsProduct getFirstProductByGoodsId(Integer goodsId);
}