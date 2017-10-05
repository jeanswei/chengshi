package com.chengshi.shop.controller.mobile.member;

import com.chengshi.shop.controller.common.BaseController;
import com.chengshi.shop.model.member.Member;
import com.chengshi.shop.model.member.MemberAccountLog;
import com.chengshi.shop.model.member.MemberCoupon;
import com.chengshi.shop.service.member.MemberAssetsService;
import com.chengshi.shop.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 会员个人资产
 *
 * @author xuxinlong
 * @version 2017年08月01日
 */
@Api(value = "asset", description = "会员个人资产相关接口")
@RestController
@RequestMapping(value = "/mobile/asset")
public class MobileMemberAssetController extends BaseController {
    @Resource
    private MemberAssetsService memberAssetsService;

    /**
     * 获取我的资产(积分数、优惠券、余额)
     *
     * @return
     */
    @ApiOperation(value = "会员资产基本信息")
    @GetMapping(value = "/getAssetInfo")
    public HashMap<String, Object> getAssetInfo() {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            Integer memberId = SessionUtils.getMember().getMemberId();

            List<HashMap<String, Object>> assetList = new ArrayList<>();
            HashMap<String, Object> map = new HashMap<>();
            BigDecimal points = memberAssetsService.getMemberPointsByMemberId(memberId);
            map.put("id", "points");
            map.put("name", "积分");//积分
            map.put("value", points);//积分余额
            assetList.add(map);

            Integer couponNum = memberAssetsService.getCouponNum(memberId);
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("id", "coupon");
            map1.put("name", "优惠券");//优惠券
            map1.put("value", couponNum);//优惠券数量
            assetList.add(map1);

            BigDecimal balance = memberAssetsService.getMemberBalanceByMemberId(memberId);
            HashMap<String, Object> map2 = new HashMap<>();
            map2.put("id", "balance");
            map2.put("name", "余额");//账户
            map2.put("value", balance);//余额
            assetList.add(map2);
            retMap.put("assetList", assetList);
        } catch (Exception e) {
            retMap = MessageUtils.error();
        }
        return retMap;
    }


    /**
     * 获取积分明细的ajax请求
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "会员积分明细列表")
    @GetMapping(value = "/getMemberPointsInfo")
    public HashMap<String, Object> getMemberPointsInfo(HttpServletRequest request) {

        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            List<HashMap<String, Object>> pointsList = new ArrayList<>();
            Member nowMember = SessionUtils.getMember();
            PageHelper.startPage(request);
            List<MemberAccountLog> pointsHistoryList = memberAssetsService.getPointsHistoryListByMemberId(nowMember.getMemberId());
            for (MemberAccountLog memberAccountLog : pointsHistoryList) {
                HashMap<String, Object> map = new HashMap<>();

                map.put("count", memberAccountLog.getNumber());
                map.put("createTime", DateFormatUtil.formatDate(memberAccountLog.getCreateTime(), DateFormatUtil.FULL_DATE_FORMAT));
                map.put("content", memberAccountLog.getContent());
                pointsList.add(map);
            }
            PageInfo pageList = new PageInfo<>(pointsHistoryList);
            pageList.setList(pointsList);
            retMap.put("data", pageList);
        } catch (Exception e) {
            retMap = MessageUtils.error("获取积分明细失败！");
        }
        return retMap;
    }

    /**
     * 获取余额使用明细的ajax请求
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "会员余额明细列表")
    @GetMapping(value = "/getMemberBalanceInfo")
    public HashMap<String, Object> getMemberBalanceInfo(HttpServletRequest request) {

        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            Member nowMember = SessionUtils.getMember();
            PageHelper.startPage(request);
            List<MemberAccountLog> list = memberAssetsService.getBalanceHistoryListByMemberId(nowMember.getMemberId());
            List<HashMap<String, Object>> balanceList = new ArrayList<>();

            for (MemberAccountLog memberAccountLog : list) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("count", memberAccountLog.getNumber());
                map.put("content", memberAccountLog.getContent());
                map.put("createTime", DateFormatUtil.formatDate(memberAccountLog.getCreateTime(), DateFormatUtil.FULL_DATE_FORMAT));
                balanceList.add(map);
            }
            PageInfo pageList = new PageInfo<>(list);
            pageList.setList(balanceList);
            retMap.put("data", pageList);
        } catch (Exception e) {
            retMap = MessageUtils.error("获取账户明细失败！");
        }
        return retMap;
    }

    /**
     * 我的优惠券列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "会员拥有优惠券列表")
    @GetMapping(value = "/getCouponList")
    public HashMap<String, Object> getCouponList(HttpServletRequest request) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            PageHelper.startPage(request);
            Member nowMember = SessionUtils.getMember();
            List<MemberCoupon> memberCouponList = memberAssetsService.getCouponListByMemberId(nowMember.getMemberId());
            List<HashMap<String, Object>> couponList = new ArrayList<>();

            for (MemberCoupon memberCoupon : memberCouponList) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("memberCouponId", memberCoupon.getMemberCouponId());
                map.put("couponName", memberCoupon.getCouponName());
                map.put("couponPrice", memberCoupon.getMoney());
                map.put("usePrice", memberCoupon.getNeedMoney());
                map.put("useStart", DateFormatUtil.formatDate(memberCoupon.getUseStart(), "yyyy.MM.dd"));
                map.put("useEnd", DateFormatUtil.formatDate(memberCoupon.getUseEnd(), "yyyy.MM.dd"));
                map.put("status", memberCoupon.getStatus());
                map.put("labelName", memberCoupon.getCouponType() == EnumUtil.COUPON_TYPE.全部商品.getValue().byteValue() ? "全场通用" : "指定商品");
                map.put("content", memberCoupon.getContent());
                map.put("couponType", memberCoupon.getCouponType());
                couponList.add(map);
            }
            PageInfo pageList = new PageInfo<>(memberCouponList);
            pageList.setList(couponList);
            retMap.put("data", pageList);
        } catch (Exception e) {
            retMap = MessageUtils.error("获取优惠券列表失败！");
        }
        return retMap;
    }

    /**
     * 获取会员余额金额
     * @return
     */
    @ApiOperation(value = "获取会员余额剩余金额")
    @GetMapping(value = "/getMemberBalance")
    public HashMap<String, Object> getMemberBalance(){
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            Member nowMember = SessionUtils.getMember();
            BigDecimal balance = memberAssetsService.getMemberBalanceByMemberId(nowMember.getMemberId());
            retMap.put("balance", balance);
        } catch (Exception e){
            retMap = MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 获取会员积分数量
     * @return
     */
    @ApiOperation(value = "获取会员积分数量")
    @GetMapping(value = "/getMemberPoints")
    public HashMap<String, Object> getMemberPoints(){
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            Member nowMember = SessionUtils.getMember();
            BigDecimal points = memberAssetsService.getMemberPointsByMemberId(nowMember.getMemberId());
            retMap.put("points", points);
        } catch (Exception e){
            retMap = MessageUtils.error();
        }
        return retMap;
    }
}
