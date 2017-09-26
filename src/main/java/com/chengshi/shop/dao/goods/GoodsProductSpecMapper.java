package com.chengshi.shop.dao.goods;

import com.chengshi.shop.model.goods.GoodsProductSpec;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsProductSpecMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsProductSpec record);

    int insertSelective(GoodsProductSpec record);

    GoodsProductSpec selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsProductSpec record);

    int updateByPrimaryKey(GoodsProductSpec record);

    /**
     * 获取货品拥有的规格值
     * @param productId
     * @return
     */
    List<String> getSpecValueListByProductId(Integer productId);
}