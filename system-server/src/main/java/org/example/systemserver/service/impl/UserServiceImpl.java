package org.example.systemserver.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.systemserver.constants.Constants;
import org.example.systemserver.domain.TPermission;
import org.example.systemserver.domain.TRole;
import org.example.systemserver.domain.TUser;
import org.example.systemserver.manager.RedisManager;
import org.example.systemserver.mapper.TPermissionMapper;
import org.example.systemserver.mapper.TRoleMapper;
import org.example.systemserver.mapper.TUserMapper;
import org.example.systemserver.query.BaseQuery;
import org.example.systemserver.query.UserQuery;
import org.example.systemserver.service.UserService;
import org.example.systemserver.uitl.CacheUtils;
import org.example.systemserver.uitl.JWTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private TUserMapper tUserMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private TRoleMapper tRoleMapper;
    @Resource
    private RedisManager redisManager;
    @Resource
    private TPermissionMapper tPermissionMapper;

    /**
     * 登录查询
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser tUser = tUserMapper.selectByLoginAct(username);

        if (tUser == null) {
            throw new UsernameNotFoundException("登录账号不存在");
        }

        // 查询一下当前用户的角色
        List<TRole> tRoleList = tRoleMapper.selectByUserId(tUser.getId());

        // 为用户添加角色
        List<String> roleList = new ArrayList<>();
        tRoleList.forEach(tRole -> {
            roleList.add(tRole.getRole());
        });
        tUser.setRoleList(roleList);

        // 查询一下该用户有哪些菜单权限
        List<TPermission> menuPermissionList=tPermissionMapper
                .selectMenuPermissionByUserId(tUser.getId());

        tUser.setMenuPermissionList(menuPermissionList);

        // 查询一下该用户有哪些功能权限
        List<TPermission> buttonPermissionList=tPermissionMapper
                .selectButtonPermissionByUserId(tUser.getId());
        List<String> permissionList=new ArrayList<>();
        buttonPermissionList.forEach(tPermission -> {
            permissionList.add(tPermission.getCode());
        });
        tUser.setPermissionList(permissionList);
        return tUser;
    }

    // 分页查询所有用户的信息
    @Override
    public PageInfo<TUser> getUserByPage(Integer current) {
        // 设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);

        // mapper查询
        List<TUser> list = tUserMapper.selectUserByPage(BaseQuery.builder().build());

        // 封装分页数据到PageInfo
        return new PageInfo<>(list);
    }

    // 根据id查询用户
    @Override
    public TUser getUserById(Long id) {
        TUser tUser = tUserMapper.selectByPrimaryKey(id);
        if (tUser.getCreateBy() != null) {
            tUser.setCreateByDO(tUserMapper.selectByPrimaryKey(tUser.getCreateBy().longValue()));
        }
        if (tUser.getEditBy() != null) {
            tUser.setEditByDO(tUserMapper.selectByPrimaryKey(tUser.getEditBy().longValue()));
        }

        return tUser;
    }

    // 保存用户个人信息
    @Override
    public int saveUser(UserQuery userQuery) {
        TUser tUser = new TUser();

        // 把userQuery的属性复制到tUser中
        BeanUtils.copyProperties(userQuery, tUser);

        // 密码加密
        tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd()));

        // 添加创建时间
        tUser.setCreateTime(new Date());

        // 获取创建人的id，并加入tUser对象中
        Integer createBy = JWTUtils.parseUserFromJWT(userQuery.getToken()).getId();
        tUser.setCreateBy(createBy);

        return tUserMapper.insertSelective(tUser);
    }

    // 编辑用户
    @Override
    public int updateUser(UserQuery userQuery) {
        TUser tUser = new TUser();
        // 把userQuery的属性复制到tUser中
        BeanUtils.copyProperties(userQuery, tUser);
        // 密码加密
        if (StringUtils.hasText(userQuery.getLoginPwd())) {
            tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd()));
        }
        // 添加修改时间
        tUser.setEditTime(new Date());

        // 获取修改人的id，并加入tUser对象中
        Integer editBy = JWTUtils.parseUserFromJWT(userQuery.getToken()).getId();
        tUser.setEditBy(editBy);

        return tUserMapper.updateByPrimaryKeySelective(tUser);
    }

    // 删除用户
    @Override
    public int delUserById(Integer id) {
        return tUserMapper.deleteByPrimaryKey(id.longValue());
    }

    // 批量删除用户
    @Override
    public int delUsersById(String[] ids) {
        return tUserMapper.deleteUsersById(ids);
    }

    // 查询负责人
    @Override
    public List<TUser> getOwnerList() {
        // 从redis查询
        // redis查不到，就从数据库查询，并且把数据放入redis（3分钟过期）
        return CacheUtils.getCacheData(()->{
            // 生产，从redis查询数据
            return (List<TUser>) redisManager.getValue(Constants.REDIS_OWNER_KEY);
        },()->{
            // 从数据库中查询数据
            return tUserMapper.selectByOwner();
        },t-> {
            // 将数据放入redis中
            redisManager.setValue(Constants.REDIS_OWNER_KEY, t);
        });
    }
}
