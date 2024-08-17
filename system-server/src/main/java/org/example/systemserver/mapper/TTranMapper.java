package org.example.systemserver.mapper;

import org.example.systemserver.domain.TTran;

import java.math.BigDecimal;

/**
* @author 炫
* @description 针对表【t_tran(交易表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TTran
*/
public interface TTranMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TTran record);

    int insertSelective(TTran record);

    TTran selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TTran record);

    int updateByPrimaryKey(TTran record);

    BigDecimal selectBySuccessTranAmount();

    BigDecimal selectByTotalTranAmount();

    int selectByTotalTranCount();

    int selectBySuccessTranCount();
}
