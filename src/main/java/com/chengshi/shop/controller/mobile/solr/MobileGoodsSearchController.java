//package com.chengshi.shop.controller.mobile.solr;
//
//import com.chengshi.shop.controller.common.BaseController;
//import com.chengshi.shop.model.goods.GoodsSearchBean;
//import com.chengshi.shop.model.member.Member;
//import com.chengshi.shop.service.solr.SolrSearchService;
//import com.chengshi.shop.util.DateFormatUtil;
//import com.chengshi.shop.util.ImageUtil;
//import com.chengshi.shop.util.MessageUtils;
//import com.chengshi.shop.util.SessionUtils;
//import com.github.pagehelper.PageInfo;
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * 搜索相关
// *
// * @author xuxinlong
// * @version 2017年07月18日
// */
//@RestController
//@RequestMapping(value = "/mobile/search")
//public class MobileGoodsSearchController extends BaseController {
//
//    @Resource
//    private SolrSearchService solrSearchService;
//
//
//    //搜索日志
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//
//    /**
//     * pageNo 页码
//     * pageSize 每页显示的数量
//     * goodsName 查询的商品名称
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/queryGoodsByName")
//    public HashMap<String, Object> queryGoodsByName(HttpServletRequest request) {
//        HashMap<String, Object> retMap = MessageUtils.success();
//        try {
//            Member nowMember = SessionUtils.getMember();
//            long startTime = System.currentTimeMillis();
//            PageInfo<GoodsSearchBean> goodsPage = new PageInfo<>();
//            String goodsName = request.getParameter("goodsName"); //商品名称
//            String catId = request.getParameter("catId"); //分类id
//            String pageNum = request.getParameter("pageNum"); //当前页码
//            String pageSize = request.getParameter("pageSize"); //分页长度
//            String orderby = request.getParameter("orderby"); //排序字段
//            String order = request.getParameter("order"); //排序方式
//
//            if (StringUtils.isNumeric(pageNum))
//                goodsPage.setPageNum(Integer.parseInt(pageNum));
//            if (StringUtils.isNumeric(pageSize))
//                goodsPage.setPageSize(Integer.parseInt(pageSize));
//
//            List<SearchGoodsBean> searchGoodsBeanList = new ArrayList<>();
//            HashMap<String, Object> goodsMap = solrSearchService.queryGoodList(goodsName, catId, orderby, order, goodsPage, SessionUtils.getMallId());
//            List<GoodsSearchBean> goodsSearchBeanList = (List<GoodsSearchBean>) goodsMap.get("goods");
//
//            for (GoodsSearchBean goodsSearchBean : goodsSearchBeanList) {
//                SearchGoodsBean searchGoodsBean = new SearchGoodsBean();
//                searchGoodsBean.setGoodsId(goodsSearchBean.getGoodsId());
//                searchGoodsBean.setGoodsImage(ImageUtil.getImage(goodsSearchBean.getGoodsImage()));
//                searchGoodsBean.setGoodsName(goodsSearchBean.getGoodsName());
//                searchGoodsBean.setSaleCount(goodsSearchBean.getSaleCount());
//                searchGoodsBean.setEvaCount(goodsSearchBean.getEvaCount());
//                searchGoodsBean.setOpenSpec((byte) goodsSearchBean.getOpenSpec());
//                searchGoodsBean.setPrice(NumericUtil.formatMoney(String.valueOf(goodsSearchBean.getPrice())));
//                searchGoodsBean.setStatus((byte) goodsSearchBean.getStatus());
//                searchGoodsBean.setMarktPrice(NumericUtil.formatMoney(String.valueOf(goodsSearchBean.getMktprice())));
//                searchGoodsBean.setCreateTime(DateFormatUtil.formatDate(goodsSearchBean.getCreateTime(), DateFormatUtil.FULL_DATE_FORMAT));
//                searchGoodsBeanList.add(searchGoodsBean);
//            }
//
//            com.github.pagehelper.PageInfo page = new com.github.pagehelper.PageInfo();
//            BeanUtils.copyProperties(page,goodsPage);
//            page.setList(searchGoodsBeanList);
//            retMap.put("data", page);
//        } catch (Exception e) {
//            return MessageUtils.error();
//        }
//        return retMap;
//    }
//}
