package com.chengshi.shop.service.member.impl;

import com.chengshi.shop.dao.member.MemberAddressMapper;
import com.chengshi.shop.model.member.MemberAddress;
import com.chengshi.shop.service.member.MemberAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 收货地址
 *
 * @author xuxinlong
 * @version 2017年07月22日
 */
@Service
public class MemberAddressServiceImpl implements MemberAddressService {
    @Autowired
    private MemberAddressMapper memberAddressMapper;

    /**
     * 获取用户默认的收货地址
     *
     * @param memberId
     * @return
     */
    @Override
    public MemberAddress getDefaultAddress(Integer memberId) {
        return memberAddressMapper.getDefaultAddress(memberId);
    }

    /**
     * 保存用户收货地址
     *
     * @param memberAddress
     */
    @Override
    @Transactional
    public void saveMemberAddress(MemberAddress memberAddress) {
        //如果是默认地址
        if (memberAddress.getIsDefault()) {
            memberAddressMapper.changeOtherDefault(memberAddress.getMemberId());
        }
        if (memberAddress.getAddressId() != null) {
            memberAddressMapper.updateByPrimaryKeySelective(memberAddress);
        } else {
            memberAddress.setCreateTime(new Date());
            memberAddressMapper.insertSelective(memberAddress);
        }
    }

    /**
     * 改变其他收货地址为非默认收货地址
     *
     * @param memberId
     */
    @Override
    public void changeOtherDefault(Integer memberId) {
        memberAddressMapper.changeOtherDefault(memberId);
    }

    /**
     * 获取用户所有收货地址
     *
     * @param memberId
     * @return
     */
    @Override
    public List<MemberAddress> getAddressList(Integer memberId) {
        return memberAddressMapper.getListByMemberId(memberId);
    }

    /**
     * 获取收货地址详情
     *
     * @param addressId
     * @return
     */
    @Override
    public MemberAddress getAddressDetail(Integer addressId) {
        return memberAddressMapper.selectByPrimaryKey(addressId);
    }

    /**
     * 删除收货地址
     *
     * @param addressId
     */
    @Override
    public void deleteAddress(Integer addressId) {
        memberAddressMapper.deleteAddress(addressId);
    }

    /**
     * 变更默认收货地址
     *
     * @param memberId
     * @param addressId
     */
    @Override
    @Transactional
    public void changeDefault(Integer memberId, Integer addressId) {
        this.changeOtherDefault(memberId);
        MemberAddress memberAddress = memberAddressMapper.selectByPrimaryKey(addressId);
        memberAddress.setIsDefault(true);
        memberAddressMapper.updateByPrimaryKeySelective(memberAddress);
    }
}
