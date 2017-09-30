package com.chengshi.shop.service.system.impl;

import com.chengshi.shop.dao.system.ShopConfigMapper;
import com.chengshi.shop.service.system.ShopConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商城配置
 * @author xuxinlong
 * @version 2017年09月30日
 */
@Service
public class ShopConfigServiceImpl implements ShopConfigService {
    @Resource
    private ShopConfigMapper shopConfigMapper;


    /**
     * 获取商家配置
     *
     * @param key
     * @return
     */
    @Override
    public String getConfig(String key) {
        return shopConfigMapper.getConfig(key);
    }
}
