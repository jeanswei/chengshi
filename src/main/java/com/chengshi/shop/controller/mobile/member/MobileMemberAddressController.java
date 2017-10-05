package com.chengshi.shop.controller.mobile.member;

import com.chengshi.shop.controller.common.BaseController;
import com.chengshi.shop.model.member.Member;
import com.chengshi.shop.model.member.MemberAddress;
import com.chengshi.shop.service.member.MemberAddressService;
import com.chengshi.shop.util.MessageUtils;
import com.chengshi.shop.util.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 会员地址管理
 *
 * @author 徐新龙
 * @version 2016年12月5日 下午2:27:40
 */
@Api(value = "address", description = "会员收货地址相关接口")
@RestController
@RequestMapping(value = "/mobile/address")
public class MobileMemberAddressController extends BaseController {
    @Resource
    private MemberAddressService memberAddressService;

    /**
     * 保存收货地址的请求
     *
     * @param memberAddress
     * @return
     */
    @ApiOperation(value = "保存收货地址信息")
    @ApiImplicitParam(name = "memberAddress", required = true, value = "收货地址", paramType = "path", dataType = "MemberAddress")
    @PostMapping(value = "/saveAddress")
    public HashMap<String, Object> saveAddress(@ModelAttribute("MemberAddress") MemberAddress memberAddress) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            Member nowMember = SessionUtils.getMember();
            memberAddress.setMemberId(nowMember.getMemberId());
            memberAddressService.saveMemberAddress(memberAddress);
        } catch (Exception e) {
            return MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 获取收货地址列表的ajax请求
     *
     * @return
     */
    @ApiOperation(value = "获取会员收货地址列表")
    @GetMapping(value = "/getAddressList")
    public HashMap<String, Object> getAddressList() {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            Member nowMember = SessionUtils.getMember();
            List<MemberAddress> addressList = memberAddressService.getAddressList(nowMember.getMemberId());
            retMap.put("addressList", addressList);
        } catch (Exception e) {
            return MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 在编辑收货地址界面获取收货地址信息
     *
     * @param addressId
     * @return
     */
    @ApiOperation(value = "获取收货地址详情")
    @ApiImplicitParam(name = "addressId", required = true, value = "收货地址Id", paramType = "query", dataType = "int")
    @GetMapping(value = "/getAddressDetail")
    public HashMap<String, Object> getAddressDetail(@RequestParam Integer addressId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            MemberAddress address = memberAddressService.getAddressDetail(addressId);
            retMap.put("address", address);
        } catch (Exception e) {
            return MessageUtils.error();
        }
        return retMap;

    }

    /**
     * 删除收货地址的请求
     *
     * @param addressId
     * @return
     */
    @ApiOperation(value = "删除收货地址")
    @ApiImplicitParam(name = "addressId", required = true, value = "收货地址Id", paramType = "query", dataType = "int")
    @PostMapping(value = "/deleteAddress")
    public HashMap<String, Object> deleteAddress(@RequestParam Integer addressId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            memberAddressService.deleteAddress(addressId);
        } catch (Exception e) {
            return MessageUtils.error();
        }
        return retMap;
    }

    /**
     * 修改默认收货地址的请求
     *
     * @param addressId
     * @return
     */
    @ApiOperation(value = "设置为默认收货地址")
    @ApiImplicitParam(name = "addressId", required = true, value = "收货地址Id", paramType = "query", dataType = "int")
    @PostMapping(value = "/changeDefaultAddress")
    public HashMap<String, Object> changeDefaultAddress(@RequestParam Integer addressId) {
        HashMap<String, Object> retMap = MessageUtils.success();
        try {
            Member nowMember = SessionUtils.getMember();
            memberAddressService.changeDefault(nowMember.getMemberId(), addressId);
        } catch (Exception e) {
            return MessageUtils.error();
        }
        return retMap;
    }
}
