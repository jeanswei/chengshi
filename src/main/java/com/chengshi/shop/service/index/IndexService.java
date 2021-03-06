package com.chengshi.shop.service.index;

import com.chengshi.shop.model.index.IndexAd;
import com.chengshi.shop.model.index.IndexFloor;

import java.util.List;

/**
 * 首页相关
 * @author xuxinlong
 * @version 2017年10月11日
 */
public interface IndexService {

    /**
     * 获取首页有效的广告列表
     * @return
     */
    List<IndexAd> getValidAdList();

    /**
     * 获取首页所有广告列表
     * @return
     */
    List<IndexAd> getIndexAdList();

    /**
     * 查询首页广告详情
     * @param adId
     * @return
     */
    IndexAd getIndexAdByAdId(Integer adId);

    /**
     * 删除首页广告
     * @param adId
     */
    void deleteIndexAd(Integer adId);

    /**
     * 保存首页广告
     * @param indexAd
     */
    void saveIndexAd(IndexAd indexAd);

    /**
     * 获取首页有效的楼层
     * @return
     */
    List<IndexFloor> getFloorList();

    /**
     * 查询首页楼层
     * @param floorId
     * @return
     */
    IndexFloor getIndexFloorByFloorId(Integer floorId);

    /**
     * 删除楼层
     * @param floorId
     */
    void deleteIndexFloor(Integer floorId);

    /**
     * 保存首页楼层
     * @param indexFloor
     */
    void saveIndexFloor(IndexFloor indexFloor);
}
