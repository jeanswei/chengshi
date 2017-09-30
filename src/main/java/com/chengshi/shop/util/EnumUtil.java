package com.chengshi.shop.util;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 枚举类
 *
 * @author xuxinlong
 * @version 2017年07月11日
 */
public class EnumUtil extends EnumUtils {


    /**
     * 通用状态
     */
    public enum COMMON_STATUS {
        有效(1), 无效(2);
        private Integer value;

        COMMON_STATUS(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 订单状态
     */
    public enum ORDER_STATUS {
        待付款(1), 待审核(2), 待发货(3), 待收货(4), 已退款(9), 交易成功(10), 交易关闭(11);
        private Integer value;

        ORDER_STATUS(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 支付方式
     */
    public enum PAYMENT_METHOD {
        未支付(0), 微信(1), 支付宝(2), 积分(3), 优惠券(4), 余额(5), 线下支付(6), 会员折扣(9), 活动优惠(10);
        private Integer value;

        PAYMENT_METHOD(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 子订单状态
     */
    public enum ORDER_ITEM_STATUS {
        未发货(1), 已发货(2), 退款中(3), 确认收货(4), 已退款(5);
        private Integer value;

        ORDER_ITEM_STATUS(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 售后类型
     */
    public enum RETURN_TYPE {
        换货(1), 退货(2);
        private Integer value;

        RETURN_TYPE(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 签收状态
     */
    public enum IS_SIGN {
        未签收(1), 签收(2);
        private Integer value;

        IS_SIGN(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 审核状态
     */
    public enum AUDIT_STATUS {
        未审核(1), 审核通过(2), 审核不通过(3);
        private Integer value;

        AUDIT_STATUS(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 评价分数
     */
    public enum EVALUATE_SCORE {
        好评(1), 中评(2), 差评(3);
        private Integer value;

        EVALUATE_SCORE(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 评价状态
     */
    public enum EVALUATE_STATUS {
        未评价(1), 已评价(2);
        private Integer value;

        EVALUATE_STATUS(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 退货来源
     */
    public enum SOURCE_TYPE {
        运营退款(1), 用户退货(2);
        private Integer value;

        SOURCE_TYPE(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 优惠券适用范围
     */
    public enum COUPON_TYPE {
        全部商品(1), 指定商品(2);
        private Integer value;

        COUPON_TYPE(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 会员账户类型
     */
    public enum ACCOUNT_TYPE {
        会员积分(1), 等级经验(2), 会员余额(3);
        private Integer value;

        ACCOUNT_TYPE(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 会员入账类型
     */
    public enum ACCOUNT_FROM_TYPE {
        商城订单(1), 订单退还(2), 兑换(3), 充值(4), 签到(5), 分享(6), 注册(7), 提现(8), 提现退还(9);
        private Integer value;

        ACCOUNT_FROM_TYPE(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 优惠券获取方式
     */
    public enum COUPON_GET_TYPE {
        自领(1), 促销赠送(2), 后台发放(3);
        private Integer value;

        COUPON_GET_TYPE(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 优惠券状态
     */
    public enum COUPON_STATUS {
        有效(1), 已失效(2), 删除(3);
        private Integer value;

        COUPON_STATUS(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 根据数字找对应枚举类的释义
     *
     * @param enumName
     * @param param
     * @return
     */
    public static String getName(String enumName, Object param) {
        if (StringUtils.isBlank(enumName) || null == param) return null;
        Integer value = null;
        if (param instanceof Integer) {
            value = (Integer) param;
        } else if (param instanceof Byte) {
            value = Integer.valueOf(param.toString());
        } else if (param instanceof String) {
            value = Integer.valueOf(param.toString());
        }
        try {
            Class class1 = Class.forName(EnumUtil.class.getName() + "$" + enumName);
            for (Object obj : class1.getEnumConstants()) {
                if (class1.getMethod("getValue").invoke(obj).equals(value)) {
                    return class1.getMethod("name").invoke(obj).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

}
