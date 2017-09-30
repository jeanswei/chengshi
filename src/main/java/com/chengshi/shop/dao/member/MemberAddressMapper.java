package com.chengshi.shop.dao.member;

import com.chengshi.shop.model.member.MemberAddress;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberAddressMapper {
    int deleteByPrimaryKey(Integer addressId);

    int insert(MemberAddress record);

    int insertSelective(MemberAddress record);

    MemberAddress selectByPrimaryKey(Integer addressId);

    int updateByPrimaryKeySelective(MemberAddress record);

    int updateByPrimaryKey(MemberAddress record);

    /**
     * 查询会员的默认收货地址
     * @param memberId
     * @return
     */
    MemberAddress getDefaultAddress(Integer memberId);

    /**
     * 改变其他收货地址为非默认
     * @param memberId
     */
    void changeOtherDefault(Integer memberId);

    /**
     * 查询会员所有收货地址
     * @param memberId
     * @return
     */
    List<MemberAddress> getListByMemberId(Integer memberId);

    /**
     * 删除收货地址
     * @param memberPostAddressId
     */
    void deleteAddress(Integer memberPostAddressId);
}