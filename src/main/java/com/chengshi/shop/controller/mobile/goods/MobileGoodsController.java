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
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @Value("${img_url}")
    private String IMG_URL;

    /**
     * 商品信息
     * @param goodsId
     * @return
     */
    @ApiOperation(value = "获取商品详细信息")
    @ApiImplicitParam(name = "goodsId", value = "商品ID", paramType = "query", required = true, dataType = "int")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @GetMapping(value = "goods")
    public HashMap<String, Object> goods(@RequestParam Integer goodsId) {
        HashMap<String, Object> retMap = MessageUtils.success("查询成功");
        try {
            Goods goods = goodsService.getGoodsByGoodsId(goodsId);
            if (goods.getIsDelete()){
                return MessageUtils.error("商品已失效");
            }
            //图片
            List<GoodsImage> imageList = goodsImageService.getImageList(goodsId);
            for (GoodsImage image : imageList) {
                image.setImgUrl(IMG_URL + image.getImgUrl());
            }
            goods.setImageList(imageList);
            goods.setGoodsImg(IMG_URL + goods.getGoodsImg());
            //货品
            List<GoodsProduct> productList = goodsProductService.getProductList(goodsId);
            for (GoodsProduct goodsProduct : productList){
                goodsProduct.setSpecValueList(goodsSpecService.getSpecValueListByProductId(goodsProduct.getProductId()));
            }
            goods.setProductList(productList);
            goods.setMarktPrice(productList.get(0).getMarktPrice());
            goods.setPrice(productList.get(0).getPrice());
            goods.setStore(productList.get(0).getStore());
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
