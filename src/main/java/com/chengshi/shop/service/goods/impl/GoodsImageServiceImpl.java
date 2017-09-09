package com.chengshi.shop.service.goods.impl;

import com.chengshi.shop.dao.goods.GoodsImageMapper;
import com.chengshi.shop.model.goods.GoodsImage;
import com.chengshi.shop.service.goods.GoodsImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品图片相关接口
 * @author xuxinlong
 * @version 2017年09月09日
 */
@Service
public class GoodsImageServiceImpl implements GoodsImageService {
    @Resource
    private GoodsImageMapper goodsImageMapper;

    /**
     * 保存商品图片
     *
     * @param goodsImage
     */
    @Override
    public void saveGoodsImage(GoodsImage goodsImage) {
        if (goodsImage.getImgId() != null) {
            goodsImageMapper.updateByPrimaryKeySelective(goodsImage);
        } else {
            goodsImageMapper.insertSelective(goodsImage);
        }
    }

    /**
     * 获取商品图片列表
     *
     * @param goodsId
     * @return
     */
    @Override
    public List<GoodsImage> getImageList(Integer goodsId) {
        return goodsImageMapper.getList(goodsId);
    }
}
