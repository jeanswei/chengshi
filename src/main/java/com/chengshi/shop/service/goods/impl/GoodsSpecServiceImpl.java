package com.chengshi.shop.service.goods.impl;

import com.chengshi.shop.dao.goods.GoodsSpecMapper;
import com.chengshi.shop.dao.goods.GoodsSpecValueMapper;
import com.chengshi.shop.model.goods.GoodsSpec;
import com.chengshi.shop.model.goods.GoodsSpecValue;
import com.chengshi.shop.service.goods.GoodsSpecService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private GoodsSpecValueMapper goodsSpecValueMapper;

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

    /**
     * 检查规格值是否存在
     *
     * @param specId
     * @param specValue
     * @return
     */
    @Override
    public boolean checkSpecValue(Integer specId, String specValue) {
        return goodsSpecValueMapper.checkSpecValue(specId, specValue);
    }

    /**
     * 保存规格值
     *
     * @param goodsSpecValue
     */
    @Override
    public void saveSpecValue(GoodsSpecValue goodsSpecValue) {
        if (goodsSpecValue.getSpecValueId() != null) {
            goodsSpecValueMapper.updateByPrimaryKeySelective(goodsSpecValue);
        } else {
            goodsSpecValueMapper.insertSelective(goodsSpecValue);
        }
    }

    /**
     * 获取商品规格列表
     *
     * @return
     */
    @Override
    public List<GoodsSpec> getSpecList() {
        return goodsSpecMapper.getSpecList();
    }

    /**
     * 获取商品规格值列表
     *
     * @param specId
     * @return
     */
    @Override
    public List<GoodsSpecValue> getSpecValueList(Integer specId) {
        return goodsSpecValueMapper.getSpecValueList(specId);
    }
}
