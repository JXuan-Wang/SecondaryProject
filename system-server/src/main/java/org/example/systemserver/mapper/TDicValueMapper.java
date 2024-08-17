package org.example.systemserver.mapper;

import org.example.systemserver.domain.TDicValue;

/**
* @author 炫
* @description 针对表【t_dic_value(字典值表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TDicValue
*/
public interface TDicValueMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TDicValue record);

    int insertSelective(TDicValue record);

    TDicValue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TDicValue record);

    int updateByPrimaryKey(TDicValue record);

}
