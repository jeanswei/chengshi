package com.chengshi.shop.controller.mobile.member;

import com.alibaba.fastjson.JSON;
import com.chengshi.shop.controller.common.BaseController;
import com.chengshi.shop.model.goods.GoodsEvaluate;
import com.chengshi.shop.model.member.Member;
import com.chengshi.shop.service.goods.GoodsEvaluateService;
import com.chengshi.shop.util.MessageUtils;
import com.chengshi.shop.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 商品评价相关
 *
 * @author 徐新龙
 * @version 2017年3月2日
 */
@Api(value = "evaluate", description = "商品评价相关接口")
@RestController
@RequestMapping(value = "/mobile/evaluate")
public class MobileMemberEvaluateController extends BaseController {

    @Resource
    private GoodsEvaluateService goodsEvaluateService;


    /**
     * 保存订单评价
     *
     * @param orderId
     * @return
     */
    @ApiOperation(value = "保存订单商品评价")
    @PostMapping(value = "/saveEvaluate")
    public HashMap<String, Object> saveEvaluate(@RequestParam Integer orderId, HttpServletRequest request) {
        HashMap<String, Object> retMap = MessageUtils.success("评价成功");
        try {
            Member nowMember = SessionUtils.getMember();
            String evaluateString = request.getParameter("evaluateString");
            List<GoodsEvaluate> evaluateList = new ArrayList<>();
            if (StringUtils.isNotBlank(evaluateString)) {
                evaluateList = JSON.parseArray(evaluateString, GoodsEvaluate.class);
            }
            goodsEvaluateService.saveGoodsEvaluate(evaluateList, orderId, nowMember.getMemberId());
        } catch (Exception e) {
            return MessageUtils.error("评价失败!");
        }
        return retMap;
    }
}
