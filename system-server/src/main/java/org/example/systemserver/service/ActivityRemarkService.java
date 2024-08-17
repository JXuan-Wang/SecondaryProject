package org.example.systemserver.service;

import com.github.pagehelper.PageInfo;
import org.example.systemserver.domain.TActivityRemark;
import org.example.systemserver.query.ActivityRemarkQuery;

public interface ActivityRemarkService {
    int saveActivityRemark(ActivityRemarkQuery activityRemarkQuery);

    PageInfo<TActivityRemark> getActivityRemarkByPage(Integer current, ActivityRemarkQuery activityRemarkQuery);

    TActivityRemark getActivityRemarkById(Integer id);

    int updateActivityRemark(ActivityRemarkQuery activityRemarkQuery);

    int delActivityRemark(Integer id);
}
