package org.example.systemserver.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.example.systemserver.constants.Constants;
import org.example.systemserver.domain.TActivity;
import org.example.systemserver.domain.TUser;
import org.example.systemserver.mapper.TActivityMapper;
import org.example.systemserver.mapper.TUserMapper;
import org.example.systemserver.query.ActivityQuery;
import org.example.systemserver.service.ActivityService;
import org.example.systemserver.uitl.JWTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private TActivityMapper tActivityMapper;
    @Resource
    private TUserMapper tUserMapper;

    @Override
    public PageInfo<TActivity> getActivityByPage(Integer current, ActivityQuery activityQuery) {
        // 设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);

        // mapper查询
        List<TActivity> list = tActivityMapper.selectActivityByPage(activityQuery);

        list.forEach(tActivity -> tActivity.setOwnerName(tUserMapper
                .selectByPrimaryKey(tActivity
                        .getOwnerId().longValue()).getName()));

        // 封装分页数据到PageInfo
        return new PageInfo<>(list);
    }

    @Override
    public int saveActivity(ActivityQuery activityQuery) {
        TActivity tActivity = new TActivity();

        // 把activityQuery的属性复制到tActivity中
        BeanUtils.copyProperties(activityQuery, tActivity);



        // 添加创建时间
        tActivity.setCreateTime(new Date());

        // 获取创建人的id，并加入tActivity对象中
        Integer createBy = JWTUtils.parseUserFromJWT(activityQuery.getToken()).getId();
        tActivity.setCreateBy(createBy);

        return tActivityMapper.insertSelective(tActivity);
    }

    @Override
    public TActivity getActivityById(Integer id) {
        TActivity tActivity = tActivityMapper.selectByPrimaryKey(id.longValue());
        if(!ObjectUtils.isEmpty(tActivity.getOwnerId())) {
            tActivity.setOwnerDO(tUserMapper.
                    selectByPrimaryKey(tActivity.getOwnerId().longValue()));
        }
        if(!ObjectUtils.isEmpty(tActivity.getCreateBy())) {
            tActivity.setCreateByDO(tUserMapper.
                    selectByPrimaryKey(tActivity.getCreateBy().longValue()));
        }
        if(!ObjectUtils.isEmpty(tActivity.getEditBy())) {
            tActivity.setEditByDO(tUserMapper.
                    selectByPrimaryKey(tActivity.getEditBy().longValue()));
        }
        return tActivity;
    }

    @Override
    public int updateActivity(ActivityQuery activityQuery) {
        TActivity tActivity = new TActivity();
        // 把activityQuery的属性复制到tActivity中
        BeanUtils.copyProperties(activityQuery, tActivity);

        // 添加修改时间
        tActivity.setEditTime(new Date());

        // 获取修改人的id，并加入tActivity对象中
        Integer editBy = JWTUtils.parseUserFromJWT(activityQuery.getToken()).getId();
        tActivity.setEditBy(editBy);

        return tActivityMapper.updateByPrimaryKeySelective(tActivity);
    }

    @Override
    public List<TActivity> getOngoingActivity() {
        return tActivityMapper.selecOngoingActivity();
    }
}
