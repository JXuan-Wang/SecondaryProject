package org.example.systemserver.service.impl;

import jakarta.annotation.Resource;
import org.example.systemserver.domain.TDicType;
import org.example.systemserver.mapper.TDicTypeMapper;
import org.example.systemserver.service.DicTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicTypeServiceImpl implements DicTypeService {
    @Resource
    private TDicTypeMapper dicTypeMapper;

    @Override
    public List<TDicType> loadAllDicData() {
        return dicTypeMapper.selectByAll();
    }
}
