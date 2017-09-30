package com.chengshi.shop.service.coupon.impl;

import com.chengshi.shop.dao.coupon.CouponGoodsMapper;
import com.chengshi.shop.dao.coupon.CouponMapper;
import com.chengshi.shop.dao.member.MemberCouponMapper;
import com.chengshi.shop.dao.member.MemberRankMapper;
import com.chengshi.shop.model.cart.CartGoods;
import com.chengshi.shop.model.coupon.Coupon;
import com.chengshi.shop.model.coupon.CouponGoods;
import com.chengshi.shop.model.member.MemberCoupon;
import com.chengshi.shop.model.member.MemberRank;
import com.chengshi.shop.service.coupon.CouponService;
import com.chengshi.shop.util.DateFormatUtil;
import com.chengshi.shop.util.EnumUtil;
import com.chengshi.shop.util.MessageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 优惠券接口
 *
 * @author xuxinlong
 * @version 2017年09月11日
 */
@Service
public class CouponServiceImpl implements CouponService {
    @Resource
    private CouponMapper couponMapper;
    @Resource
    private CouponGoodsMapper couponGoodsMapper;
    @Resource
    private MemberCouponMapper memberCouponMapper;
    @Resource
    private MemberRankMapper memberRankMapper;


    /**
     * 优惠券列表
     *
     * @param inMap
     * @return
     */
    @Override
    public List<Coupon> getCouponList(HashMap<String, Object> inMap) {
        return couponMapper.getList(inMap);
    }

    /**
     * 保存优惠券信息
     *
     * @param coupon
     */
    @Override
    @Transactional
    public void saveCoupon(Coupon coupon) {
        if (coupon.getCouponId() != null) {
            couponMapper.updateByPrimaryKeySelective(coupon);
        } else {
            coupon.setCreateTime(new Date());
            couponMapper.insertSelective(coupon);
        }
        //保存优惠券适用商品
        StringBuilder couponGoodsIds = new StringBuilder();
        if (coupon.getCouponType() == EnumUtil.COUPON_TYPE.指定商品.getValue().byteValue()
                && !coupon.getCouponGoodsList().isEmpty()) {
            for (CouponGoods couponGoods : coupon.getCouponGoodsList()) {
                couponGoods.setCouponId(coupon.getCouponId());
                if (couponGoods.getId() != null) {
                    couponGoodsMapper.updateByPrimaryKeySelective(couponGoods);
                } else {
                    couponGoodsMapper.insertSelective(couponGoods);
                }
                couponGoodsIds.append(",").append(couponGoods.getId());
            }
        }
        couponGoodsMapper.deleteNotInCouponGoodsIds(coupon.getCouponId(), couponGoodsIds.length() > 0 ? couponGoodsIds.substring(1) : "0");
    }

    /**
     * 获取优惠券信息
     *
     * @param couponId
     * @return
     */
    @Override
    public Coupon getCoupon(Integer couponId) {
        return couponMapper.selectByPrimaryKey(couponId);
    }

    /**
     * 获取优惠券商品列表
     *
     * @param couponId
     * @return
     */
    @Override
    public List<CouponGoods> getCouponGoodsList(Integer couponId) {
        return couponGoodsMapper.getList(couponId);
    }

    /**
     * 订单可以使用的优惠券列表
     *
     * @param memberId
     * @param totalMoney
     * @param cartGoodsList
     * @return
     */
    @Override
    public List<HashMap<String, Object>> getCanUseCouponList(Integer memberId, BigDecimal totalMoney, List<CartGoods> cartGoodsList) {
        //满足条件的全场通用优惠券
        List<MemberCoupon> memberCouponList = memberCouponMapper.getCanUseCommonCouponList(memberId, totalMoney);
        //会员拥有有效的指定商品优惠券
        List<MemberCoupon> specificCouponList = memberCouponMapper.getSpecificCouponList(memberId);
        for (MemberCoupon coupon : specificCouponList) {
            //订单相关商品金额
            BigDecimal goodsMoney = BigDecimal.ZERO;
            List<Integer> goodsIdList = couponGoodsMapper.getGoodsIdListByCouponId(coupon.getCouponId());
            for (CartGoods goods : cartGoodsList) {
                if (goodsIdList.contains(goods.getGoodsId())) {
                    goodsMoney = goodsMoney.add(goods.getTotalMoney().subtract(goods.getPromotionMoney()));
                }
            }
            //满足优惠券使用条件
            if (goodsMoney.compareTo(coupon.getNeedMoney()) >= 0) {
                memberCouponList.add(coupon);
            }
        }
        List<HashMap<String, Object>> couponList = new ArrayList<>();
        for (MemberCoupon memberCoupon : memberCouponList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("memberCouponId", memberCoupon.getMemberCouponId());
            map.put("couponName", memberCoupon.getCouponName());
            map.put("couponType", memberCoupon.getCouponType());
            map.put("couponPrice", memberCoupon.getMoney());
            map.put("usePrice", memberCoupon.getNeedMoney());
            map.put("useStart", DateFormatUtil.formatDate(memberCoupon.getUseStart(), "yyyy.MM.dd"));
            map.put("useEnd", DateFormatUtil.formatDate(memberCoupon.getUseEnd(), "yyyy.MM.dd"));
            map.put("labelName", memberCoupon.getCouponType() == EnumUtil.COUPON_TYPE.全部商品.getValue().byteValue() ? "全场通用" : "指定商品");
            map.put("content", memberCoupon.getContent());
            couponList.add(map);
        }
        return couponList;
    }

