package org.example.systemserver.mapper;

import org.example.systemserver.commons.DataScope;
import org.example.systemserver.domain.TActivityRemark;
import org.example.systemserver.query.ActivityRemarkQuery;

import java.util.List;

/**
* @author 炫
* @description 针对表【t_activity_remark(市场活动备注表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TActivityRemark
*/
public interface TActivityRemarkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TActivityRemark record);

    int insertSelective(TActivityRemark record);

    TActivityRemark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TActivityRemark record);

    int updateByPrimaryKey(TActivityRemark record);

    @DataScope(tableAlias = "tar",tableField = "create_by")
    List<TActivityRemark> selectActivityRemarkByPage(ActivityRemarkQuery activityRemarkQuery);
}
