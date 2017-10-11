package com.chengshi.shop.controller.admin.goods;

import com.chengshi.shop.model.goods.Goods;
import com.chengshi.shop.model.goods.GoodsImage;
import com.chengshi.shop.model.goods.GoodsProduct;
import com.chengshi.shop.model.goods.GoodsSpec;
import com.chengshi.shop.service.goods.GoodsImageService;
import com.chengshi.shop.service.goods.GoodsProductService;
import com.chengshi.shop.service.goods.GoodsService;
import com.chengshi.shop.service.goods.GoodsSpecService;
import com.chengshi.shop.util.MessageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * 商品管理
 *
 * @author xuxinlong
 * @version 2017年09月04日
 */
@RestController
@RequestMapping(value = "admin/goods")
public class GoodsController {
    @Resource
    private GoodsService goodsService;
    @Resource
    private GoodsSpecService goodsSpecService;
    @Resource
    private GoodsImageService goodsImageService;
    @Resource
    private GoodsProductService goodsProductService;

    /**
     * 获取商品列表
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping(value = "getGoodsList")
    public PageInfo<Goods> getGoodsList(HttpServletRequest request, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        HashMap<String, Object> inMap = new HashMap<>();
        inMap.put("isOnSale", request.getParameter("isOnSale"));
        List<Goods> goodsList = goodsService.getGoodsList(inMap);
        return new PageInfo<>(goodsList);
    }

    /**
     * 商品列表页面
     *
     * @return
     */
    @GetMapping(value = "goodsList")
    public ModelAndView goodsList() {
        return new ModelAndView("admin/goods/goodsList");
    }

    /**
     * 商品编辑页面
     *
     * @return
     */
    @GetMapping(value = "goodsForm")
    public ModelAndView goodsForm(@RequestParam(required = false) Integer goodsId) {
        ModelAndView mav = new ModelAndView("admin/goods/goodsForm");
        Goods goods = new Goods();
        if (goodsId != null) {
            goods = goodsService.getGoodsByGoodsId(goodsId);
            List<GoodsImage> imageList = goodsImageService.getImageList(goodsId);
            goods.setImageList(imageList);

            List<GoodsSpec> specList = goodsSpecService.getSpecListByGoodsId(goodsId);
            goods.setSpecList(specList);

            List<GoodsProduct> productList = goodsProductService.getProductList(goodsId);
            for (GoodsProduct goodsProduct : productList){
                goodsProduct.setSpecValueList(goodsSpecService.getSpecValueListByProductId(goodsProduct.getProductId()));
            }
            goods.setProductList(productList);
        }
        mav.addObject("goods", goods);
        return mav;
    }

    /**
     * 逻辑删除商品
     *
     * @param goodsId
     * @return
     */
    @PostMapping(value = "deleteGoods")
    public HashMap<String, Object> deleteGoods(@RequestParam Integer goodsId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            goodsService.deleteGoods(goodsId);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 保存商品信息
     * @param goods
     * @return
     */
    @PostMapping(value = "saveGoods")
    public HashMap<String, Object> saveGoods(@ModelAttribute Goods goods) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            if (goods.getImageList().isEmpty()){
                return MessageUtils.error("请上传商品图片");
            }
            goodsService.saveGoods(goods);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 选择商品页面
     * @return
     */
    @GetMapping(value = "goodsChooseList")
    public ModelAndView goodsChooseList() {
        return new ModelAndView("admin/goods/goodsChooseList");
    }

}
