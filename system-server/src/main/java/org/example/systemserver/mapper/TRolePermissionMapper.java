package org.example.systemserver.mapper;

import org.example.systemserver.domain.TRolePermission;

/**
* @author 炫
* @description 针对表【t_role_permission(角色权限关系表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TRolePermission
*/
public interface TRolePermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TRolePermission record);

    int insertSelective(TRolePermission record);

    TRolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TRolePermission record);

    int updateByPrimaryKey(TRolePermission record);

}
