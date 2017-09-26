package com.chengshi.shop.controller.mobile.goods;

import com.chengshi.shop.model.goods.Goods;
import com.chengshi.shop.model.goods.GoodsImage;
import com.chengshi.shop.service.goods.GoodsImageService;
import com.chengshi.shop.service.goods.GoodsService;
import com.chengshi.shop.util.MessageUtils;
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
 * @author xuxinlong
 * @version 2017年09月11日
 */
@RestController
@RequestMapping(value = "mobile")
public class MobileGoodsController {
    @Resource
    private GoodsService goodsService;
    @Resource
    private GoodsImageService goodsImageService;

    @Value("${img_url}")
    private String IMG_URL;

    /**
     * 商品信息
     * @param goodsId
     * @return
     */
    @GetMapping(value = "goods")
    public HashMap<String, Object> goods(@RequestParam Integer goodsId){
        HashMap<String, Object> retMap = MessageUtils.success("查询成功");
        try {
            Goods goods = goodsService.getGoodsByGoodsId(goodsId);
            List<GoodsImage> imageList = goodsImageService.getImageList(goodsId);
            for (GoodsImage image : imageList){
                image.setThumbnail(IMG_URL + image.getImgUrl());
            }
            goods.setImageList(imageList);
            goods.setThumbnail(IMG_URL + goods.getGoodsImg());
            retMap.put("goods", goods);
        } catch (Exception e){
            retMap = MessageUtils.error("商品不存在");
        }
        return retMap;
    }

}
