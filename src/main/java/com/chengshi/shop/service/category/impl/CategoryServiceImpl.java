package com.chengshi.shop.service.category.impl;

import com.chengshi.shop.dao.category.CategoryMapper;
import com.chengshi.shop.model.category.Category;
import com.chengshi.shop.service.category.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分类接口
 * @author xuxinlong
 * @version 2017年10月12日
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 获取分类列表
     *
     * @return
     */
    @Override
    public List<Category> getCatList() {
        return categoryMapper.getList();
    }
}
