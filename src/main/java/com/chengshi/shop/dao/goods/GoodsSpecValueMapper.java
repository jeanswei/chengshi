package com.chengshi.shop.dao.goods;

import com.chengshi.shop.model.goods.GoodsSpecValue;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsSpecValueMapper {
    int deleteByPrimaryKey(Integer specValueId);

    int insert(GoodsSpecValue record);

    int insertSelective(GoodsSpecValue record);

    GoodsSpecValue selectByPrimaryKey(Integer specValueId);

    int updateByPrimaryKeySelective(GoodsSpecValue record);

    int updateByPrimaryKey(GoodsSpecValue record);

    /**
     * 检查是否已拥有该规格值
     * @param specId
     * @param specValue
     * @return
     */
    boolean checkSpecValue(@Param("specId") Integer specId,@Param("specValue") String specValue);

    /**
     * 商品规格值列表
     * @param specId
     * @param specValue
     * @return
     */
    List<GoodsSpecValue> getSpecValueList(@Param("specId") Integer specId,@Param("specValue") String specValue);

    /**
     * 获取商品所使用规格值
     * @param specId
     * @param goodsId
     * @return
     */
    List<GoodsSpecValue> getListBySpecIdAndGoodsId(@Param("specId") Integer specId,@Param("goodsId") Integer goodsId);
}