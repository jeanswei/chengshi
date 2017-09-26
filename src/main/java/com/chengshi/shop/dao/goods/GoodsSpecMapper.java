package com.chengshi.shop.dao.goods;

import com.chengshi.shop.model.goods.GoodsSpec;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsSpecMapper {
    int deleteByPrimaryKey(Integer specId);

    int insert(GoodsSpec record);

    int insertSelective(GoodsSpec record);

    GoodsSpec selectByPrimaryKey(Integer specId);

    int updateByPrimaryKeySelective(GoodsSpec record);

    int updateByPrimaryKey(GoodsSpec record);

    /**
     * 检查是否存在该规格
     *
     * @param specName
     * @return
     */
    boolean checkSpecName(String specName);

    /**
     * 获取商品规格列表
     *
     * @param specName
     * @return
     */
    List<GoodsSpec> getSpecList(@Param(value = "specName") String specName);

    /**
     * 根据商品id获取所用规格
     * @param goodsId
     * @return
     */
    List<GoodsSpec> getSpecListByGoodsId(Integer goodsId);
}