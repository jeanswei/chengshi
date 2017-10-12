package com.chengshi.shop.service.index.impl;

import com.chengshi.shop.dao.index.IndexAdMapper;
import com.chengshi.shop.dao.index.IndexFloorMapper;
import com.chengshi.shop.model.index.IndexAd;
import com.chengshi.shop.model.index.IndexFloor;
import com.chengshi.shop.service.index.IndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 首页相关
 *
 * @author xuxinlong
 * @version 2017年10月11日
 */
@Service
public class IndexServiceImpl implements IndexService {
    @Resource
    private IndexAdMapper indexAdMapper;
    @Resource
    private IndexFloorMapper indexFloorMapper;


    /**
     * 获取首页有效的广告列表
     *
     * @return
     */
    @Override
    public List<IndexAd> getValidAdList() {
        return indexAdMapper.getValidAdList();
    }

    /**
     * 获取所有广告列表
     *
     * @return
     */
    @Override
    public List<IndexAd> getIndexAdList() {
        return indexAdMapper.getIndexAdList();
    }

    /**
     * 查询首页广告详情
     *
     * @param adId
     * @return
     */
    @Override
    public IndexAd getIndexAdByAdId(Integer adId) {
        return indexAdMapper.selectByPrimaryKey(adId);
    }

    /**
     * 删除首页广告
     *
     * @param adId
     */
    @Override
    public void deleteIndexAd(Integer adId) {
        indexAdMapper.deleteByPrimaryKey(adId);
    }

    /**
     * 保存首页广告
     *
     * @param indexAd
     */
    @Override
    public void saveIndexAd(IndexAd indexAd) {
        if (indexAd.getAdId() != null) {
            indexAdMapper.updateByPrimaryKeySelective(indexAd);
        } else {
            indexAd.setCreateTime(new Date());
            indexAdMapper.insertSelective(indexAd);
        }
    }

    /**
     * 获取首页有效的楼层
     *
     * @return
     */
    @Override
    public List<IndexFloor> getFloorList() {
        return indexFloorMapper.getFloorList();
    }

    /**
     * 查询首页楼层
     *
     * @param floorId
     * @return
     */
    @Override
    public IndexFloor getIndexFloorByFloorId(Integer floorId) {
        return indexFloorMapper.selectByPrimaryKey(floorId);
    }

    /**
     * 删除楼层
     *
     * @param floorId
     */
    @Override
    public void deleteIndexFloor(Integer floorId) {
        indexFloorMapper.deleteByPrimaryKey(floorId);
    }

    /**
     * 保存首页楼层
     *
     * @param indexFloor
     */
    @Override
    public void saveIndexFloor(IndexFloor indexFloor) {
        if (indexFloor.getFloorId() !=null){
            indexFloorMapper.updateByPrimaryKeySelective(indexFloor);
        } else {
            indexFloor.setCreateTime(new Date());
            indexFloorMapper.insertSelective(indexFloor);
        }
    }
}
