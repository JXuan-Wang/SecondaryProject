package org.example.systemserver.mapper;

import org.example.systemserver.domain.TClue;
import org.example.systemserver.query.BaseQuery;
import org.example.systemserver.result.NameValue;

import java.util.List;

/**
* @author 炫
* @description 针对表【t_clue(线索表)】的数据库操作Mapper
* @createDate 2024-07-05 03:54:15
* @Entity org.example.systemserver.domain.TClue
*/
public interface TClueMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TClue record);

    int insertSelective(TClue record);

    TClue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TClue record);

    int updateByPrimaryKey(TClue record);

    List<TClue> selectClueByPage(BaseQuery build);

    void saveClue(List<TClue> cachedDataList);

    int selectByCount(String phone);

    TClue selectDetailById(Integer id);

    Integer selectClueByCount();

    List<NameValue> selectBySource();
}
