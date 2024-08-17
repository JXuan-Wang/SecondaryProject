package org.example.systemserver.mapper;

import org.example.systemserver.commons.DataScope;
import org.example.systemserver.domain.TUser;
import org.example.systemserver.query.BaseQuery;

import java.util.List;

/**
* @author 炫
* @description 针对表【t_user(用户表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TUser
*/
public interface TUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    TUser selectByLoginAct(String username);

    @DataScope(tableAlias = "tu", tableField = "id")
    List<TUser> selectUserByPage(BaseQuery baseQuery);

    int deleteUsersById(String[] ids);

    List<TUser> selectByOwner();
}