    /**
     * 获取可领取的优惠券
     *
     * @param goodsId
     * @return
     */
    @Override
    public List<Coupon> getCanGetCouponList(Integer goodsId) {
        return couponMapper.getCanGetCouponList(goodsId);
    }

    /**
     * 用户已经拥有某优惠券的数量
     *
     * @param memberId
     * @param couponId
     * @return
     */
    @Override
    public Integer getHasCountByCouponId(Integer memberId, Integer couponId) {
        return memberCouponMapper.getHasCountByCouponId(memberId, couponId);
    }

    /**
     * 会员领取优惠券
     *
     * @param memberId
     * @param coupon
     */
    @Override
    @Transactional
    public void receiveCoupon(Integer memberId, Coupon coupon) {
        MemberCoupon memberCoupon = new MemberCoupon();
        memberCoupon.setCouponId(coupon.getCouponId());
        memberCoupon.setCouponName(coupon.getCouponName());
        memberCoupon.setCouponType(coupon.getCouponType());
        memberCoupon.setStatus(EnumUtil.COMMON_STATUS.有效.getValue().byteValue());
        memberCoupon.setContent(coupon.getContent());
        memberCoupon.setCreateTime(new Date());
        memberCoupon.setGetType(EnumUtil.COUPON_GET_TYPE.自领.getValue().byteValue());
        memberCoupon.setMoney(coupon.getMoney());
        memberCoupon.setNeedMoney(coupon.getNeedMoney());
        memberCoupon.setUseStart(coupon.getUseStart());
        memberCoupon.setUseEnd(coupon.getUseEnd());
        memberCoupon.setMemberId(memberId);
        memberCouponMapper.insertSelective(memberCoupon);
        //增加优惠券领取数量
        couponMapper.addGetCount(coupon.getCouponId(), 1);
        //如果优惠券总数量被领完，将优惠券作废
        if (coupon.getTotalCount() > 0 && coupon.getTotalCount() - coupon.getGetCount() == 1) {
            couponMapper.updateStatus(coupon.getCouponId(), EnumUtil.COUPON_STATUS.已失效.getValue().byteValue());
        }
    }

    /**
     * 检查会员是否可以领取优惠券
     *
     * @param memberId
     * @param coupon
     * @return
     */
    @Override
    public HashMap<String, Object> checkCouponCanGet(Integer memberId, Coupon coupon) {
        if (coupon != null && Byte.valueOf(EnumUtil.COUPON_STATUS.有效.getValue().toString()).equals(coupon.getStatus())
                && coupon.getUseEnd().after(new Date())) {
            if (coupon.getLimitGrade() > 0) {
                MemberRank memberRank = memberRankMapper.getMemberRankByMemberId(memberId);
                MemberRank couponMemberRank = memberRankMapper.selectByPrimaryKey(coupon.getLimitGrade().byteValue());
                if (null == memberRank || couponMemberRank.getMinPoints() > memberRank.getMinPoints()) {
                    return MessageUtils.error("当前会员等级不够，不能领取");
                }
            }
            Integer hasCount = this.getHasCountByCouponId(memberId, coupon.getCouponId());
            if (coupon.getLimitNum() > 0 && coupon.getLimitNum() - hasCount <= 0) {
                return MessageUtils.error("限每人领" + coupon.getLimitNum() + "张，已领完");
            }
            if (coupon.getTotalCount() - coupon.getGetCount() <= 0) {
                return MessageUtils.error("优惠券已领完");
            }
            return MessageUtils.success();
        } else {
            return MessageUtils.error("优惠券已失效");
        }
    }
}
