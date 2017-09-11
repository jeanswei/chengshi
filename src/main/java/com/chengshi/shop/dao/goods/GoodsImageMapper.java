package com.chengshi.shop.dao.goods;

import com.chengshi.shop.model.goods.GoodsImage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsImageMapper {
    int deleteByPrimaryKey(Integer imgId);

    int insert(GoodsImage record);

    int insertSelective(GoodsImage record);

    GoodsImage selectByPrimaryKey(Integer imgId);

    int updateByPrimaryKeySelective(GoodsImage record);

    int updateByPrimaryKey(GoodsImage record);

    /**
     * 获取商品图片列表
     * @param goodsId
     * @return
     */
    List<GoodsImage> getList(Integer goodsId);
}