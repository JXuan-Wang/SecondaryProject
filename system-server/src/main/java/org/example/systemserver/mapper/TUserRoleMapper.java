package org.example.systemserver.mapper;

import org.example.systemserver.domain.TUserRole;

/**
* @author 炫
* @description 针对表【t_user_role(用户角色关系表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TUserRole
*/
public interface TUserRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TUserRole record);

    int insertSelective(TUserRole record);

    TUserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TUserRole record);

    int updateByPrimaryKey(TUserRole record);

}
