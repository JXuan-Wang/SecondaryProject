package org.example.systemserver.mapper;

import org.example.systemserver.domain.TDicType;

import java.util.List;

/**
* @author 炫
* @description 针对表【t_dic_type(字典类型表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TDicType
*/
public interface TDicTypeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TDicType record);

    int insertSelective(TDicType record);

    TDicType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TDicType record);

    int updateByPrimaryKey(TDicType record);

    List<TDicType> selectByAll();
}
