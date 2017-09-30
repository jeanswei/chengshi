package com.chengshi.shop.dao.member;

import com.chengshi.shop.model.member.Member;
import com.chengshi.shop.model.member.MemberCoupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public interface MemberCouponMapper {
    int deleteByPrimaryKey(Integer memberCouponId);

    int insert(MemberCoupon record);

    int insertSelective(MemberCoupon record);

    MemberCoupon selectByPrimaryKey(Integer memberCouponId);

    int updateByPrimaryKeySelective(MemberCoupon record);

    int updateByPrimaryKey(MemberCoupon record);

    /**
     * 查询领取优惠券的会员列表
     * @param inMap
     * @return
     */
    List<Member> getMemberList(HashMap<String, Object> inMap);

    /**
     * 会员拥有的优惠券数量
     * @param memberId
     * @return
     */
    int getCouponNum(Integer memberId);

    /**
     * 用户订单可以使用的全场优惠券
     * @param memberId
     * @param totalMoney
     * @return
     */
    List<MemberCoupon> getCanUseCommonCouponList(@Param("memberId") Integer memberId, @Param("totalMoney") BigDecimal totalMoney);

    /**
     * 会员有效的优惠券
     * @param memberId
     * @return
     */
    List<MemberCoupon> getCanUseCouponList(Integer memberId);

    /**
     * 会员拥有有效的指定商品优惠券
     * @param memberId
     * @return
     */
    List<MemberCoupon> getSpecificCouponList(Integer memberId);

    /**
     * 用户已经拥有某优惠券的数量
     * @param memberId
     * @param couponId
     * @return
     */
    int getHasCountByCouponId(@Param("memberId") Integer memberId,@Param("couponId") Integer couponId);
}