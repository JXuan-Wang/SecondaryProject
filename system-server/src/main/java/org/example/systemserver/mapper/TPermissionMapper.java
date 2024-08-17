package org.example.systemserver.mapper;

import org.example.systemserver.domain.TPermission;

import java.util.List;

/**
* @author 炫
* @description 针对表【t_permission(权限表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TPermission
*/
public interface TPermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TPermission record);

    int insertSelective(TPermission record);

    TPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TPermission record);

    int updateByPrimaryKey(TPermission record);

    List<TPermission> selectMenuPermissionByUserId(Integer id);
    List<TPermission> selectButtonPermissionByUserId(Integer id);

}
