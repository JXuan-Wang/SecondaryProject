package org.example.systemserver.mapper;

import org.example.systemserver.domain.TSystemInfo;

/**
* @author 炫
* @description 针对表【t_system_info(系统信息表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TSystemInfo
*/
public interface TSystemInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TSystemInfo record);

    int insertSelective(TSystemInfo record);

    TSystemInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TSystemInfo record);

    int updateByPrimaryKey(TSystemInfo record);

}
