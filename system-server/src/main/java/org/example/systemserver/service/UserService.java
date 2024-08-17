package org.example.systemserver.service;

import com.github.pagehelper.PageInfo;
import org.example.systemserver.domain.TUser;
import org.example.systemserver.query.UserQuery;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    PageInfo<TUser> getUserByPage(Integer current);

    TUser getUserById(Long id);

    int saveUser(UserQuery userQuery);

    int updateUser(UserQuery userQuery);

    int delUserById(Integer id);

    int delUsersById(String[] ids);

    List<TUser> getOwnerList();

}
