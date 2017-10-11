package com.chengshi.shop.service.index.impl;

import com.chengshi.shop.dao.index.IndexAdMapper;
import com.chengshi.shop.model.index.IndexAd;
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
}
