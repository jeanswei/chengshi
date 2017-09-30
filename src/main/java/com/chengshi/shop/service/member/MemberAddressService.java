package com.chengshi.shop.service.member;


import com.chengshi.shop.model.member.MemberAddress;

import java.util.List;

/**
 * 会员收货地址
 * @author xuxinlong
 * @version 2017年07月22日
 */
public interface MemberAddressService {
    /**
     * 获取用户默认的收货地址
     * @param memberId
     * @return
     */
    MemberAddress getDefaultAddress(Integer memberId);

    /**
     * 保存用户收货地址
     * @param memberAddress
     */
    void saveMemberAddress(MemberAddress memberAddress);

    /**
     * 改变其他收货地址为非默认收货地址
     * @param memberId
     */
    void changeOtherDefault(Integer memberId);

    /**
     * 获取用户所有收货地址
     * @param memberId
     * @return
     */
    List<MemberAddress> getAddressList(Integer memberId);

    /**
     * 获取收货地址详情
     * @param addressId
     * @return
     */
    MemberAddress getAddressDetail(Integer addressId);

    /**
     * 删除收货地址
     * @param addressId
     */
    void deleteAddress(Integer addressId);

    /**
     * 变更默认收货地址
     * @param memberId
     * @param addressId
     */
    void changeDefault(Integer memberId, Integer addressId);
}
