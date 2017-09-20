package com.chengshi.shop.dao.goods;

import com.chengshi.shop.model.goods.GoodsSpec;
import org.springframework.stereotype.Repository;

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
     * @param specName
     * @return
     */
    boolean checkSpecName(String specName);
}