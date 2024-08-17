package org.example.systemserver.web;

import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.example.systemserver.domain.TClueRemark;
import org.example.systemserver.query.ClueRemarkQuery;
import org.example.systemserver.result.R;
import org.example.systemserver.service.ClueRemarkService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClueRemarkController {

    @Resource
    private ClueRemarkService clueRemarkService;

    @PostMapping(value = "/api/clue/remark")
    public R addActivityRemark(@RequestBody ClueRemarkQuery clueRemarkQuery, @RequestHeader(value = "Authorization") String token) {
        //axios提交post请求，提交过来的是json数据，使用@RequestBody注解接收
        clueRemarkQuery.setToken(token);
        int save = clueRemarkService.saveClueRemark(clueRemarkQuery);
        return save >= 1 ? R.OK( ) : R.FAIL();
    }

    @GetMapping(value = "/api/clue/remark")
    public R clueRemarkPage(@RequestParam(value = "current", required = false) Integer current,
                            @RequestParam(value = "clueId") Integer clueId) {

        ClueRemarkQuery clueRemarkQuery = new ClueRemarkQuery();
        clueRemarkQuery.setClueId(clueId);

        if (current == null) {
            current = 1;
        }
        PageInfo<TClueRemark> pageInfo = clueRemarkService.getClueRemarkByPage(current, clueRemarkQuery);
        return R.OK(pageInfo);
    }
}
