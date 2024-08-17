package org.example.systemserver.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.example.systemserver.constants.Constants;
import org.example.systemserver.domain.TClueRemark;
import org.example.systemserver.mapper.TClueRemarkMapper;
import org.example.systemserver.query.ClueRemarkQuery;
import org.example.systemserver.service.ClueRemarkService;
import org.example.systemserver.uitl.JWTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {
    @Resource
    private TClueRemarkMapper tClueRemarkMapper;

    @Override
    public int saveClueRemark(ClueRemarkQuery clueRemarkQuery) {
        TClueRemark tClueRemark = new TClueRemark();

        //把ClueRemarkQuery对象里面的属性数据复制到TClueRemark对象里面去(复制要求：两个对象的属性名相同，属性类型要相同，这样才能复制)
        BeanUtils.copyProperties(clueRemarkQuery, tClueRemark);

        tClueRemark.setCreateTime(new Date()); //创建时间

        //登录人的id
        Integer loginUserId = JWTUtils.parseUserFromJWT(clueRemarkQuery.getToken()).getId();
        tClueRemark.setCreateBy(loginUserId); //创建人

        return tClueRemarkMapper.insertSelective(tClueRemark);
    }

    @Override
    public PageInfo<TClueRemark> getClueRemarkByPage(Integer current, ClueRemarkQuery clueRemarkQuery) {
        // 1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 2.查询
        List<TClueRemark> list = tClueRemarkMapper.selectClueRemarkByPage(clueRemarkQuery);
        // 3.封装分页数据到PageInfo
        return new PageInfo<>(list);
    }
}
