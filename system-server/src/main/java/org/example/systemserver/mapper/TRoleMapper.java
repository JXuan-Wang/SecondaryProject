package org.example.systemserver.mapper;

import org.example.systemserver.domain.TRole;

import java.util.List;

/**
* @author 炫
* @description 针对表【t_role(角色表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TRole
*/
public interface TRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TRole record);

    int insertSelective(TRole record);

    TRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TRole record);

    int updateByPrimaryKey(TRole record);

    List<TRole> selectByUserId(Integer userId);
}
