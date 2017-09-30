package com.chengshi.shop.model.member;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Member implements Serializable{
    private Integer memberId;

    private String name;

    private Byte sex;

    private Date birthday;

    private BigDecimal memberPoints;

    private BigDecimal memberBalance;

    private Integer memberRank;

    private BigDecimal rankPoints;

    private String mobile;

    private String openId;

    private String headImg;

    private String country;

    private String province;

    private String city;

    private Date createTime;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(BigDecimal memberPoints) {
        this.memberPoints = memberPoints;
    }

    public BigDecimal getMemberBalance() {
        return memberBalance;
    }

    public void setMemberBalance(BigDecimal memberBalance) {
        this.memberBalance = memberBalance;
    }

    public Integer getMemberRank() {
        return memberRank;
    }

    public void setMemberRank(Integer memberRank) {
        this.memberRank = memberRank;
    }

    public BigDecimal getRankPoints() {
        return rankPoints;
    }

    public void setRankPoints(BigDecimal rankPoints) {
        this.rankPoints = rankPoints;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}