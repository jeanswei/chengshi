package com.chengshi.shop.service.member;


import com.chengshi.shop.model.member.Member;

import java.util.HashMap;
import java.util.List;

/**
 * 会员信息相关接口
 * @author xuxinlong
 * @version 2017年07月21日
 */
public interface MemberService {
    /**
     * 根据微信openid查询用户信息
     * @param openId
     * @return
     */
    Member getMemberByOpenId(String openId);

    /**
     * 保存会员
     * @param member
     */
    void saveMember(Member member);

    /**
     * 查询会员个人信息
     * @param memberId
     * @return
     */
    Member getMemberByMemberId(Integer memberId);

    /**
     * 查询会员列表数据
     * @param inMap
     * @return
     */
	List<Member> getMemberList(HashMap<String, Object> inMap);

}
