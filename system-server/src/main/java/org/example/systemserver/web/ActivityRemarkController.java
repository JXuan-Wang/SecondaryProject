package org.example.systemserver.web;

import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.systemserver.domain.TActivityRemark;
import org.example.systemserver.query.ActivityRemarkQuery;
import org.example.systemserver.result.R;
import org.example.systemserver.service.ActivityRemarkService;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ActivityRemarkController {
    @Resource
    private ActivityRemarkService activityRemarkService;

    @PostMapping("/api/activity/remark")
    public R addActivityRemark(@RequestBody ActivityRemarkQuery activityRemarkQuery,
                               @RequestHeader("Authorization")String token){
        activityRemarkQuery.setToken(token);
        int save = activityRemarkService.saveActivityRemark(activityRemarkQuery);
        return save >= 1 ? R.OK() : R.FAIL();
    }

    @GetMapping("/api/activity/remark")
    public R activityRemarkPage(@RequestParam(value="current",required = false)Integer current,
                                @RequestParam("activityId") Integer activityId){
        ActivityRemarkQuery activityRemarkQuery=new ActivityRemarkQuery();
        activityRemarkQuery.setActivityId(activityId);
        // current默认为1
        if(current == null){
            current = 1;
        }
        PageInfo<TActivityRemark> info=activityRemarkService.getActivityRemarkByPage(current, activityRemarkQuery);
        return R.OK(info);
    }

    @GetMapping("/api/activity/remark/{id}")
    public R activityRemarkById(@PathVariable("id")Integer id){
        TActivityRemark tActivityRemark=activityRemarkService.getActivityRemarkById(id);
        return R.OK(tActivityRemark);
    }

    @PutMapping("/api/activity/remark")
    public R activityRemarkUpdate(@RequestBody ActivityRemarkQuery activityRemarkQuery,
                                  @RequestHeader("Authorization")String token){
        activityRemarkQuery.setToken(token);
        int updated = activityRemarkService.updateActivityRemark(activityRemarkQuery);
        return updated >= 1 ? R.OK() : R.FAIL();
    }

    @DeleteMapping("/api/activity/remark/{id}")
    public R delActivityRemark(@PathVariable("id") Integer id){
        int del = activityRemarkService.delActivityRemark(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }
}
