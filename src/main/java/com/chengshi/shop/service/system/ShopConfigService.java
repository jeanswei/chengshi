package com.chengshi.shop.service.system;

/**
 * 商城配置表
 * @author xuxinlong
 * @version 2017年09月30日
 */
public interface ShopConfigService {

    /**
     * 获取商家配置
     * @param key
     * @return
     */
    String getConfig(String key);
}
