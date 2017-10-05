package com.chengshi.shop.service.solr.impl;

import com.chengshi.shop.dao.goods.GoodsMapper;
import com.chengshi.shop.dao.goods.GoodsProductMapper;
import com.chengshi.shop.model.goods.Goods;
import com.chengshi.shop.model.goods.GoodsProduct;
import com.chengshi.shop.model.goods.GoodsSearchBean;
import com.chengshi.shop.service.solr.SolrSearchService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuanrs
 */
@Service
public class SolrSearchServiceImpl implements SolrSearchService {
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private GoodsProductMapper goodsProductMapper;
    @Resource
    private SolrClient client;

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
    @Override
    public HashMap<String, Object> queryGoodList(String q, String catId, String orderby, String order, PageInfo<GoodsSearchBean> goodsPage, Integer mallId) {
        HashMap<String, Object> goodMap = new HashMap<>();
        try {

            // 排序字段
            Map<String, Object> sortfield = new LinkedHashMap<>();
            if (StringUtils.isNotBlank(orderby)) {
                sortfield.put(orderby, order.equals("1"));
            }
            // 搜索条件
            Map<String, Object> field = getField(q, catId, mallId);

            QueryResponse rsp = this.search(field, goodsPage, sortfield);// 查询结果
            if (rsp != null) {
                //TODO
//                List<GoodsSearchBean> a = goodsPage.getResult();
//                goodMap.put("counts", goodsPage.getTotal()); // 总记录数
//                goodMap.put("goods", a);
            }
        } catch (Exception e) {
            System.out.println("搜索出错:" + e.toString());
        }
        return goodMap;
    }


    /**
     * 构造过滤条件为map
     *
     * @param q
     * @param catId
     * @return
     */
    private Map<String, Object> getField(String q, String catId, Integer mallId) {
        // 搜索条件
        Map<String, Object> field = new LinkedHashMap<>();
        if (catId != null && !catId.equals("")) {// 优先分类搜索
            field.put("catId", "\"" + catId + "\""); // 关键字查询
        } else {
            if (q == null || q.equals("")) {
                q = "*"; // 关键字为空 查询所有商品
            }
            field.put("text", q); // 关键字查询
        }
        field.put("mallId", mallId);
        return field;
    }

    /**
     * 创建商品索引
     *
     * @param goodsId
     * @throws Exception
     */
    @Override
    @Transactional
    public void createGoodsIndex(Integer goodsId) throws Exception {

        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", "G_" + goods.getGoodsId());
        doc.addField("goodsId", goods.getGoodsId());
        doc.addField("goodsName", goods.getGoodsName());
        //找到价格最小的货品
        GoodsProduct product = goodsProductMapper.getFirstProductByGoodsId(goods.getGoodsId());
        doc.addField("mktprice", product.getMarktPrice().floatValue());
        doc.addField("price", product.getPrice().floatValue());
        if (StringUtils.isNotBlank(goods.getKeywords())) {
            String keyWordsArray[] = goods.getKeywords().split("\\|");
            for (String keyWords : keyWordsArray) {
                doc.addField("keyWords", keyWords);
            }
        }
        if (StringUtils.isNotBlank(goods.getGoodsImg()))
            doc.addField("goodsImage", goods.getGoodsImg());
        doc.addField("saleCount", goods.getSaleCount());
        doc.addField("evaluateCount", goods.getEvaluateCount());
        doc.addField("isOnSale", goods.getIsOnSale());
        doc.addField("createTime", goods.getCreateTime());
        client.add(doc);
        client.commit(true, true);
        client.optimize(true, true);
    }

    /**
     * 删除商品索引
     *
     * @param goodsId
     * @throws Exception
     */
    @Override
    public void deleteIndexByGoodsId(Integer goodsId) throws Exception {
        client.deleteByQuery("goodsId:" + goodsId);// delete everything!
        client.commit();
    }

    /**
     * solr 搜索
     *
     * @param field
     * @param pagerInfo
     * @param sort
     * @return
     */
    public QueryResponse search(Map<String, Object> field, PageInfo<GoodsSearchBean> pagerInfo, Map<String, Object> sort) {
        if (null == field || field.size() == 0) {
            return null;
        }
        SolrQuery query = new SolrQuery();
        try {
            int i = 1;
            String queryS;
            for (Map.Entry<String, Object> e : field.entrySet()) {
                if (e.getValue().toString().contains(",")) {
                    queryS = e.getKey() + ":" + e.getValue().toString().replace(",", "\" OR " + e.getKey() + ":" + "\"");
                } else {
                    queryS = e.getKey() + ":" + e.getValue().toString();
                }
                if (i == 1) {
                    query = new SolrQuery(queryS);
                    i++;
                } else {
                    query.addFilterQuery(queryS);
                }
            }
            for (Map.Entry<String, Object> e : sort.entrySet()) {
                if ((Boolean) e.getValue()) {
                    query.addSort(e.getKey(), SolrQuery.ORDER.asc);
                } else {
                    query.addSort(e.getKey(), SolrQuery.ORDER.desc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        query.setStart((pagerInfo.getPageNum() - 1) * pagerInfo.getPageSize());
        query.setRows(pagerInfo.getPageSize());
        QueryResponse rsp = null;
        try {
            System.out.println(URLDecoder.decode(query.toString(), "UTF-8"));
            rsp = client.query(query, SolrRequest.METHOD.POST);
            pagerInfo.setTotal(Long.valueOf(rsp.getResults().getNumFound()).intValue());
            List<GoodsSearchBean> a = client.getBinder().getBeans(GoodsSearchBean.class, rsp.getResults());
//            pagerInfo.setResult(a);
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return rsp;
    }
}
