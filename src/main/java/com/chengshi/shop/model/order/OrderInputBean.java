package com.chengshi.shop.model.order;

public class OrderInputBean {

    private Integer memberId;//用户id

    private Integer addressId;//收货地址id

    private String markText; //订单备注

    private String productIdAndNum;//货品id和购买数量“-”隔开 多个“,”隔开

    private Boolean isUsePoints;//是否使用积分

    private Integer memberCouponId;//优惠券id

    private String consignee; //收货人

    private String mobile; //收货人手机号

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMarkText() {
        return markText;
    }

    public void setMarkText(String markText) {
        this.markText = markText;
    }

    public Integer getMemberCouponId() {
        return memberCouponId;
    }

    public void setMemberCouponId(Integer memberCouponId) {
        this.memberCouponId = memberCouponId;
    }

    public String getProductIdAndNum() {
        return productIdAndNum;
    }

    public void setProductIdAndNum(String productIdAndNum) {
        this.productIdAndNum = productIdAndNum;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Boolean getIsUsePoints() {
        return isUsePoints;
    }

    public void setIsUsePoints(Boolean isUsePoints) {
        this.isUsePoints = isUsePoints;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
