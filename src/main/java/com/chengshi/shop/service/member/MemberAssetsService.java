package com.chengshi.shop.service.member;

import com.chengshi.shop.model.member.MemberAccountLog;
import com.chengshi.shop.model.member.MemberCoupon;

import java.math.BigDecimal;
import java.util.List;

/**
 * 会员资产相关接口
 *
 * @author xuxinlong
 * @version 2017年07月26日
 */
public interface MemberAssetsService {


    /**
     * 获取会员余额金额
     *
     * @param memberId
     * @return
     */
    BigDecimal getMemberBalanceByMemberId(Integer memberId);

    /**
     * 获取会员资产明细
     *
     * @param memberId
     * @return
     */
    List<MemberAccountLog> getBalanceHistoryListByMemberId(Integer memberId);

    /**
     * 获取会员优惠券数量
     *
     * @param memberId
     * @return
     */
    int getCouponNum(Integer memberId);

    /**
     * 查询用户拥有的优惠券
     *
     * @param memberId
     * @return
     */
    List<MemberCoupon> getCouponListByMemberId(Integer memberId);

    /**
     * 会员积分明细
     * @param memberId
     * @return
     */
    List<MemberAccountLog> getPointsHistoryListByMemberId(Integer memberId);

    /**
     * 获取会员余额
     * @param memberId
     * @return
     */
    BigDecimal getMemberPointsByMemberId(Integer memberId);
}
