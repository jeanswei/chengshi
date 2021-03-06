package com.chengshi.shop.service.goods;

import com.chengshi.shop.model.goods.GoodsImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品图片相关接口
 * @author xuxinlong
 * @version 2017年09月09日
 */
public interface GoodsImageService {

    /**
     * 保存商品图片
     * @param goodsImage
     */
    void saveGoodsImage(GoodsImage goodsImage);

    /**
     * 获取商品图片列表
     * @param goodsId
     * @return
     */
    List<GoodsImage> getImageList(Integer goodsId);

    /**
     * 删除多余的商品图片
     * @param goodsId
     * @param imgIds
     */
    void deleteNotInImgIds(Integer goodsId, String imgIds);
}
