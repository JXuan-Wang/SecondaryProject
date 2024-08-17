package org.example.systemserver.web;

import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.example.systemserver.domain.TClue;
import org.example.systemserver.query.ClueQuery;
import org.example.systemserver.result.R;
import org.example.systemserver.service.ClueService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ClueController {

    @Resource
    private ClueService clueService;

    @PreAuthorize(value = "hasAuthority('clue:list')")
    @GetMapping(value = "/api/clues")
    public R cluePage(@RequestParam(value = "current", required = false) Integer current) {
        if (current == null) {
            current = 1;
        }
        PageInfo<TClue> pageInfo = clueService.getClueByPage(current);
        return R.OK(pageInfo);
    }

    @PreAuthorize(value = "hasAuthority('clue:import')")
    @PostMapping(value = "/api/importExcel")
    public R importExcel(MultipartFile file, @RequestHeader(value = "Authorization") String token) throws IOException { //filex的名字要和前端formData里面的名字相同，否则接收不到

        clueService.importExcel(file.getInputStream(), token);

        return R.OK();
    }

    @GetMapping(value = "/api/clue/{phone}")
    public R checkPhone(@PathVariable(value = "phone") String phone) {
        Boolean check = clueService.checkPhone(phone);
        return check ? R.OK() : R.FAIL();
    }

    @PreAuthorize(value = "hasAuthority('clue:add')")
    @PostMapping(value = "/api/clue")
    public R addClue(ClueQuery clueQuery, @RequestHeader(value = "Authorization") String token) {
        clueQuery.setToken(token);
        int save = clueService.saveClue(clueQuery);

        return save >= 1 ? R.OK() : R.FAIL();
    }

    @PreAuthorize(value = "hasAuthority('clue:view')")
    @GetMapping(value = "/api/clue/detail/{id}")
    public R loadClue(@PathVariable(value = "id") Integer id) {
        TClue tClue = clueService.getClueById(id);
        return R.OK(tClue);
    }

    @PreAuthorize(value = "hasAuthority('clue:edit')")
    @PutMapping(value = "/api/clue")
    public R editClue(ClueQuery clueQuery, @RequestHeader(value = "Authorization") String token) {
        clueQuery.setToken(token);
        int update = clueService.updateClue(clueQuery);

        return update >= 1 ? R.OK() : R.FAIL();
    }

    @PreAuthorize(value = "hasAuthority('clue:delete')")
    @DeleteMapping(value = "/api/clue/{id}")
    public R delClue(@PathVariable(value = "id") Integer id) {
        int del = 1; //clueService.delClueById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }
}
