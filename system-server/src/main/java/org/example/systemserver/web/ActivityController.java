package org.example.systemserver.web;

import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.example.systemserver.domain.TActivity;
import org.example.systemserver.query.ActivityQuery;
import org.example.systemserver.query.UserQuery;
import org.example.systemserver.result.R;
import org.example.systemserver.service.ActivityService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityController {
    @Resource
    private ActivityService activityService;

    /**
     * 分页查询活动信息
     * @param current
     * @return
     */
    @GetMapping("/api/activities")
    public R activityPage(@RequestParam(value="current",required = false)Integer current,
                          ActivityQuery activityQuery){
        // current默认为1
        if(current == null){
            current = 1;
        }
        PageInfo<TActivity> info=activityService.getActivityByPage(current,activityQuery);
        return R.OK(info);
    }

    /**
     * 保存活动信息
     * @param activityQuery
     * @param token
     * @return
     */
    @PostMapping("/api/activity")
    public R addActivity(ActivityQuery activityQuery, @RequestHeader("Authorization")String token){
        activityQuery.setToken(token);
        int save = activityService.saveActivity(activityQuery);
        return save >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 按id查询活动数据
     * @param id
     * @return
     */
    @GetMapping("/api/activity/{id}")
    public R loadActivity(@PathVariable("id")Integer id){
        TActivity tActivity = activityService.getActivityById(id);
        return R.OK(tActivity);
    }

    @PutMapping("/api/activity")
    public R updateActivity(ActivityQuery activityQuery, @RequestHeader("Authorization")String token){
        activityQuery.setToken(token);
        int save = activityService.updateActivity(activityQuery);
        return save >= 1 ? R.OK() : R.FAIL();
    }
}
