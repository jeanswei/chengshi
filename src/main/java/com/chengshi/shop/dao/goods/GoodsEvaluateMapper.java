package com.chengshi.shop.dao.goods;

import com.chengshi.shop.model.goods.GoodsEvaluate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsEvaluateMapper {
    int deleteByPrimaryKey(Integer evaluateId);

    int insert(GoodsEvaluate record);

    int insertSelective(GoodsEvaluate record);

    GoodsEvaluate selectByPrimaryKey(Integer evaluateId);

    int updateByPrimaryKeySelective(GoodsEvaluate record);

    int updateByPrimaryKey(GoodsEvaluate record);

    /**
     * 获取商品评价
     * @param goodsId
     * @param score
     * @return
     */
    List<GoodsEvaluate> getEvaluateListByGoodsId(@Param("goodsId") Integer goodsId, @Param("score") Byte score);
}