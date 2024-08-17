package org.example.systemserver.mapper;

import org.example.systemserver.commons.DataScope;
import org.example.systemserver.domain.TClueRemark;
import org.example.systemserver.query.ClueRemarkQuery;

import java.util.List;

/**
* @author 炫
* @description 针对表【t_clue_remark(线索跟踪记录表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TClueRemark
*/
public interface TClueRemarkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TClueRemark record);

    int insertSelective(TClueRemark record);

    TClueRemark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TClueRemark record);

    int updateByPrimaryKey(TClueRemark record);

    @DataScope(tableAlias = "tcr", tableField = "create_by")
    List<TClueRemark> selectClueRemarkByPage(ClueRemarkQuery clueRemarkQuery);
}
