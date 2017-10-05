package com.chengshi.shop.service.member.impl;

import com.chengshi.shop.dao.member.MemberAccountLogMapper;
import com.chengshi.shop.dao.member.MemberCouponMapper;
import com.chengshi.shop.dao.member.MemberMapper;
import com.chengshi.shop.model.member.MemberAccountLog;
import com.chengshi.shop.model.member.MemberCoupon;
import com.chengshi.shop.service.member.MemberAssetsService;
import com.chengshi.shop.util.EnumUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 会员个人资产相关
 *
 * @author xuxinlong
 * @version 2017年07月26日
 */
@Service
public class MemberAssetsServiceImpl implements MemberAssetsService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberCouponMapper memberCouponMapper;
    @Resource
    private MemberAccountLogMapper memberAccountLogMapper;


    /**
     * 获取会员账户余额
     *
     * @param memberId
     * @return
     */
    @Override
    public BigDecimal getMemberBalanceByMemberId(Integer memberId) {
        return memberMapper.selectByPrimaryKey(memberId).getMemberBalance();
    }

    /**
     * 获取会员余额明细
     *
     * @param memberId
     * @return
     */
    @Override
    public List<MemberAccountLog> getBalanceHistoryListByMemberId(Integer memberId) {
        return memberAccountLogMapper.getLogListByMemberId(memberId, EnumUtil.ACCOUNT_TYPE.会员余额.getValue().byteValue());
    }

    /**
     * 获取会员优惠券数量
     *
     * @param memberId
     * @return
     */
    @Override
    public int getCouponNum(Integer memberId) {
        return memberCouponMapper.getCouponNum(memberId);
    }

    /**
     * 查询用户拥有的优惠券
     *
     * @param memberId
     * @return
     */
    @Override
    public List<MemberCoupon> getCouponListByMemberId(Integer memberId) {
        return memberCouponMapper.getCanUseCouponList(memberId);
    }

    /**
     * 会员积分明细
     *
     * @param memberId
     * @return
     */
    @Override
    public List<MemberAccountLog> getPointsHistoryListByMemberId(Integer memberId) {
        return memberAccountLogMapper.getLogListByMemberId(memberId, EnumUtil.ACCOUNT_TYPE.会员积分.getValue().byteValue());
    }

    /**
     * 获取会员积分
     *
     * @param memberId
     * @return
     */
    @Override
    public BigDecimal getMemberPointsByMemberId(Integer memberId) {
        return memberMapper.selectByPrimaryKey(memberId).getMemberPoints();
    }

}
