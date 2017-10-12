package com.chengshi.shop.controller.mobile.goods;

import com.chengshi.shop.model.goods.Goods;
import com.chengshi.shop.model.goods.GoodsImage;
import com.chengshi.shop.model.goods.GoodsProduct;
import com.chengshi.shop.model.goods.GoodsSpec;
import com.chengshi.shop.service.goods.GoodsImageService;
import com.chengshi.shop.service.goods.GoodsProductService;
import com.chengshi.shop.service.goods.GoodsService;
import com.chengshi.shop.service.goods.GoodsSpecService;
import com.chengshi.shop.util.MessageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * 移动端商品接口
 *
 * @author xuxinlong
 * @version 2017年09月11日
 */
@Api(value = "goods", description = "商品相关接口")
@RestController
@RequestMapping(value = "mobile")
public class MobileGoodsController {
    @Resource
    private GoodsService goodsService;
    @Resource
    private GoodsImageService goodsImageService;
    @Resource
    private GoodsProductService goodsProductService;
    @Resource
    private GoodsSpecService goodsSpecService;

    /**
     * 商品信息
     *
     * @param goodsId
     * @return
     */
    @ApiOperation(value = "获取商品详细信息")
    @ApiImplicitParam(name = "goodsId", value = "商品ID", paramType = "query", required = true, dataType = "int")
    @GetMapping(value = "goods")
    public HashMap<String, Object> goods(@RequestParam Integer goodsId) {
        HashMap<String, Object> retMap = MessageUtils.success("查询成功");
        try {
            Goods goods = goodsService.getGoodsByGoodsId(goodsId);
            if (goods.getIsDelete()) {
                return MessageUtils.error("商品已失效");
            }
            //图片
            List<GoodsImage> imageList = goodsImageService.getImageList(goodsId);
            for (GoodsImage image : imageList) {
                image.setImgUrl(image.getImgUrl());
            }
            goods.setImageList(imageList);
            goods.setGoodsImg(goods.getGoodsImg());
            //货品
            List<GoodsProduct> productList = goodsProductService.getProductList(goodsId);
            BigDecimal maxPrice = BigDecimal.ZERO;
            BigDecimal minPrice = BigDecimal.ZERO;
            Integer store = 0;
            for (int i = 0; i < productList.size(); i++) {
                productList.get(i).setSpecValueList(goodsSpecService.getSpecValueListByProductId(productList.get(i).getProductId()));
                BigDecimal price = productList.get(i).getPrice();
                if (i == 0) {
                    minPrice = price;
                    maxPrice = price;
                }
                if (price.compareTo(maxPrice) > 0) {
                    maxPrice = price;
                }
                if (price.compareTo(minPrice) < 0) {
                    minPrice = price;
                }
                store += productList.get(i).getStore();
            }
            goods.setProductList(productList);
            goods.setMaxPrice(maxPrice);
            goods.setMinPrice(minPrice);
            goods.setPrice(minPrice);
            goods.setStore(store);
            //规格
            List<GoodsSpec> specList = goodsSpecService.getSpecListByGoodsId(goodsId);
            goods.setSpecList(specList);
            retMap.put("goods", goods);
        } catch (Exception e) {
            retMap = MessageUtils.error("商品不存在");
        }
        return retMap;
    }

}
