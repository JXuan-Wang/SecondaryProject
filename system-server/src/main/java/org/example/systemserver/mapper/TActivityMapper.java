package org.example.systemserver.mapper;

import org.example.systemserver.commons.DataScope;
import org.example.systemserver.domain.TActivity;
import org.example.systemserver.query.ActivityQuery;
import org.example.systemserver.query.BaseQuery;

import java.util.List;

/**
* @author 炫
* @description 针对表【t_activity(市场活动表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TActivity
*/
public interface TActivityMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TActivity record);

    int insertSelective(TActivity record);

    TActivity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TActivity record);

    int updateByPrimaryKey(TActivity record);

    @DataScope(tableAlias = "ta", tableField = "owner_id")
    List<TActivity> selectActivityByPage(ActivityQuery build);

    List<TActivity> selecOngoingActivity();

    Integer selectByCount();
}
