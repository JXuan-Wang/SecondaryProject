package org.example.systemserver.mapper;

import org.example.systemserver.domain.TTranHistory;

/**
* @author 炫
* @description 针对表【t_tran_history(交易历史记录表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TTranHistory
*/
public interface TTranHistoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TTranHistory record);

    int insertSelective(TTranHistory record);

    TTranHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TTranHistory record);

    int updateByPrimaryKey(TTranHistory record);

}
