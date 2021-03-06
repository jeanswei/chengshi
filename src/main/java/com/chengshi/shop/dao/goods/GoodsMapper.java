package com.chengshi.shop.dao.goods;

import com.chengshi.shop.model.goods.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface GoodsMapper {
    int deleteByPrimaryKey(Integer goodsId);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer goodsId);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    /**
     * 查询商品列表
     * @param inMap
     * @return
     */
    List<Goods> getGoodsList(HashMap<String, Object> inMap);

    /**
     * 逻辑删除商品
     * @param goodsId
     */
    void deleteByGoodsId(Integer goodsId);

    /**
     * 增加商品评价数量
     * @param goodsId
     * @param count
     */
    void addEvaluateCount(@Param("goodsId") Integer goodsId, @Param("count") Integer count);

    /**
     * 增加商品销量
     * @param goodsId
     * @param count
     */
    void addSaleCount(@Param("goodsId") Integer goodsId, @Param("count") Integer count);
}