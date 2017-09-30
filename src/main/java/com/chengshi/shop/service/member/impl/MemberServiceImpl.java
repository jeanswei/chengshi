package com.chengshi.shop.service.member.impl;

import com.chengshi.shop.dao.member.MemberMapper;
import com.chengshi.shop.dao.member.MemberRankMapper;
import com.chengshi.shop.model.member.Member;
import com.chengshi.shop.model.member.MemberRank;
import com.chengshi.shop.service.member.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 会员相关信息
 *
 * @author xuxinlong
 * @version 2017年07月21日
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberRankMapper memberRankMapper;


    /**
     * 根据微信openid查询用户信息
     *
     * @param openId
     * @return
     */
    @Override
    public Member getMemberByOpenId(String openId) {
        return memberMapper.getMemberByOpenId(openId);
    }

    /**
     * 保存会员/注册新会员
     *
     * @param member
     */
    @Override
    @Transactional
    public void saveMember(Member member) {
        if (member.getMemberId() != null) {
            memberMapper.updateByPrimaryKeySelective(member);
        } else {
            //注册会员
            member.setCreateTime(new Date());
            MemberRank memberRank = memberRankMapper.findMinMemberRank();
            if (memberRank != null) {
                member.setMemberRank(memberRank.getRankId());
            }
            memberMapper.insertSelective(member);

        }
    }

    /**
     * 查询会员个人信息
     *
     * @param memberId
     * @return
     */
    @Override
    public Member getMemberByMemberId(Integer memberId) {
        return memberMapper.selectByPrimaryKey(memberId);
    }

    /**
     * 查询会员列表数据
     *
     * @param inMap
     * @return
     */
    @Override
    public List<Member> getMemberList(HashMap<String, Object> inMap) {
        return memberMapper.getList(inMap);
    }

}
