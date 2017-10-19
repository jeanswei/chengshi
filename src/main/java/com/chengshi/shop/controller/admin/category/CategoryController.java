package com.chengshi.shop.controller.admin.category;

import com.chengshi.shop.controller.common.BaseController;
import com.chengshi.shop.model.category.Category;
import com.chengshi.shop.service.category.CategoryService;
import com.chengshi.shop.util.MessageUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 商品分类相关
 *
 * @author xuxinlong
 * @version 2017年10月11日
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryController extends BaseController{
    @Resource
    private CategoryService categoryService;

    /**
     * 获取分类列表
     *
     * @return
     */
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

}
