package com.chengshi.shop.dao.member;

import com.chengshi.shop.model.member.MemberRank;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface MemberRankMapper {
    int deleteByPrimaryKey(Byte rankId);

    int insert(MemberRank record);

    int insertSelective(MemberRank record);

    MemberRank selectByPrimaryKey(Byte rankId);

    int updateByPrimaryKeySelective(MemberRank record);

    int updateByPrimaryKey(MemberRank record);

    /**
     * 获取用户等级信息
     * @param memberId
     * @return
     */
    MemberRank getMemberRankByMemberId(Integer memberId);

    /**
     * 获取最小的等级
     * @return
     */
    MemberRank findMinMemberRank();

    /**
     * 获取会员对应等级的折扣
     * @param memberId
     * @return
     */
    BigDecimal getMemberDiscount(Integer memberId);
}