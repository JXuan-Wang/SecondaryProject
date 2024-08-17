package org.example.systemserver.web;

import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.systemserver.domain.TUser;
import org.example.systemserver.query.UserQuery;
import org.example.systemserver.result.R;
import org.example.systemserver.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 获取登录人信息
     * @param authentication
     * @return
     */
    @GetMapping("/api/login/info")
    public R loginInfo(Authentication authentication){
        TUser principal =(TUser) authentication.getPrincipal();
        return R.OK(principal);
    }

    /**
     * 用来执行自动登录
     * @return
     */
    @GetMapping("/api/login/free")
    public R freeLogin(){
        return R.OK();
    }

    /**
     * 分页查询用户信息
     * @param current
     * @return
     */
    @GetMapping("/api/users")
    public R userPage(@RequestParam(value="current",required = false)Integer current){
        // current默认为1
        if(current == null){
            current = 1;
        }
        PageInfo<TUser> info=userService.getUserByPage(current);
        return R.OK(info);
    }

    /**
     * 获取某个id的用户的信息
     * @param id
     * @return
     */
    @GetMapping("/api/user/{id}")
    public R userDetail(@PathVariable("id") Long id){
        TUser tUser=userService.getUserById(id);
        return R.OK(tUser);
    }

    /**
     * 保存用户信息
     * @param userQuery
     * @return
     */
    @PostMapping("/api/user")
    public R addUser(UserQuery userQuery,@RequestHeader("Authorization")String token){
        userQuery.setToken(token);
        int save = userService.saveUser(userQuery);
        return save >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 编辑用户信息
     * @param userQuery
     * @return
     */
    @PutMapping("/api/user")
    public R editUser(@RequestBody UserQuery userQuery,
                      @RequestHeader("Authorization")String token){
        userQuery.setToken(token);
        int updateUser = userService.updateUser(userQuery);
        return updateUser >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/api/user/{id}")
    public R delUser(@PathVariable("id") Integer id){
        int del = userService.delUserById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @DeleteMapping("/api/users")
    public R batchDelUser(@RequestParam("ids") String ids){
       String[] idArr = ids.split(",");

        int del = userService.delUsersById(idArr);
        return del >= idArr.length ? R.OK() : R.FAIL();
    }

    /**
     * 查询负责人
     * @return
     */
    @GetMapping("/api/owner")
    public R owner(){
        List<TUser> ownerList = userService.getOwnerList();
        return R.OK(ownerList);
    }
}
