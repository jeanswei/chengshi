package com.chengshi.shop.dao.index;

import com.chengshi.shop.model.index.IndexAd;

import java.util.List;

public interface IndexAdMapper {
    int deleteByPrimaryKey(Integer adId);

    int insert(IndexAd record);

    int insertSelective(IndexAd record);

    IndexAd selectByPrimaryKey(Integer adId);

    int updateByPrimaryKeySelective(IndexAd record);

    int updateByPrimaryKey(IndexAd record);

    /**
     * 获取首页有效的广告
     * @return
     */
    List<IndexAd> getValidAdList();

    /**
     * 获取所有广告
     * @return
     */
    List<IndexAd> getIndexAdList();
}