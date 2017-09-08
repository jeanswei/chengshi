package com.chengshi.shop.controller.goods;

import com.chengshi.shop.model.goods.Goods;
import com.chengshi.shop.service.goods.GoodsService;
import com.chengshi.shop.util.MessageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 商品管理
 *
 * @author xuxinlong
 * @version 2017年09月04日
 */
@RestController
@RequestMapping(value = "admin")
public class GoodsController {
    @Resource
    private GoodsService goodsService;
    @Value("${img_url}")
    private String img_url;

    /**
     * 获取商品列表
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping(value = "getGoodsList")
    public PageInfo<Goods> getGoodsList(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        HashMap<String, Object> inMap = new HashMap<>();
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
        }
        mav.addObject("img_url", img_url);
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
}
