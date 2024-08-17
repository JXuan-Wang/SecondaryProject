package org.example.systemserver.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.systemserver.constants.Constants;
import org.example.systemserver.domain.TActivityRemark;
import org.example.systemserver.mapper.TActivityRemarkMapper;
import org.example.systemserver.mapper.TUserMapper;
import org.example.systemserver.query.ActivityRemarkQuery;
import org.example.systemserver.query.BaseQuery;
import org.example.systemserver.service.ActivityRemarkService;
import org.example.systemserver.uitl.JWTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Resource
    private TActivityRemarkMapper tActivityRemarkMapper;
    @Resource
    private TUserMapper tUserMapper;

    @Override
    public int saveActivityRemark(ActivityRemarkQuery activityRemarkQuery) {
        TActivityRemark tActivityRemark = new TActivityRemark();

        // 把activityQuery的属性复制到tActivity中
        BeanUtils.copyProperties(activityRemarkQuery, tActivityRemark);

        // 添加创建时间
        tActivityRemark.setCreateTime(new Date());

        // 获取创建人的id，并加入tActivity对象中
        Integer createBy = JWTUtils.parseUserFromJWT(activityRemarkQuery.getToken()).getId();
        tActivityRemark.setCreateBy(createBy);

        return tActivityRemarkMapper.insertSelective(tActivityRemark);
    }

    @Override
    public PageInfo<TActivityRemark> getActivityRemarkByPage(Integer current, ActivityRemarkQuery activityRemarkQuery) {
        // 设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);

        // mapper查询
        List<TActivityRemark> list = tActivityRemarkMapper.selectActivityRemarkByPage(activityRemarkQuery);

        // 封装分页数据到PageInfo
        return new PageInfo<>(list);
    }

    @Override
    public TActivityRemark getActivityRemarkById(Integer id) {
        return tActivityRemarkMapper.selectByPrimaryKey(id.longValue());
    }

    @Override
    public int updateActivityRemark(ActivityRemarkQuery activityRemarkQuery) {
        TActivityRemark tActivityRemark = new TActivityRemark();

        // 把activityQuery的属性复制到tActivity中
        BeanUtils.copyProperties(activityRemarkQuery, tActivityRemark);
        tActivityRemark.setId(activityRemarkQuery.getActivityId());
        tActivityRemark.setActivityId(null);
        // 添加创建时间
        tActivityRemark.setEditTime(new Date());

        // 获取创建人的id，并加入tActivity对象中
        Integer editBy = JWTUtils.parseUserFromJWT(activityRemarkQuery.getToken()).getId();
        tActivityRemark.setEditBy(editBy);

        return tActivityRemarkMapper.updateByPrimaryKeySelective(tActivityRemark);
    }

    @Override
    public int delActivityRemark(Integer id) {
        TActivityRemark tActivityRemark=new TActivityRemark();
        tActivityRemark.setId(id);
        tActivityRemark.setDeleted(1);
        return tActivityRemarkMapper.updateByPrimaryKeySelective(tActivityRemark);
    }
}
