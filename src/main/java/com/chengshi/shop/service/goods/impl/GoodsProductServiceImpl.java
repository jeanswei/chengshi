package com.chengshi.shop.service.goods.impl;

import com.chengshi.shop.dao.goods.GoodsProductMapper;
import com.chengshi.shop.dao.goods.GoodsProductSpecMapper;
import com.chengshi.shop.dao.goods.GoodsSpecMapper;
import com.chengshi.shop.dao.goods.GoodsSpecValueMapper;
import com.chengshi.shop.model.goods.GoodsProduct;
import com.chengshi.shop.model.goods.GoodsProductSpec;
import com.chengshi.shop.model.goods.GoodsSpec;
import com.chengshi.shop.model.goods.GoodsSpecValue;
import com.chengshi.shop.service.goods.GoodsProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 货品相关接口
 * @author xuxinlong
 * @version 2017年09月25日
 */
@Service
public class GoodsProductServiceImpl implements GoodsProductService {
    @Resource
    private GoodsProductMapper goodsProductMapper;
    @Resource
    private GoodsProductSpecMapper goodsProductSpecMapper;
    @Resource
    private GoodsSpecMapper goodsSpecMapper;
    @Resource
    private GoodsSpecValueMapper goodsSpecValueMapper;

    /**
     * 保存货品
     *
     * @param goodsProduct
     */
    @Override
    @Transactional
    public void saveProduct(GoodsProduct goodsProduct) {
        if (goodsProduct.getProductId()!=null){
            goodsProductMapper.updateByPrimaryKeySelective(goodsProduct);
        } else {
            goodsProductMapper.insertSelective(goodsProduct);
            //生成货品拥有商品规格表
            if (StringUtils.isNotBlank(goodsProduct.getSpecIdAndValueId())){
                for (String specIdAndValueId : goodsProduct.getSpecIdAndValueId().split(";")){
                    GoodsProductSpec goodsProductSpec = new GoodsProductSpec();
                    goodsProductSpec.setGoodsId(goodsProduct.getGoodsId());
                    goodsProductSpec.setProductId(goodsProduct.getProductId());
                    goodsProductSpec.setSpecId(Integer.valueOf(specIdAndValueId.split(":")[0]));
                    GoodsSpec goodsSpec = goodsSpecMapper.selectByPrimaryKey(Integer.valueOf(specIdAndValueId.split(":")[0]));
                    goodsProductSpec.setSpecName(goodsSpec.getSpecName());
                    goodsProductSpec.setSpecValueId(Integer.valueOf(specIdAndValueId.split(":")[1]));
                    GoodsSpecValue goodsSpecValue = goodsSpecValueMapper.selectByPrimaryKey(Integer.valueOf(specIdAndValueId.split(":")[1]));
                    goodsProductSpec.setSpecValue(goodsSpecValue.getSpecValue());
                    goodsProductSpecMapper.insertSelective(goodsProductSpec);
                }
            }
        }
    }

    /**
     * 根据商品id获取货品列表
     *
     * @param goodsId
     * @return
     */
    @Override
    public List<GoodsProduct> getProductList(Integer goodsId) {
        return goodsProductMapper.getList(goodsId);
    }

    /**
     * 查询货品详情
     *
     * @param productId
     * @return
     */
    @Override
    public GoodsProduct getProductByProductId(Integer productId) {
        return goodsProductMapper.selectByPrimaryKey(productId);
    }
}
