package com.chengshi.shop.dao.system;

import com.chengshi.shop.model.system.Sequence;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Sequence record);

    int insertSelective(Sequence record);
}