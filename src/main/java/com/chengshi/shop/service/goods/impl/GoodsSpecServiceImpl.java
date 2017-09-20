package com.chengshi.shop.service.goods.impl;

import com.chengshi.shop.dao.goods.GoodsSpecMapper;
import com.chengshi.shop.model.goods.GoodsSpec;
import com.chengshi.shop.service.goods.GoodsSpecService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商品规格相关接口
 *
 * @author xuxinlong
 * @version 2017年09月20日
 */
@Service
public class GoodsSpecServiceImpl implements GoodsSpecService {
    @Resource
    private GoodsSpecMapper goodsSpecMapper;

    /**
     * 检查是否存在规格
     *
     * @param specName
     * @return
     */
    @Override
    public boolean checkSpecName(String specName) {
        return goodsSpecMapper.checkSpecName(specName);
    }

    /**
     * 保存商品规格
     *
     * @param goodsSpec
     */
    @Override
    public void saveSpec(GoodsSpec goodsSpec) {
        if (goodsSpec.getSpecId() != null) {
            goodsSpecMapper.updateByPrimaryKeySelective(goodsSpec);
        } else {
            goodsSpecMapper.insertSelective(goodsSpec);
        }
    }
}
