package org.example.systemserver.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.example.systemserver.config.listener.UploadDataListener;
import org.example.systemserver.constants.Constants;
import org.example.systemserver.domain.TClue;
import org.example.systemserver.mapper.TClueMapper;
import org.example.systemserver.query.BaseQuery;
import org.example.systemserver.query.ClueQuery;
import org.example.systemserver.service.ClueService;
import org.example.systemserver.uitl.JWTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    private TClueMapper tClueMapper;

    @Override
    public PageInfo<TClue> getClueByPage(Integer current) {
        // 1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 2.查询
        List<TClue> list = tClueMapper.selectClueByPage(BaseQuery.builder().build());
        // 3.封装分页数据到PageInfo
        PageInfo<TClue> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public void importExcel(InputStream inputStream, String token) {
        EasyExcel.read(inputStream, TClue.class,
                new UploadDataListener(tClueMapper,token)).sheet().doRead();
    }

    @Override
    public Boolean checkPhone(String phone) {
        int count = tClueMapper.selectByCount(phone);
        return count <= 0; //没有查到手机号是true
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveClue(ClueQuery clueQuery) {
        int count = tClueMapper.selectByCount(clueQuery.getPhone());
        if (count <= 0) {
            TClue tClue = new TClue();

            //把前端提交过来的参数数据对象ClueQuery复制到TClue对象中
            //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
            BeanUtils.copyProperties(clueQuery, tClue);

            //解析jwt得到userId
            Integer loginUserId = JWTUtils.parseUserFromJWT(clueQuery.getToken()).getId();

            tClue.setCreateTime(new Date()); //创建时间
            tClue.setCreateBy(loginUserId); //创建人id

            return tClueMapper.insertSelective(tClue);
        } else {
            throw new RuntimeException("该手机号已经录入过了，不能再录入");
        }
    }

    @Override
    public TClue getClueById(Integer id) {
        return tClueMapper.selectDetailById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateClue(ClueQuery clueQuery) {
        TClue tClue = new TClue();

        //把前端提交过来的参数数据对象ClueQuery复制到TClue对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(clueQuery, tClue);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseUserFromJWT(clueQuery.getToken()).getId();

        tClue.setEditTime(new Date()); //编辑时间
        tClue.setEditBy(loginUserId); //编辑人id

        return tClueMapper.updateByPrimaryKeySelective(tClue);
    }
}
