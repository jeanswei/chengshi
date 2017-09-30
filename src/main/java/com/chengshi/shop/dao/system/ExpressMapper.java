package com.chengshi.shop.dao.system;

import com.chengshi.shop.model.system.Express;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpressMapper {
    int deleteByPrimaryKey(Byte expressId);

    int insert(Express record);

    int insertSelective(Express record);

    Express selectByPrimaryKey(Byte expressId);

    int updateByPrimaryKeySelective(Express record);

    int updateByPrimaryKey(Express record);

    /**
     * 根据快递编号查询快递公司名称
     * @param expressCode
     * @return
     */
    String findByExpressCode(String expressCode);
}