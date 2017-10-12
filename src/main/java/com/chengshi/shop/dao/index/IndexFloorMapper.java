package com.chengshi.shop.dao.index;

import com.chengshi.shop.model.index.IndexFloor;

import java.util.List;

public interface IndexFloorMapper {
    int deleteByPrimaryKey(Integer floorId);

    int insert(IndexFloor record);

    int insertSelective(IndexFloor record);

    IndexFloor selectByPrimaryKey(Integer floorId);

    int updateByPrimaryKeySelective(IndexFloor record);

    int updateByPrimaryKey(IndexFloor record);

    /**
     * 获取首页楼层
     * @return
     */
    List<IndexFloor> getFloorList();
}