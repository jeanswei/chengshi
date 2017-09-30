package com.chengshi.shop.service.solr;


import com.chengshi.shop.model.goods.GoodsSearchBean;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;

/**
 * solr查询
 *
 * @author Administrator
 */
public interface SolrSearchService {

    /**
     * 根据关键字搜索商品
     *
     * @param q
     * @param catId
     * @param orderby
     * @param order
     * @param goodsPage
     * @param mallId
     * @return
     */
    HashMap<String, Object> queryGoodList(String q, String catId, String orderby, String order, PageInfo<GoodsSearchBean> goodsPage, Integer mallId);

    /**
     * 创建商品索引
     * @param goodsId
     * @throws Exception
     */
    void createGoodsIndex(Integer goodsId) throws Exception;

    /**
     * 删除商品索引
     * @param goodsId
     * @throws Exception
     */
    void deleteIndexByGoodsId(Integer goodsId) throws Exception;
}