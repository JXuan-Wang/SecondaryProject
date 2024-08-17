package org.example.systemserver.service;

import com.github.pagehelper.PageInfo;
import org.example.systemserver.domain.TActivity;
import org.example.systemserver.query.ActivityQuery;

import java.util.List;

public interface ActivityService {
    PageInfo<TActivity> getActivityByPage(Integer current, ActivityQuery activityQuery);

    int saveActivity(ActivityQuery activityQuery);

    TActivity getActivityById(Integer id);

    int updateActivity(ActivityQuery activityQuery);

    List<TActivity> getOngoingActivity();

}
