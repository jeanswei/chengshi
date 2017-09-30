package com.chengshi.shop.dao.member;

import com.chengshi.shop.model.member.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public interface MemberMapper {
    int deleteByPrimaryKey(Integer memberId);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Integer memberId);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    /**
     * 增加会员积分
     * @param memberId
     * @param points
     */
    void addMemberPoints(@Param("memberId") Integer memberId, @Param("points") BigDecimal points);

    /**
     * 减少会员积分
     * @param memberId
     * @param points
     */
    void subMemberPoints(@Param("memberId") Integer memberId, @Param("points") BigDecimal points);

    /**
     * 增加会员余额
     * @param memberId
     * @param balance
     */
    void addMemberBalance(@Param("memberId") Integer memberId, @Param("balance") BigDecimal balance);

    /**
     * 减少会员余额
     * @param memberId
     * @param balance
     */
    void subMemberBalance(@Param("memberId") Integer memberId, @Param("balance") BigDecimal balance);

    /**
     * 根据openId查询会员
     * @param openId
     * @return
     */
    Member getMemberByOpenId(String openId);

    /**
     * 查询会员列表
     * @param inMap
     * @return
     */
    List<Member> getList(HashMap<String, Object> inMap);

}