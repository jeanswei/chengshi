package com.chengshi.shop.dao.member;

import com.chengshi.shop.model.member.MemberAccountLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberAccountLogMapper {
    int deleteByPrimaryKey(Integer logId);

    int insert(MemberAccountLog record);

    int insertSelective(MemberAccountLog record);

    MemberAccountLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(MemberAccountLog record);

    int updateByPrimaryKey(MemberAccountLog record);

    /**
     * 获取账户变动记录
     *
     * @param memberId
     * @param accountType
     * @return
     */
    List<MemberAccountLog> getLogListByMemberId(@Param("memberId") Integer memberId, @Param("accountType") Byte accountType);
}