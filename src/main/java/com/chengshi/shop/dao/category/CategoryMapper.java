package com.chengshi.shop.dao.category;

import com.chengshi.shop.model.category.Category;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Short catId);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Short catId);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    /**
     * 获取分类列表
     * @return
     */
    List<Category> getList();
}