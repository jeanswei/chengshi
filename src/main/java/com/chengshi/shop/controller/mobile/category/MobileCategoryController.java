package com.chengshi.shop.controller.mobile.category;

import com.chengshi.shop.controller.common.BaseController;
import com.chengshi.shop.model.category.Category;
import com.chengshi.shop.model.goods.Goods;
import com.chengshi.shop.service.category.CategoryService;
import com.chengshi.shop.service.goods.GoodsService;
import com.chengshi.shop.util.MessageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * 商城分类相关
 *
 * @author xuxinlong
 * @version 2017年10月11日
 */
@Api(description = "商城分类相关接口")
@RestController
@RequestMapping("/mobile/category")
public class MobileCategoryController extends BaseController{
    @Resource
    private CategoryService categoryService;
    @Resource
    private GoodsService goodsService;

    /**
     * 获取分类列表
     *
     * @return
     */
    @ApiOperation(value = "获取分类列表")
    @GetMapping("/getCatList")
    public HashMap<String, Object> getCatList() {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            List<Category> catList = categoryService.getCatList();
            retMap.put("list", catList);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 获取分类下的商品列表
     *
     * @param catId
     * @return
     */
    @ApiOperation(value = "获取分类下的商品列表")
    @ApiImplicitParam(name = "catId", value = "分类ID", paramType = "query", dataType = "int")
    @GetMapping("/getGoodsListByCatId")
    public HashMap<String, Object> getGoodsListByCatId(@RequestParam(required = false) Integer catId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            HashMap<String, Object> inMap = new HashMap<>();
            inMap.put("isOnSale", 1);
            inMap.put("catId", catId);
            this.startPage();
            List<Goods> goodsList = goodsService.getGoodsList(inMap);
            retMap.put("data", new PageInfo<>(goodsList));
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }
}
