package com.chengshi.shop.service.category;

import com.chengshi.shop.model.category.Category;

import java.util.List;

/**
 * 分类接口
 * @author xuxinlong
 * @version 2017年10月12日
 */
public interface CategoryService {

    /**
     * 获取分类列表
     * @return
     */
    List<Category> getCatList();
}
