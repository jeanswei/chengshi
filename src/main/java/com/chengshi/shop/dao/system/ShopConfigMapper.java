package com.chengshi.shop.dao.system;

import com.chengshi.shop.model.system.ShopConfig;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopConfigMapper {
    int deleteByPrimaryKey(Integer configId);

    int insert(ShopConfig record);

    int insertSelective(ShopConfig record);

    ShopConfig selectByPrimaryKey(Integer configId);

    int updateByPrimaryKeySelective(ShopConfig record);

    int updateByPrimaryKey(ShopConfig record);

    /**
     * 获取系统配置
     * @param key
     * @return
     */
    String getConfig(String key);
}